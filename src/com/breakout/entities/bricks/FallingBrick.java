package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;

/**
 * Represents a special {@link Brick} that starts falling when hit.
 * <p>
 * Instead of being destroyed immediately upon impact, the {@code FallingBrick}
 * begins to fall downwards under the influence of gravity. Once it falls outside
 * the visible screen area, it is marked as destroyed.
 * </p>
 *
 * <p>
 * The brick uses gravity and screen boundaries defined in {@link GameConfig}.
 * </p>
 */
public class FallingBrick extends Brick {

    /** The vertical velocity of the brick, in pixels per second. */
    private double velocity;

    /** Whether the brick is currently falling. */
    private boolean falling;

    /**
     * Creates a new {@code FallingBrick} at the given position.
     *
     * @param x The x-coordinate of the brick (top-left corner).
     * @param y The y-coordinate of the brick (top-left corner).
     */
    public FallingBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.FALLING_BRICK_IMAGE;
        velocity = 0;
        falling = false;
    }

    /**
     * Updates the position and velocity of the brick over time.
     * <p>
     * If the brick is currently falling, its velocity increases due to gravity,
     * and its vertical position is updated. When the brick moves past the
     * bottom of the screen, it is marked as destroyed.
     * </p>
     *
     * @param deltaTime Time elapsed (in seconds) since the last frame update.
     */
    @Override
    public void update(double deltaTime) {
        if (falling) {
            if (y < GameConfig.SCREEN_HEIGHT) {
                velocity += GameConfig.GRAVITY * deltaTime; // Fall faster over time
                y += velocity * deltaTime;
            } else {
                destroyed = true; // Destroyed when it falls out of the window
            }
        }
    }

    /**
     * Called when the brick is hit by the ball.
     * <p>
     * Instead of breaking immediately, this brick begins to fall
     * under gravity, allowing visual movement before destruction.
     * </p>
     */
    @Override
    public void hit() {
        hit = true;
        falling = true; // Starts falling instead of being destroyed instantly
    }
}
