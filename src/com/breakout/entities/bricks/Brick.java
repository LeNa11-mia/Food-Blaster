package com.breakout.entities.bricks;

import com.breakout.core.GameObject;
import com.breakout.interfaces.Destructible;

import javax.swing.*;

/**
 * Brick - can be drawn and destroyed.
 */
public class Brick extends GameObject implements Destructible {
    protected boolean destroyed = false;
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

    @Override
    public void hit() {
        destroyed = true;
        onDestroyed();
        // Bricks are destroyed when hit (default)
    }

    @Override
    public void onDestroyed() {} // No effect

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
