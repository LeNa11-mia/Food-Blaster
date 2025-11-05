package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * {@code PaddleSpeedUpItem} is a positive item (GoodItem) that increases the movement speed
 * of the {@link com.breakout.entities.Paddle} upon collection.
 * <p>
 * This effect allows the player to move the paddle faster, improving control.
 * </p>
 */
public class PaddleSpeedUpItem extends GoodItem {
    /** The multiplier used to increase the paddle's speed (1.3 means 30% increase). */
    private static final double SPEED_MULTIPLIER = 1.3;

    /**
     * Initializes a new paddle speed-up item at the specified coordinates.
     *
     * @param x The initial x-coordinate of the item.
     * @param y The initial y-coordinate of the item.
     */
    public PaddleSpeedUpItem(final double x, final double y) {
        super(x, y);
        this.name = "PADDLE SPEED UP";
    }

    /**
     * Applies the effect of increasing the paddle's speed.
     * <p>
     * The paddle's current speed is multiplied by {@code SPEED_MULTIPLIER}.
     * </p>
     *
     * @param paddle The current {@link Paddle} instance whose speed will be increased.
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