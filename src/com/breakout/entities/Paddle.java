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
    
    @Override
    public void update(double deltaTime) {
    }
    
    public void moveLeft(double deltaTime, double screenWidth) {
        x -= speed * deltaTime;
        if (x < 0) x = 0; // Keep within screen bounds
    }
    
    public void moveRight(double deltaTime, double screenWidth) {
        x += speed * deltaTime;
        if (x + width > screenWidth) {
            x = screenWidth - width;
        }
    }
}