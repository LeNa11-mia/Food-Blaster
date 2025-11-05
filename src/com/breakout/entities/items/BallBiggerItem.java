package com.breakout.entities.items;

import com.breakout.entities.Ball;
import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * {@code BallBiggerItem} is an item that increases the size of the ball
 * ({@link com.breakout.entities.Ball}) upon collision with the paddle
 * ({@link com.breakout.entities.Paddle}).
 * <p>
 * This is a {@code GoodItem} and benefits the player.
 * </p>
 */
public class BallBiggerItem extends GoodItem {
    /** The size multiplier applied to the ball's dimensions. */
    private static final double SIZE_MULTIPLIER = 1.3;

    /**
     * Initializes a new ball-bigger item at the specified coordinates.
     *
     * @param x The initial x-coordinate of the item.
     * @param y The initial y-coordinate of the item.
     */
    public BallBiggerItem(final double x, final double y) {
        super(x, y);
        this.name = "BALL BIGGER";
    }

    /**
     * Applies the effect of increasing the ball's size.
     * <p>
     * The current {@link Ball} object retrieved from the {@link GameManager} is
     * resized by the {@code SIZE_MULTIPLIER}.
     * </p>
     *
     * @param paddle The current paddle (not directly used by this effect).
     * @param gm The game manager, used to access the current {@link Ball} instance.
     */
    @Override
    public void applyEffect(final Paddle paddle, final GameManager gm) {
        final Ball ball = gm.getBall();

        final double oldWidth = ball.getWidth();
        final double oldHeight = ball.getHeight();

        final double newWidth = oldWidth * SIZE_MULTIPLIER;
        final double newHeight = oldHeight * SIZE_MULTIPLIER;

        ball.resizeSprite(newWidth, newHeight);

        // Uses local final variable for message string for clarity and adherence to final modifier usage.
        final String msg = "✅" + this.name + " : "
                + String.format("%.2f", oldWidth) + "x"
                + String.format("%.2f", oldHeight) + " → "
                + String.format("%.2f", newWidth) + " x "
                + String.format("%.2f", newHeight);

        System.out.println(msg);
    }
}