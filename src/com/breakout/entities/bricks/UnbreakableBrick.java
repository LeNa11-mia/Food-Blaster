package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;

/**
 * Represents a brick that cannot be destroyed.
 * <p>
 * An {@code UnbreakableBrick} remains intact regardless of how many times it is hit.
 * It is primarily used as an indestructible obstacle in the game field.
 * </p>
 */
public class UnbreakableBrick extends Brick {

    /**
     * Constructs an {@code UnbreakableBrick} at the specified position.
     *
     * @param x the x-coordinate of the brick
     * @param y the y-coordinate of the brick
     */
    public UnbreakableBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.UNBREAKABLE_BRICK_IMAGE;
    }

    /**
     * Called when the brick is hit by the ball.
     * <p>
     * For {@code UnbreakableBrick}, this method does nothing since
     * the brick cannot be destroyed.
     * </p>
     */
    @Override
    public void hit() {
        // Unbreakable bricks ignore all hits.
    }
}
