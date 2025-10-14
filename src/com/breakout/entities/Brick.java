package com.breakout.entities;

import com.breakout.core.GameObject;

import javax.swing.*;

/**
 * Brick - can be drawn and destroyed.
 */
public class Brick extends GameObject {
    private boolean destroyed = false;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 20;
    
    public Brick(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        sprite = new ImageIcon("assets/brick.png");
    }
    
    @Override
    public void update(double deltaTime) {
        // Bricks do not move
    }
    
    public void hit() {
        destroyed = true;
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
}
