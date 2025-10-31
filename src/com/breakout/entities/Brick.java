package com.breakout.entities;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

/**
 * Brick - can be drawn and destroyed.
 */
public class Brick extends GameObject {
    private boolean destroyed = false;
    
    public Brick(double x, double y) {
        super(x, y, GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT);
        sprite = GameConfig.NORMAL_BRICK_IMAGE;
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
