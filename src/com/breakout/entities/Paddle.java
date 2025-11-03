package com.breakout.entities;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

/**
 * Paddle - moves left and right.
 */
public class Paddle extends GameObject {
    private double speed;
    private double vx; // Paddle chỉ di chuyển theo chiều ngang

    public Paddle(double x, double y) {
        super(x, y, GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_HEIGHT); // Paddle size: 100x15 pixels
        sprite = GameConfig.PADDLE_IMAGE;
        speed = GameConfig.PADDLE_SPEED;
        vx = 0;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getVx() {
        return vx;
    }

    @Override
    public void update(double deltaTime) {
        // Control paddle
        if (Game.getGame().getKeyListener().isLeftPressed()) {
            moveLeft(deltaTime, GameConfig.SCREEN_WIDTH);
        }
        if (Game.getGame().getKeyListener().isRightPressed()) {
            moveRight(deltaTime, GameConfig.SCREEN_WIDTH);
        }
    }

    public void moveLeft(double deltaTime, double screenWidth) {
        vx = -speed;
        x += vx * deltaTime;
        if (x < 0) {
            x = 0; // Keep within screen bounds
        }
    }

    public void moveRight(double deltaTime, double screenWidth) {
        vx = speed;
        x += vx * deltaTime;
        double maxX = screenWidth - width - 15;
        if (x > maxX) {
            x = maxX;
        }
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWidth() {
        return super.getWidth();
    }
}
