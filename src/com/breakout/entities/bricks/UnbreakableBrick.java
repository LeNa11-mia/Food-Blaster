package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;

public class UnbreakableBrick extends Brick {

    public UnbreakableBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.UNBREAKABLE_BRICK_IMAGE;
    }

    @Override
    public void hit() {
        // Cannot be destroyed
    }
}