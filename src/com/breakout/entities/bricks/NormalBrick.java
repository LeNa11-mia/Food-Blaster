package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.interfaces.Destructible;

/**
 * Represents a standard brick in the game.
 * <p>
 * A {@code NormalBrick} is a basic brick type that is destroyed immediately when hit
 * and does not trigger any special effects.
 * </p>
 */
public class NormalBrick extends Brick implements Destructible {

    /**
     * Constructs a {@code NormalBrick} at the specified position.
     *
     * @param x the x-coordinate of the brick
     * @param y the y-coordinate of the brick
     */
    public NormalBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.NORMAL_BRICK_IMAGE;
    }

    /**
     * Updates the brick state. For a normal brick, this method does nothing
     * since normal bricks do not move or change over time.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    @Override
    public void update(double deltaTime) {
        // Normal bricks are static; no update logic needed.
    }

    /**
     * Called when the brick is hit by the ball.
     * Marks the brick as destroyed immediately and triggers {@link #onDestroyed()}.
     */
    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
    }

    /**
     * Called when the brick is destroyed.
     * For {@code NormalBrick}, this method has no additional effect.
     */
    @Override
    public void onDestroyed() {
        // No special effect for normal bricks.
    }
}
