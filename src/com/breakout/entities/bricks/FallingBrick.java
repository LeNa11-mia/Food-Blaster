package com.breakout.entities.bricks;

import com.breakout.Main;

public class FallingBrick extends Brick {
    private static final double GRAVITY = 980;
    private double velocity;
    private boolean falling;

    public FallingBrick(double x, double y) {
        super(x, y);
        velocity = 0;
        falling = false;
    }

    @Override
    public void update(double deltaTime) {
        if (falling) {
            if (y < Main.HEIGHT) {
                velocity += GRAVITY * deltaTime; // Fall faster over time
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
