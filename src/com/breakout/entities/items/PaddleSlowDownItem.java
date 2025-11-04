package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

public class PaddleSlowDownItem extends BadItem {
    private static final double SPEED_MULTIPLIER = 0.7;

    public PaddleSlowDownItem(double x, double y) {
        super(x, y);
        name = "PADDLE SLOW DOWN";
    }

    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        double newSpeed = paddle.getSpeed() * SPEED_MULTIPLIER;
        double oldSpeed = paddle.getSpeed();
        paddle.setSpeed(newSpeed);
        System.out.println("✅" + name + " : " + oldSpeed
                + " → " + newSpeed);

        gm.showMessageOnScreen(name + " " + icon);
    }
}
