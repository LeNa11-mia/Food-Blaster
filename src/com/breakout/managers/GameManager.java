package com.breakout.managers;

import com.breakout.Main;
import com.breakout.entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class GameManager {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;

    private int score;
    private int lives;
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

    public void startGame() {
        bricks = Level.loadLevel(1);
        lives = 1;
        score = 0;
        gameOver = false;
    }

    public void update(double deltaTime, boolean leftPressed, boolean rightPressed) {
        if (gameOver) return; // Don't update if game is over

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
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= Main.WIDTH) {
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

    private void resetBall() {
        ball = new Ball(Main.WIDTH/2, Main.HEIGHT/2);
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

    // Getters
    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Brick> getBricks() { return bricks; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return gameOver; }
}