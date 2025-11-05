package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

import java.awt.*;

/**
 * Represents a destructible brick object in the Breakout game.
 * <p>
 * Each {@code Brick} has a fixed width and height defined in {@link GameConfig},
 * and maintains two state flags: {@code hit} (whether it has been hit recently)
 * and {@code destroyed} (whether it has been destroyed and should be removed
 * from the game).
 * </p>
 *
 * <p>
 * This class is abstract — concrete subclasses define their specific behavior
 * when hit, via the {@link #hit()} method.
 * </p>
 */
public abstract class Brick extends GameObject {

    /** Whether the brick has been hit in the current frame or event. */
    protected boolean hit;

    /** Whether the brick has been destroyed and should no longer be active. */
    protected boolean destroyed;

    /**
     * Creates a new {@code Brick} object at the specified position.
     * <p>
     * Width and height are automatically set based on {@link GameConfig#BRICK_WIDTH}
     * and {@link GameConfig#BRICK_HEIGHT}.
     * </p>
     *
     * @param x The x-coordinate of the brick (top-left corner).
     * @param y The y-coordinate of the brick (top-left corner).
     */
    public Brick(double x, double y) {
        super(x, y, GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT);
        hit = false;
        destroyed = false;
    }

    /**
     * Updates the brick's state. The base {@code Brick} does nothing by default.
     *
     * @param deltaTime Time elapsed (in seconds) since the last update.
     */
    @Override
    public void update(double deltaTime) {
        // No update behavior for base Brick
    }

    /**
     * Marks this brick as destroyed.
     */
    public void destroy() {
        destroyed = true;
    }

    /**
     * Defines the behavior when the brick is hit by the ball.
     * <p>
     * Each subclass should implement how the brick reacts — for example,
     * changing color, reducing health, or being destroyed immediately.
     * </p>
     */
    public abstract void hit();

    /**
     * Checks whether this brick has been destroyed.
     *
     * @return {@code true} if the brick is destroyed; {@code false} otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Checks whether this brick has been hit (but not necessarily destroyed).
     *
     * @return {@code true} if the brick was hit; {@code false} otherwise.
     */
    public boolean isHit() {
        return hit;
    }
}
