package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

public class PaddleShrinkItem extends BadItem {
    private static final double SHRINK_MULTIPLIER = 0.7;

    public PaddleShrinkItem(double x, double y) {
        super(x, y);
        name = "PADDLE SHRINK";
    }

    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        double centerX = paddle.getX() + paddle.getWidth() / 2;
        double newWidth = paddle.getWidth() * SHRINK_MULTIPLIER;
        double oldWidth = paddle.getWidth();
        paddle.setX(centerX - newWidth / 2);
        paddle.resizeSprite(newWidth, paddle.getHeight());
        System.out.println("✅" + name + " : " + oldWidth + "x" + paddle.getHeight()
                + " → " + newWidth + "x" + paddle.getHeight());
    }
}
