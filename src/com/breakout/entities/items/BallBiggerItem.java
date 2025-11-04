package com.breakout.entities.items;

import com.breakout.entities.Ball;
import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

public class BallBiggerItem extends GoodItem {
    private static final double SIZE_MULTIPLIER = 1.3;

    public BallBiggerItem(double x, double y) {
        super(x, y);
        name = "BALL BIGGER";
    }

    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        Ball ball = gm.getBall();

        double oldWidth = ball.getWidth();
        double oldHeight = ball.getHeight();

        double newWidth = oldWidth * SIZE_MULTIPLIER;
        double newHeight = oldHeight * SIZE_MULTIPLIER;

        ball.resizeSprite(newWidth, newHeight);

        String msg = "✅" + name + " : " + String.format("%.2f", oldWidth) + "x"
                + String.format("%.2f", oldHeight) + " → "
                + String.format("%.2f", newWidth) + " x "
                + String.format("%.2f", newHeight);
        System.out.println(msg);
    }
}