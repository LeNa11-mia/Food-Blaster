package com.breakout.managers;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.entities.*;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.bricks.FallingBrick;
import com.breakout.entities.items.Item;

import java.util.*;
import java.util.List;

/**
 * Gameplay manager
 */
public class GameManager {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<Item> activeItems;

    private int score;
    private int lives;
    private int currentDifficulty;
    private boolean laserEnabled;

    public GameManager() {
        // Initialize game objects
        ball = new Ball(GameConfig.SCREEN_WIDTH/2.0, GameConfig.SCREEN_HEIGHT/2.0);
        paddle = new Paddle(GameConfig.SCREEN_WIDTH/2.0 - 50, GameConfig.SCREEN_HEIGHT - 50);
        bricks = new ArrayList<>();
        activeItems = new ArrayList<>();
        lives = 1; // Only 1 life as per your requirement
    }

    public void addItem(Item item) {
        this.activeItems.add(item);
    }

    public List<Item> getActiveItems() {
        return activeItems;
    }

    public void startGame(int difficulty) {
        currentDifficulty = difficulty;
        bricks = Level.loadLevel(difficulty);
        resetBall();
        resetPaddle();
        lives = 1;
        score = 0;
    }

    public void update(Game game, double deltaTime, boolean leftPressed, boolean rightPressed) {
        if (isGameOver()) {
            game.changeState(Defs.STATE_GAMEOVER);
            return; // Don't update if game is over
        } else if (isWin()) {
            game.changeState(Defs.STATE_WIN);
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
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= GameConfig.SCREEN_WIDTH) {
            ball.bounceX();
        }

        // Ball hits top wall
        if (ball.getY() <= 0) {
            ball.bounceY();
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
        }

        // Collision with bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && ball.intersects(brick)) {
                brick.hit();
                ball.bounceY();
                break; // Only destroy one brick per collision
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

    public int getNextDifficulty() {
        if (currentDifficulty < Defs.LEVEL_BOSS) {
            currentDifficulty++;
        }
        return currentDifficulty;
    }

    private void resetBall() {
        ball = new Ball(GameConfig.SCREEN_WIDTH/2.0, GameConfig.SCREEN_HEIGHT/2.0);
    }

    private void resetPaddle() {
        paddle = new Paddle(GameConfig.SCREEN_WIDTH/2.0 - 50, GameConfig.SCREEN_HEIGHT - 50);
    }

    private void spawnRandomItem(double x, double y) {
    }

    public void addExtraBall() {
    }

    public void enableLaser() {
        laserEnabled = true;
        // Laser shooting logic:
    }

    private void nextLevel() {
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
    public boolean isGameOver() { return lives <= 0; }
    public int getCurrentDifficulty() { return currentDifficulty; }
}