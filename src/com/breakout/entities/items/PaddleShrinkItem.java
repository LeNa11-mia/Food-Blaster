package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * {@code PaddleShrinkItem} is an item that decreases the width of the
 * {@link com.breakout.entities.Paddle} upon collection.
 * <p>
 * This is a {@code BadItem} and hinders the player by making the paddle smaller.
 * </p>
 */
public class PaddleShrinkItem extends BadItem {
    /** The multiplier used to decrease the paddle's width (0.7 means 30% reduction). */
    private static final double SHRINK_MULTIPLIER = 0.7;

    /**
     * Initializes a new paddle shrink item at the specified coordinates.
     *
     * @param x The initial x-coordinate of the item.
     * @param y The initial y-coordinate of the item.
     */
    public PaddleShrinkItem(final double x, final double y) {
        super(x, y);
        this.name = "PADDLE SHRINK";
    }

    /**
     * Applies the effect of shrinking the paddle's width.
     * <p>
     * The paddle's width is decreased by {@code SHRINK_MULTIPLIER} while keeping its center
     * position constant (ensuring the shrink is balanced on both sides).
     * </p>
     *
     * @param paddle The current {@link Paddle} instance whose width will be decreased.
     * @param gm The game manager (not directly used in this effect).
     */
    @Override
    public void applyEffect(final Paddle paddle, final GameManager gm) {
        // Calculate the center X-coordinate before resizing to maintain its position.
        final double centerX = paddle.getX() + paddle.getWidth() / 2;

        // Calculate the new width using the multiplier.
        final double newWidth = paddle.getWidth() * SHRINK_MULTIPLIER;
        final double oldWidth = paddle.getWidth();

        // Adjust the X-coordinate so the paddle shrinks equally toward the center.
        paddle.setX(centerX - newWidth / 2);

        // Apply the new width while keeping the height the same.
        paddle.resizeSprite(newWidth, paddle.getHeight());

        // Output the effect details.
        System.out.println("✅" + this.name + " : " + oldWidth + "x" + paddle.getHeight()
                + " → " + newWidth + "x" + paddle.getHeight());
    }
}