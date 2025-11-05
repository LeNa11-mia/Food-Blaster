package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

/**
 * Represents a brick in the Breakout game.
 * <p>
 * A brick can be hit by the ball and potentially destroyed. Different
 * brick types may respond differently when hit, so subclasses define
 * their own behavior through the {@link #hit()} method.
 */
public abstract class Brick extends GameObject {

    /** Whether this brick has been hit at least once. */
    protected boolean hit;

    /** Whether this brick has been destroyed. */
    protected boolean destroyed;

    /**
     * Creates a new Brick at the given position using the default brick dimensions.
     *
     * @param x the x-position of the brick
     * @param y the y-position of the brick
     */
    public Brick(double x, double y) {
        super(x, y, GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT);
        hit = false;
        destroyed = false;
    }

    /**
     * Updates the brick state.
     * <p>
     * Most bricks do not require per-frame updates, so this implementation is empty.
     * Subclasses may override if needed.
     *
     * @param deltaTime time elapsed since last frame in seconds
     */
    @Override
    public void update(double deltaTime) {}

    /**
     * Marks the brick as destroyed.
     * After being destroyed, it should not be drawn or collide with objects.
     */
    public void destroy() {
        destroyed = true;
    }

    /**
     * Called when the brick is hit by the ball.
     * <p>
     * Each subclass defines how it reacts to being hit:
     * e.g. losing durability, changing sprite, dropping power-up, etc.
     */
    public abstract void hit();

    /**
     * Returns whether the brick has been destroyed.
     *
     * @return true if destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Returns whether the brick has been hit at least once.
     *
     * @return true if hit, false otherwise
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Returns the remaining health of the brick.
     * <p>
     * Default bricks always have only 1 health point.
     *
     * @return 1 if intact, 0 if destroyed
     */
    public int getHealth() {
        return destroyed ? 0 : 1;
    }

    /**
     * Sets the health of the brick.
     * <p>
     * Since a normal brick only has 1 health, any value ≤ 0 destroys the brick.
     *
     * @param health new health value
     */
    public void setHealth(int health) {
        destroyed = (health <= 0);
    }

    /**
     * Returns the row index of the brick in the grid based on its y-position.
     *
     * @return row index of this brick
     */
    public int getRow() {
        return (int) (getY() / GameConfig.BRICK_HEIGHT);
    }

    /**
     * Returns the column index of the brick in the grid based on its x-position.
     *
     * @return column index of this brick
     */
    public int getCol() {
        return (int) (getX() / GameConfig.BRICK_WIDTH);
    }
}
