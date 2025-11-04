package com.breakout.managers;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.entities.*;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.bricks.FallingBrick;
import com.breakout.entities.items.Item;
import com.breakout.saves.BallSave;
import com.breakout.saves.BrickSave;
import com.breakout.saves.GameSave;
import com.breakout.saves.PaddleSave;

import java.util.*;
import java.util.List;

/**
 * Gameplay manager
 */
public class GameManager {
    private String screenMessage = null;
    private double messageTimer = 0.0;
    private static final double MESSAGE_DURATION = 2.0;

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<Item> activeItems;

    private int score;
    private int lives;
    private int currentDifficulty;
    private int currentLevel = 1;
    private boolean laserEnabled;

    public GameManager() {
        // Initialize game objects
        ball = new Ball(GameConfig.SCREEN_WIDTH/2.0, GameConfig.SCREEN_HEIGHT/2.0);
        paddle = new Paddle(GameConfig.SCREEN_WIDTH/2.0 - 50, GameConfig.SCREEN_HEIGHT - 50);
        bricks = new ArrayList<>();
        activeItems = new ArrayList<>();
        lives = 1; // Only 1 life as per your requirement
    }

    public void showMessageOnScreen(String message) {
        this.screenMessage = message;
        this.messageTimer = MESSAGE_DURATION; // Bắt đầu đếm ngược
    }

    public String getScreenMessage() {
        return screenMessage;
    }

    private boolean paused = false;
    private boolean ballStarted = false;

    public void addItem(Item item) {
        this.activeItems.add(item);
    }

    public List<Item> getActiveItems() {
        return activeItems;
    }

    public boolean isPaused() {
        return paused;
    }

    public void togglePause() {
        paused = !paused;
    }

    public boolean hasBallStarted() {
        return ballStarted;
    }

    public void startBall() {
        ballStarted = true;
    }

    public void startGame(int difficulty) {
        currentDifficulty = difficulty;
        currentLevel = 1;

        bricks = Level.loadLevel(difficulty);
        resetBall();
        resetPaddle();
        lives = 1;
        score = 0;
        activeItems.clear();
    }

    public void update(double deltaTime, boolean leftPressed, boolean rightPressed) {
        if (paused) return;

        if (!ballStarted) return;

        if (isGameOver()) {
            // Lưu game trước khi chuyển sang GAMEOVER
            saveCurrentGame();
            Game.getGame().changeState(Defs.STATE_GAMEOVER);
            return; // Don't update if game is over
        } else if (isWin()) {
            // Lưu game trước khi chuyển sang WIN
            saveCurrentGame();
            Game.getGame().changeState(Defs.STATE_WIN);
            return;
        }

        // Update ball
        ball.update(deltaTime);
        for (Brick brick : bricks) {
            if (brick instanceof FallingBrick) {
                brick.update(deltaTime);
                if (paddle.intersects(brick)) {
                    lives--;
                }
            }
        }

        // Control paddle
        if (leftPressed) {
            paddle.moveLeft(deltaTime, GameConfig.SCREEN_WIDTH);
        }
        if (rightPressed) {
            paddle.moveRight(deltaTime, GameConfig.SCREEN_WIDTH);
        }

        // Ball hits left/right walls
        if (ball.getX() <= 0
                || ball.getX() + ball.getWidth() >= GameConfig.SCREEN_WIDTH - 12) {
            ball.bounceX();
            SoundManager.playWallHitSound();
        }

        // Ball hits top wall
        if (ball.getY() <= 0) {
            ball.bounceY();
            SoundManager.playWallHitSound();
        }

        // Ball falls below bottom border - GAME OVER
        if (ball.getY() > GameConfig.SCREEN_HEIGHT) {
            lives--;
            if (!isGameOver()) {
                // Reset ball if still have lives (though you have only 1 life)
                resetBall();
            }
        }

        // Collision with paddle
        if (ball.intersects(paddle) && ball.getVy() > 0) {
            ball.bounceY();
            ball.collisionFromSides(paddle);
            SoundManager.playWallHitSound();
        }

        // Collision with bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && !brick.isHit() && ball.intersects(brick)) {
                SoundManager.playBrickHitSound();
                brick.hit();
                ball.collisionFromSides(brick);
                break; // Only destroy one brick per collision
            }
        }

        if (messageTimer > 0) {
            messageTimer -= deltaTime;
            if (messageTimer <= 0) {
                screenMessage = null; // Ẩn tin nhắn khi hết giờ
            }
        }

        // Update items
        Iterator<Item> iter = activeItems.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            item.update(deltaTime);
            if (item.intersects(paddle)) {
                item.applyEffect(paddle, this);
                iter.remove();
            } else if (item.getY() > GameConfig.SCREEN_HEIGHT) {
                iter.remove(); // xóa item khi rơi ra ngoài
            }
        }

        // Update score
        int destroyedCount = 0;
        for (Brick brick : bricks) {
            if (brick.isDestroyed()) {
                destroyedCount++;
            }
        }
        this.score = destroyedCount;
    }

    /**
     * Lưu game hiện tại
     */
    public void saveCurrentGame() {
        List<BrickSave> bricksData = new ArrayList<>();

        // Lưu trạng thái của từng brick theo index
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            bricksData.add(new BrickSave(
                    i,
                    0,  // Chỉ dùng index, không cần row/col
                    0,
                    1, // Health mặc định là 1 (vì brick chỉ cần 1 hit để phá)
                    brick.isDestroyed(),
                    "NORMAL" // Loại brick mặc định
            ));
        }

        BallSave ballData = new BallSave(
                ball.getX(), ball.getY(), ball.getVx(), ball.getVy()
        );

        PaddleSave paddleData = new PaddleSave(
                paddle.getX(), paddle.getY(), paddle.getWidth()
        );

        GameSave gameSave = new GameSave(
                currentDifficulty, score, lives, currentLevel,
                ballData, paddleData, bricksData
        );

        SaveManager.saveGame(gameSave);
    }

    /**
     * Load game đã lưu
     */
    public void loadSavedGame(GameSave gameSave) {
        if (gameSave == null) return;

        currentDifficulty = gameSave.getDifficulty();
        score = gameSave.getScore();
        lives = gameSave.getLives();
        currentLevel = gameSave.getLevel();

        // Khôi phục ball
        BallSave ballData = gameSave.getBallData();
        ball.setPosition(ballData.getX(), ballData.getY());
        ball.setVelocity(ballData.getVelX(), ballData.getVelY());

        // Khôi phục paddle
        PaddleSave paddleData = gameSave.getPaddleData();
        paddle.setPosition(paddleData.getX(), paddleData.getY());
        paddle.setWidth(paddleData.getWidth());

        // Khôi phục bricks - cần load level trước
        loadLevelForSavedGame();

        for (BrickSave brickSave : gameSave.getBricksData()) {
            int index =(int) brickSave.getIndex();
            if (index >= 0 && index < bricks.size()) {
                Brick brick = bricks.get(index);
                if (brickSave.isDestroyed()) {
                    brick.destroy();
                }
            }
        }

    }

    /**
     * Load level phù hợp cho game đã lưu
     */
    private void loadLevelForSavedGame() {
        if (bricks == null || bricks.isEmpty()) {
            bricks = Level.loadLevel(currentDifficulty);
        }
    }

    /**
     * Tìm brick tại vị trí row, col
     */
