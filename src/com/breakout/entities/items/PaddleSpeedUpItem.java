package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

public class PaddleSpeedUpItem extends GoodItem {
    private static final double SPEED_MULTIPLIER = 1.3;

    public PaddleSpeedUpItem(double x, double y) {
        super(x, y);
    }

    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        paddle.setSpeed(paddle.getSpeed() * SPEED_MULTIPLIER);
    }
}
