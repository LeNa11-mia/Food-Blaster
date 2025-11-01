package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.interfaces.Destructible;

import javax.swing.*;

/**
 * Brick - can be drawn and destroyed.
 */
public class Brick extends GameObject implements Destructible {
    protected boolean destroyed = false;
    
    public Brick(double x, double y) {
        super(x, y, GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT);
        sprite = GameConfig.NORMAL_BRICK_IMAGE;
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
