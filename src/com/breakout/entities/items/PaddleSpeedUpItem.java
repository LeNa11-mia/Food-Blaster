package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

public class PaddleSpeedUpItem extends GoodItem {
    private static final double SPEED_MULTIPLIER = 1.3;

    public PaddleSpeedUpItem(double x, double y) {
        super(x, y);
        name = "PADDLE SPEED UP";
    }

    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        double newSpeed = paddle.getSpeed() * SPEED_MULTIPLIER;
        double oldSpeed = paddle.getSpeed();
        paddle.setSpeed(newSpeed);
        System.out.println("✅" + name + " : " + oldSpeed
                + " → " + newSpeed);
    }
}
