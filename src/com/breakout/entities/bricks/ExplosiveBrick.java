package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.interfaces.Destructible;

import java.util.List;

/**
 * Represents a special {@link Brick} that explodes upon destruction.
 * <p>
 * When hit, this brick triggers an explosion that affects other bricks
 * within a defined radius. All nearby bricks within that radius
 * will also be hit or destroyed, depending on their own behavior.
 * </p>
 *
 * <p>
 * The explosion radius is defined in {@link GameConfig#EXPLOSION_RADIUS},
 * and the explosion area is calculated in brick units based on
 * {@link GameConfig#BRICK_WIDTH} and {@link GameConfig#BRICK_HEIGHT}.
 * </p>
 */
public class ExplosiveBrick extends Brick implements Destructible {

    /** The explosion radius, measured in brick units. */
    private final int explosionRadius;

    /** The list of all bricks in the current level, used for explosion propagation. */
    private List<Brick> bricks;

    /**
     * Constructs an {@code ExplosiveBrick} at the specified coordinates.
     *
     * @param x      The x-coordinate of the brick.
     * @param y      The y-coordinate of the brick.
     * @param bricks The list of all bricks in the level (used to determine which bricks are within range).
     */
    public ExplosiveBrick(double x, double y, List<Brick> bricks) {
        super(x, y);
        this.bricks = bricks;
        this.explosionRadius = GameConfig.EXPLOSION_RADIUS;
        sprite = GameConfig.EXPLOSIVE_BRICK_IMAGE;
    }

    /**
     * Called when the brick is hit by the ball.
     * <p>
     * Marks the brick as hit and destroyed, then triggers the destruction event.
     * </p>
     */
    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
    }

    /**
     * Invoked automatically when the brick is destroyed.
     * <p>
     * This method calls {@link #explode()} to damage surrounding bricks.
     * </p>
     */
    @Override
    public void onDestroyed() {
        explode();
    }

    /**
     * Triggers the explosion, damaging all nearby bricks within the explosion radius.
     * <p>
     * Each brick's distance from this one is calculated using their positions.
     * If both the horizontal and vertical distances are within the radius range,
     * the other brick's {@link Brick#hit()} method is called.
     * </p>
     */
    private void explode() {
        double cx = getX();
        double cy = getY();

        for (Brick b : bricks) {
            if (b == this || b.isDestroyed()) {
                continue;
            }

            double dx = Math.abs(b.getX() - cx);
            double dy = Math.abs(b.getY() - cy);

            // If the brick is within explosion radius (scaled by brick size)
            if (dx <= explosionRadius * (GameConfig.BRICK_WIDTH + 1)
                    && dy <= explosionRadius * (GameConfig.BRICK_HEIGHT + 1)) {
                b.hit();
            }
        }
    }
}
