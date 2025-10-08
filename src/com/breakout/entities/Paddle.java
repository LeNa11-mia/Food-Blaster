package com.breakout.entities;

import java.awt.*;
import com.breakout.core.GameObject;

/**
 * Paddle - moves left and right.
 */
public class Paddle extends GameObject {
    private double speed = 400; // Movement speed
    
    public Paddle(double x, double y) {
        super(x, y, 100, 15); // Paddle size: 100x15 pixels
    }
    
    @Override
    public void update(double deltaTime) {
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.decode("#B19CD8"));
        g.fillRect((int)x, (int)y, (int)width, (int)height);
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