//    private Brick findBrickAt(int row, int col) {
//        for (Brick brick : bricks) {
//            if (brick.getRow() == row && brick.getCol() == col) {
//                return brick;
//            }
//        }
//        return null;
//    }

    /**
     * Kiểm tra có thể continue game không
     */
    public boolean canContinueGame() {
        return SaveManager.saveExists();
    }

    /**
     * Tiếp tục game từ save
     */
    public void continueGame() {
        GameSave savedGame = SaveManager.loadGame();
        if (savedGame != null) {
            loadSavedGame(savedGame);
        }
    }


    public int getNextDifficulty() {
        if (currentDifficulty < Defs.LEVEL_BOSS) {
            currentDifficulty++;
        }
        return currentDifficulty;
    }

    private void resetBall() {
        ball = new Ball(GameConfig.SCREEN_WIDTH/2.0, GameConfig.SCREEN_HEIGHT/2.0);
        ballStarted = false;
    }

    private void resetPaddle() {
        paddle = new Paddle(GameConfig.SCREEN_WIDTH/2.0 - 50, GameConfig.SCREEN_HEIGHT - 50);
    }

    private void spawnRandomItem(double x, double y) {
        //TODO: Logic spawn item (có thể thêm sau)
    }

    public void addExtraBall() {
        //TODO: Logic thêm bóng (có thể thêm sau)
    }

    public void enableLaser() {
        laserEnabled = true;
        // Laser shooting logic (có thể thêm sau)
    }

    private void nextLevel() {
        currentLevel++;
        // Logic chuyển level (có thể thêm sau)
    }

    // Check if player won (all bricks destroyed)
    public boolean isWin() {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public String getDifficultyName() {
        switch (currentDifficulty) {
            case Defs.LEVEL_EASY: return "EASY";
            case Defs.LEVEL_MEDIUM: return "MEDIUM";
            case Defs.LEVEL_HARD: return "HARD";
            case Defs.LEVEL_BOSS: return "BOSS";
            default: return null;
        }
    }

    // Getters

    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Brick> getBricks() { return bricks; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getCurrentLevel() { return currentLevel; }

    /**
     * Lấy thông tin game đã lưu để hiển thị
     */
    public String getSaveInfo() {
        return SaveManager.getSaveInfo();
    }
    public boolean isGameOver() { return lives <= 0; }
    public int getCurrentDifficulty() { return currentDifficulty; }
}
