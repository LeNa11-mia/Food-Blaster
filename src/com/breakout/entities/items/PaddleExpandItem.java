package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * Beneficial item that increases the paddle's width when collected.
 * Extends GoodItem to provide positive gameplay effects.
 * Expands the paddle size to make it easier to hit the ball.
 * Maintains the paddle's center position during expansion.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class PaddleExpandItem extends GoodItem {
    /** Multiplier applied to the paddle's current width */
    private static final double SHRINK_MULTIPLIER = 1.3;

    /**
     * Constructs a paddle expand item at the specified position.
     *
     * @param x the x-coordinate of the item's spawn position
     * @param y the y-coordinate of the item's spawn position
     */
    public PaddleExpandItem(double x, double y) {
        super(x, y);
        name = "PADDLE EXPAND";
    }

    /**
     * Applies the expansion effect to the paddle when collected.
     * Increases the paddle's width by the defined multiplier while maintaining center position.
     * Updates the paddle's visual sprite to match the new dimensions.
     * Provides console feedback about the size change.
     *
     * @param paddle the paddle entity that collected the item
     * @param gm the game manager for accessing game state and logic
     */
    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        double centerX = paddle.getX() + paddle.getWidth() / 2;
        double oldWidth = paddle.getWidth();
        double newWidth = oldWidth * SHRINK_MULTIPLIER;

        // Update paddle position to maintain center alignment
        paddle.setX(centerX - newWidth / 2);

        // Resize the paddle sprite to match new dimensions
        paddle.resizeSprite(newWidth, paddle.getHeight());

        System.out.println("✅" + name + " : " + oldWidth + "x" + paddle.getHeight()
                + " → " + newWidth + "x" + paddle.getHeight());
    }
}
