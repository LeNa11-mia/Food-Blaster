package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.interfaces.Destructible;

public class NormalBrick extends Brick implements Destructible {

    public NormalBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.NORMAL_BRICK_IMAGE;
    }

    @Override
    public void update(double deltaTime) {
        // Bricks do not move
    }

    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
        // Bricks are destroyed when hit (default)
    }

    @Override
    public void onDestroyed() {} // No effect
}
