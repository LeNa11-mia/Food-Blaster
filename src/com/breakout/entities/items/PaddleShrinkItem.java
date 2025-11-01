package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

public class PaddleShrinkItem extends BadItem {
    private static final double SHRINK_MULTIPLIER = 0.7;

    public PaddleShrinkItem(double x, double y) {
        super(x, y);
    }

    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        paddle.setWidth(paddle.getWidth() * SHRINK_MULTIPLIER);
    }
}
