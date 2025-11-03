package com.breakout.core;

import javax.swing.*;

/**
 * Base class for all game objects.
 */
public abstract class GameObject {
    protected double x, y;
    protected double width, height;
    protected ImageIcon sprite; // Object sprite image
    
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public ImageIcon getSprite() { return sprite; }

    public abstract void update(double deltaTime);
    
    // Simple collision detection
    public boolean intersects(GameObject other) {
        return x < other.x + other.width &&
               x + width > other.x &&
               y < other.y + other.height &&
               y + height > other.y;
    }
}