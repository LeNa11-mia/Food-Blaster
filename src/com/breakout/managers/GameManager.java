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


    public GameManager() {
        // Initialize game objects
        ball = new Ball(Main.WIDTH/2, Main.HEIGHT/2);
        paddle = new Paddle(Main.WIDTH/2 - 50, Main.HEIGHT - 50);
        bricks = new ArrayList<>();
    }

    public void startGame() {
        bricks = Level.loadLevel(1);
    }

    public void update(double deltaTime, boolean leftPressed, boolean rightPressed) {
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

        // Ball falls below - reset
        if (ball.getY() > Main.HEIGHT) {
            ball = new Ball(Main.WIDTH/2, Main.HEIGHT/2);
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

    // Getters
    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Brick> getBricks() { return bricks; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return lives <= 0; }
}
