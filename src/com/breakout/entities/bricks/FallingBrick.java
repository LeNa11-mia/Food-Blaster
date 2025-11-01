package com.breakout.entities.bricks;

import com.breakout.Game;
import com.breakout.config.GameConfig;

public class FallingBrick extends Brick {
    private double velocity;
    private boolean falling;

    public FallingBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.FALLING_BRICK_IMAGE;
        velocity = 0;
        falling = false;
    }

    @Override
    public void update(double deltaTime) {
        if (falling) {
            if (y < GameConfig.SCREEN_HEIGHT) {
                velocity += GameConfig.GRAVITY * deltaTime; // Fall faster over time
                y += velocity * deltaTime;
            } else {
                destroyed = true; // Destroyed when fall out of window
            }
        }
    }

    @Override
    public void hit() {
        falling = true; // Fall off when hit
    }
}
