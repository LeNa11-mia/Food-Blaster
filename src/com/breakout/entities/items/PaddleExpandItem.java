package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * {@code PaddleExpandItem} is an item that increases the width of the
 * {@link com.breakout.entities.Paddle} upon collection.
 * <p>
 * This is a {@code GoodItem} and provides a benefit to the player by making the paddle larger.
 * </p>
 */
public class PaddleExpandItem extends GoodItem {
    /** The multiplier used to increase the paddle's width. Note: The variable name "SHRINK_MULTIPLIER"
     * is misleading given its usage (1.3), but is kept as per the original code to avoid logic changes. */
    private static final double SHRINK_MULTIPLIER = 1.3;

    /**
     * Initializes a new paddle expand item at the specified coordinates.
     *
     * @param x The initial x-coordinate of the item.
     * @param y The initial y-coordinate of the item.
     */
    public PaddleExpandItem(final double x, final double y) {
        super(x, y);
        this.name = "PADDLE EXPAND";
    }

    /**
     * Applies the effect of expanding the paddle's width.
     * <p>
     * The paddle's width is increased by {@code SHRINK_MULTIPLIER} while keeping its center
     * position constant (ensuring smooth expansion).
     * </p>
     *
     * @param paddle The current {@link Paddle} instance whose width will be increased.
     * @param gm The game manager (not directly used in this effect).
     */
    @Override
    public void applyEffect(final Paddle paddle, final GameManager gm) {
        // Calculate the center X-coordinate before resizing to maintain its position.
        final double centerX = paddle.getX() + paddle.getWidth() / 2;

        // Calculate the new width using the multiplier (which is > 1.0, so it expands).
        final double newWidth = paddle.getWidth() * SHRINK_MULTIPLIER;
        final double oldWidth = paddle.getWidth();

        // Adjust the X-coordinate so the paddle expands equally to the left and right.
        paddle.setX(centerX - newWidth / 2);

        // Apply the new width while keeping the height the same.
        paddle.resizeSprite(newWidth, paddle.getHeight());

        // Output the effect details.
        System.out.println("✅" + this.name + " : " + oldWidth + "x" + paddle.getHeight()
                + " → " + newWidth + "x" + paddle.getHeight());
    }
}