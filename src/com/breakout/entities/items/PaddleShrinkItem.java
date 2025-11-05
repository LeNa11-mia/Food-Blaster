package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * A negative item that decreases the paddle's width when collected.
 * <p>
 * The paddle is shrunk to 70% of its current width, making it harder
 * for the player to hit the ball.
 */
public class PaddleShrinkItem extends BadItem {

    /** The multiplier used to reduce the paddle's width. */
    private static final double SHRINK_MULTIPLIER = 0.7;

    /**
     * Creates a new PaddleShrinkItem at the specified position.
     *
     * @param x the x-position where the item will appear
     * @param y the y-position where the item will appear
     */
    public PaddleShrinkItem(double x, double y) {
        super(x, y);
        name = "PADDLE SHRINK";
    }

    /**
     * Applies the shrinking effect to the paddle.
     * <p>
     * The paddle keeps its center position while its width is reduced.
     *
     * @param paddle the player's paddle to shrink
     * @param gm     the game manager (not used but included for consistency)
     */
    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        double centerX = paddle.getX() + paddle.getWidth() / 2;
        double oldWidth = paddle.getWidth();
        double newWidth = oldWidth * SHRINK_MULTIPLIER;

        // Adjust position so paddle stays centered after resizing
        paddle.setX(centerX - newWidth / 2);
        paddle.resizeSprite(newWidth, paddle.getHeight());

        System.out.println("✅ " + name + " : " + oldWidth + "x" + paddle.getHeight()
                + " → " + newWidth + "x" + paddle.getHeight());
    }
}
