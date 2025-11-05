package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * A negative item that slows down the player's paddle when collected.
 * <p>
 * When applied, the paddle's speed is reduced to 70% of its current speed,
 * making the game more challenging.
 */
public class PaddleSlowDownItem extends BadItem {

    /** The multiplier used to reduce the paddle's speed. */
    private static final double SPEED_MULTIPLIER = 0.7;

    /**
     * Creates a new PaddleSlowDownItem at the specified position.
     *
     * @param x the x-position where the item will appear
     * @param y the y-position where the item will appear
     */
    public PaddleSlowDownItem(double x, double y) {
        super(x, y);
        name = "PADDLE SLOW DOWN";
    }

    /**
     * Applies the slowdown effect to the paddle.
     * <p>
     * The paddle's speed is multiplied by {@link #SPEED_MULTIPLIER}.
     *
     * @param paddle the player's paddle affected by this item
     * @param gm     the game manager (unused, but included for consistency)
     */
    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        double oldSpeed = paddle.getSpeed();
        double newSpeed = oldSpeed * SPEED_MULTIPLIER;
        paddle.setSpeed(newSpeed);

        System.out.println("✅ " + name + " : " + oldSpeed + " → " + newSpeed);
    }
}
