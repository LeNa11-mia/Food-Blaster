package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.interfaces.Destructible;

import java.util.List;

/**
 * Explosive brick that triggers a chain reaction when destroyed.
 * When hit, this brick explodes and destroys all bricks within a specified radius.
 * Implements the Destructible interface for standardized destruction behavior.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class ExplosiveBrick extends Brick implements Destructible {
    /** The explosion radius measured in number of bricks */
    private final int explosionRadius;

    /** Reference to the list of all bricks for chain reaction detection */
    private List<Brick> bricks;

    /**
     * Constructs an explosive brick at the specified position.
     *
     * @param x the x-coordinate of the brick's position
     * @param y the y-coordinate of the brick's position
     * @param bricks the list of all bricks in the current level for explosion detection
     */
    public ExplosiveBrick(double x, double y, List<Brick> bricks) {
        super(x, y);
        this.bricks = bricks;
        this.explosionRadius = GameConfig.EXPLOSION_RADIUS;
        sprite = GameConfig.EXPLOSIVE_BRICK_IMAGE;
    }

    /**
     * Handles the brick being hit by the ball.
     * Marks the brick as hit and destroyed, then triggers the explosion effect.
     */
    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
    }

    /**
     * Called when the brick is destroyed.
     * Triggers the explosion that affects nearby bricks.
     */
    @Override
    public void onDestroyed() {
        explode();
    }

    /**
     * Creates an explosion that destroys all bricks within the explosion radius.
     * Calculates distance based on brick dimensions and destroys bricks within range.
     * Skips already destroyed bricks and the explosive brick itself.
     */
    private void explode() {
        double centerX = getX();
        double centerY = getY();

        for (Brick brick : bricks) {
            // Skip self and already destroyed bricks
            if (brick == this || brick.isDestroyed()) {
                continue;
            }

            // Calculate horizontal and vertical distance in brick units
            double horizontalDistance = Math.abs(brick.getX() - centerX);
            double verticalDistance = Math.abs(brick.getY() - centerY);

            // Check if brick is within explosion radius (considering brick dimensions + spacing)
            if (horizontalDistance <= explosionRadius * (GameConfig.BRICK_WIDTH + 1) &&
                    verticalDistance <= explosionRadius * (GameConfig.BRICK_HEIGHT + 1)) {
                brick.hit(); // Destroy the nearby brick
            }
        }
    }
}
