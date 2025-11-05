package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;

/**
 * A special type of brick that falls downward when hit instead of being destroyed immediately.
 * <p>
 * The falling speed increases over time due to gravity. The brick is removed once it falls
 * beyond the bottom of the screen.
 */
public class FallingBrick extends Brick {

    /** The current falling velocity (pixels/second). */
    private double velocity;

    /** Whether the brick is currently falling. */
    private boolean falling;

    /**
     * Creates a new FallingBrick at the specified position.
     *
     * @param x the x-position of the brick
     * @param y the y-position of the brick
     */
    public FallingBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.FALLING_BRICK_IMAGE;
        velocity = 0;
        falling = false;
    }

    /**
     * Updates the brick's movement if it is falling.
     * <p>
     * The brick accelerates downward based on gravity. When it moves beyond the
     * screen boundary, it is considered destroyed.
     *
     * @param deltaTime time elapsed since the last frame in seconds
     */
    @Override
    public void update(double deltaTime) {
        if (falling) {
            if (y < GameConfig.SCREEN_HEIGHT) {
                velocity += GameConfig.GRAVITY * deltaTime;
                y += velocity * deltaTime;
            } else {
                destroyed = true;
            }
        }
    }

    /**
     * Called when the brick is hit by the ball.
     * <p>
     * Instead of breaking immediately, the brick begins falling downward.
     */
    @Override
    public void hit() {
        hit = true;
        falling = true;
    }
}
