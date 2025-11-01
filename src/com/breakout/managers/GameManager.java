package com.breakout.managers;

import com.breakout.Game;
import com.breakout.Main;
import com.breakout.entities.*;

import java.util.*;
import java.util.List;

/**
 * Gameplay manager
 */
public class GameManager {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;

    private int score;
    private int lives;
    private String currentDifficulty = "EASY";
    private int currentLevel = 1;
    private boolean laserEnabled;
    private boolean gameOver;

    public GameManager() {
        // Initialize game objects
        ball = new Ball(Main.WIDTH/2, Main.HEIGHT/2);
        paddle = new Paddle(Main.WIDTH/2 - 50, Main.HEIGHT - 50);
        bricks = new ArrayList<>();
        lives = 1; // Only 1 life as per your requirement
        gameOver = false;
    }

    private boolean paused = false;
    private boolean ballStarted = false;

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

    public void startGame(String difficulty) {
        this.currentDifficulty = difficulty;
        this.currentLevel = 1;

        if (difficulty.equals("EASY")) {
            bricks = Level.loadLevel(1);
        } else if (difficulty.equals("MEDIUM")) {
            bricks = Level.loadLevel(2);
        } else if (difficulty.equals("HARD")) {
            bricks = Level.loadLevel(3);
        } else if (difficulty.equals("BOSS")) {
            bricks = Level.loadLevel(4);
        }
        resetBall();
        resetPaddle();
        lives = 1;
        score = 0;
        gameOver = false;
        ballStarted = false;
        paused = false;

        // Xóa save cũ khi bắt đầu game mới
        SaveManager.deleteSave();
    }

    public void pauseToMenu(Game game) {
        paused = true;          // ngừng update
        saveCurrentGame();      // lưu trạng thái game
        game.changeState(GameState.MENU); // quay về menu
    }

    public void continueGame() {
        GameSave savedGame = SaveManager.loadGame();
        if (savedGame != null) {
            loadSavedGame(savedGame);

            paused = false;      // cho phép update trở lại
            ballStarted = false; // bóng dừng → chờ SPACE chạy tiếp
        }
    }


    public void update(Game game, double deltaTime, boolean leftPressed, boolean rightPressed) {

        if (paused) return;

        if (!ballStarted) return;

        if (isGameOver()) {
            // Lưu game trước khi chuyển sang GAMEOVER
            saveCurrentGame();
            game.changeState(GameState.GAMEOVER);
            return; // Don't update if game is over
        } else if (isWin()) {
            // Lưu game trước khi chuyển sang WIN
            saveCurrentGame();
            game.changeState(GameState.WIN);
            return;
        }

        // Update ball
        ball.update(deltaTime);

        // Control paddle
        if (leftPressed) {
            paddle.moveLeft(deltaTime, Main.WIDTH);
        }
        if (rightPressed) {
            paddle.moveRight(deltaTime, Main.WIDTH);
        }

        // Ball hits left/right walls
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= Main.WIDTH-15) {
            ball.bounceX();
        }

        // Ball hits top wall
        if (ball.getY() <= 0) {
            ball.bounceY();
        }

        // Ball falls below bottom border - GAME OVER
        if (ball.getY() > Main.HEIGHT) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
            } else {
                // Reset ball if still have lives (though you have only 1 life)
                resetBall();
            }
        }

        // Collision with paddle
        if (ball.intersects(paddle) && ball.getVy() > 0) {
            ball.bounceY();
        }

        // Collision with bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && ball.intersects(brick)) {
                brick.hit();
                ball.bounceY();
                score += 1;
                break; // Only destroy one brick per collision
            }
        }
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

        this.currentDifficulty = gameSave.getDifficulty();
        this.score = gameSave.getScore();
        this.lives = gameSave.getLives();
        this.currentLevel = gameSave.getLevel();

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

        this.gameOver = false;
    }

    /**
     * Load level phù hợp cho game đã lưu
     */
    private void loadLevelForSavedGame() {
        if (bricks == null || bricks.isEmpty()) {
            switch (currentDifficulty) {
                case "EASY":
                    bricks = Level.loadLevel(1);
                    break;
                case "MEDIUM":
                    bricks = Level.loadLevel(2);
                    break;
                case "HARD":
                    bricks = Level.loadLevel(3);
                    break;
                case "BOSS":
                    bricks = Level.loadLevel(4);
                    break;
            }
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
//    public void continueGame() {
//        GameSave savedGame = SaveManager.loadGame();
//        if (savedGame != null) {
//            loadSavedGame(savedGame);
//        }
//    }


    public String getNextDifficulty() {
        switch (currentDifficulty) {
            case "EASY": return "MEDIUM";
            case "MEDIUM": return "HARD";
            case "HARD": return "BOSS";
            case "BOSS": return null;
            default: return null;
        }
    }

    private void resetBall() {
        ball = new Ball(Main.WIDTH/2, Main.HEIGHT/2);
        ballStarted = false;
    }

    private void resetPaddle() {
        paddle = new Paddle(Main.WIDTH/2 - 50, Main.HEIGHT - 50);
    }

    private void spawnRandomItem(double x, double y) {
        // Logic spawn item (có thể thêm sau)
    }

    public void addExtraBall() {
        // Logic thêm bóng (có thể thêm sau)
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


    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Brick> getBricks() { return bricks; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public String getDifficulty() { return currentDifficulty; }
    public int getCurrentLevel() { return currentLevel; }
    public boolean isGameOver() { return gameOver; }

    /**
     * Lấy thông tin game đã lưu để hiển thị
     */
    public String getSaveInfo() {
        return SaveManager.getSaveInfo();
    }
}
