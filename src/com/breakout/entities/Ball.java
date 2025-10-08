package com.breakout.entities;

import java.awt.*;
import com.breakout.core.GameObject;

/**
 * Ball - handles movement and rendering.
 */
public class Ball extends GameObject {
    private double vx = 200; // X velocity
    private double vy = 200; // Y velocity
    
    public Ball(double x, double y) {
        super(x, y, 15, 15); // Ball size: 15x15 pixels
    }
    
    @Override
    public void update(double deltaTime) {
        // Move the ball
        x += vx * deltaTime;
        y += vy * deltaTime;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval((int)x, (int)y, (int)width, (int)height);
    }
    
    // Reverse direction when hitting walls
    public void bounceX() { vx = -vx; }
    public void bounceY() { vy = -vy; }
    
    public double getVx() { return vx; }
    public double getVy() { return vy; }
}