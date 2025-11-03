package com.breakout.entities;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

/**
 * Paddle - moves left and right.
 */
public class Paddle extends GameObject {
    private double speed = 400; // Movement speed

    public Paddle(double x, double y) {
        super(x, y, GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_HEIGHT); // Paddle size: 100x15 pixels
        sprite = GameConfig.PADDLE_IMAGE;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void update(double deltaTime) {
    }

    public void moveLeft(double deltaTime, double screenWidth) {
        x -= speed * deltaTime;
        if (x < 0) x = 0; // Keep within screen bounds
    }

    public void moveRight(double deltaTime, double screenWidth) {
        x += speed * deltaTime;
        double maxX = screenWidth - width - 12;
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
