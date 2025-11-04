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
import com.breakout.gui.MenuPanel;

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
        ballStarted = false;
    }

    /**
     * Tiếp tục game từ save file
     */
    public void continueGame() {
        GameSave savedGame = SaveManager.loadGame();
        if (savedGame != null) {
            loadSavedGame(savedGame);
            ballStarted = false; // không di chuyển ngay
            ball.setVelocity(0,0);
        }
    }

    /**
     * Kiểm tra xem có game đã lưu không
     */
    public boolean canContinueGame() {
        return SaveManager.saveExists(); // Sửa thành SaveManager.saveExists()
    }

    /**
     * Lấy thông tin game đã lưu để hiển thị
     */
    public String getSaveInfo() {
        if (!canContinueGame()) {
            return "No saved game";
        }

        GameSave savedGame = SaveManager.loadGame(); // Sửa thành SaveManager.loadGame()
        if (savedGame != null) {
            return String.format("Level: %d - Score: %d - Lives: %d",
                    savedGame.getLevel(), savedGame.getScore(), savedGame.getLives());
        }
        return "Saved game";
    }

    public void update(double deltaTime, boolean leftPressed, boolean rightPressed) {
        if (paused) return;

        if (!ballStarted) return;

        // XÓA các lệnh saveCurrentGame() tự động
        if (isGameOver()) {
            // KHÔNG lưu game khi game over
            deleteSavedGame();
            Game.getGame().changeState(Defs.STATE_GAMEOVER);
            return; // Don't update if game is over
        } else if (isWin()) {
            // KHÔNG lưu game khi win
            Game.getGame().changeState(Defs.STATE_WIN);
            return;
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
            ball.collisionFromSides(paddle);
            SoundManager.playWallHitSound();
        }

        // Collision with bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && !brick.isHit() && ball.intersects(brick)) {
                brick.hit();
                ball.collisionFromSides(brick);
                SoundManager.playBrickHitSound();
                break; // Only destroy one brick per collision
            }
        }

        if (messageTimer > 0) {
            messageTimer -= deltaTime;
            if (messageTimer <= 0) {
                screenMessage = null; // Ẩn tin nhắn khi hết giờ
            }
        }

        // Update ball
        ball.update(deltaTime);
        // Update paddle
        paddle.update(deltaTime);

        // Update bricks
        for (Brick brick : bricks) {
            if (brick instanceof FallingBrick) {
                brick.update(deltaTime);
                if (paddle.intersects(brick)) {
                    lives--;
                }
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
     * Lưu game hiện tại - CHỈ được gọi khi người chơi chủ động save
     */
    public void saveCurrentGame() {
        List<BrickSave> bricksData = new ArrayList<>();

        // Lưu trạng thái của từng brick
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            bricksData.add(new BrickSave(
                    (int)Math.round(brick.getX()),    // x position (làm tròn để tránh mất dữ liệu)
                    (int)Math.round(brick.getY()),    // y position (làm tròn để tránh mất dữ liệu)
                    (int)Math.round(brick.getWidth()),  // width
                    (int)Math.round(brick.getHeight()), // height
                    brick.isDestroyed(),              // destroyed
                    1,                               // hitPoints (mặc định là 1)
                    1,                               // maxHitPoints (mặc định là 1)
                    0xFF0000,                        // color (màu mặc định - đỏ)
                    10                                // points (điểm mặc định)
            ));
        }

        BallSave ballData = new BallSave(
                ball.getX(), ball.getY(), ball.getVx(), ball.getVy(), 15
        );

        PaddleSave paddleData = new PaddleSave(
                paddle.getX(),
                paddle.getY(),
                (int) paddle.getWidth(),
                (int) paddle.getHeight(),
                paddle.getSpeed()
        );

        GameSave gameSave = new GameSave(
                currentDifficulty, score, lives, currentLevel,
                ballData, paddleData, bricksData
        );

        SaveManager.saveGame(gameSave);

//        MenuPanel menuPanel = new MenuPanel(Game.getGame());
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            Game.getGame().getFrame().setContentPane(menuPanel);
//            menuPanel.updateMenu();
//        });

        System.out.println("Game saved successfully!");
    }

    /**
     * Load game đã lưu
     */
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
        ball.setVelocity(ballData.getVelocityX(), ballData.getVelocityY());

        // Khôi phục paddle
        PaddleSave paddleData = gameSave.getPaddleData();
        paddle.setPosition(paddleData.getX(), paddleData.getY());
        paddle.setWidth(paddleData.getWidth());

        // Khôi phục bricks - cần load level trước
        loadLevelForSavedGame();

        // Khôi phục trạng thái và vị trí của từng brick
        for (BrickSave brickSave : gameSave.getBricks()) {
            // Tìm brick tại vị trí tương ứng
            for (Brick brick : bricks) {
                if ((int)brick.getX() == brickSave.getX() &&
                        (int)brick.getY() == brickSave.getY() &&
                        brick.getWidth() == brickSave.getWidth() &&
                        brick.getHeight() == brickSave.getHeight()) {

                    if (brickSave.isDestroyed()) {
                        brick.destroy();
                    }
                    break;
                }
            }
        }

        // Đảm bảo ball bắt đầu di chuyển
        ballStarted = false;
        ball.setVelocity(0, 0);}

    /**
     * Load level phù hợp cho game đã lưu
     */
    private void loadLevelForSavedGame() {
        if (bricks == null || bricks.isEmpty()) {
            bricks = Level.loadLevel(currentDifficulty);
        }
    }

    /**
     * Xóa game đã lưu
     */
    public void deleteSavedGame() {
        SaveManager.deleteSave(); // Sửa thành SaveManager.deleteSave()
    }

    /**
     * Reset game về trạng thái ban đầu - dùng khi thoát không lưu
     */
    public void resetGame() {
        startGame(Defs.LEVEL_EASY);
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
            default: return "UNKNOWN";
        }
    }

    // Getters

    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Brick> getBricks() { return bricks; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getCurrentLevel() { return currentLevel; }

    public boolean isGameOver() { return lives <= 0; }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public int getCurrentDifficulty() { return currentDifficulty; }
}
