package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * {@code PaddleSlowDownItem} is a negative item (BadItem) that reduces the movement speed
 * of the {@link com.breakout.entities.Paddle} upon collection.
 * <p>
 * This effect makes it harder for the player to control the paddle effectively.
 * </p>
 */
public class PaddleSlowDownItem extends BadItem {
    /** The multiplier used to decrease the paddle's speed (0.7 means 30% reduction). */
    private static final double SPEED_MULTIPLIER = 0.7;

    /**
     * Initializes a new paddle slow-down item at the specified coordinates.
     *
     * @param x The initial x-coordinate of the item.
     * @param y The initial y-coordinate of the item.
     */
    public PaddleSlowDownItem(final double x, final double y) {
        super(x, y);
        this.name = "PADDLE SLOW DOWN";
    }

    /**
     * Applies the effect of decreasing the paddle's speed.
     * <p>
     * The paddle's current speed is multiplied by {@code SPEED_MULTIPLIER}.
     * </p>
     *
     * @param paddle The current {@link Paddle} instance whose speed will be reduced.
     * @param gm The game manager (not directly used in this effect).
     */
    @Override
    public void applyEffect(final Paddle paddle, final GameManager gm) {
        final double newSpeed = paddle.getSpeed() * SPEED_MULTIPLIER;
        final double oldSpeed = paddle.getSpeed();

        paddle.setSpeed(newSpeed);

        // Output the effect details, showing the speed change.
        System.out.println("✅" + this.name + " : " + oldSpeed
                + " → " + newSpeed);
    }
}