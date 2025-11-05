package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.entities.Ball;
import com.breakout.interfaces.Destructible;

import java.awt.*;

/**
 * Represents a special {@link Brick} that applies an invisibility effect to the ball when destroyed.
 * <p>
 * When hit, this brick makes the ball invisible for a short duration, providing
 * a temporary gameplay effect. The effect automatically wears off after 5 seconds.
 * </p>
 *
 * <p>
 * The brick uses a predefined sprite from {@link GameConfig#INVISIBLE_BALL_BRICK_IMAGE}.
 * If the sprite cannot be loaded, a fallback color or rendering can be used instead.
 * </p>
 */
public class InvisibleBallBrick extends Brick implements Destructible {

    /** Reference to the ball affected by this brick's effect. */
    private final Ball ball;

    /**
     * Creates an {@code InvisibleBallBrick} at the specified coordinates.
     *
     * @param x    The x-coordinate of the brick (top-left corner).
     * @param y    The y-coordinate of the brick (top-left corner).
     * @param ball The {@link Ball} instance that will receive the invisibility effect.
     */
    public InvisibleBallBrick(double x, double y, Ball ball) {
        super(x, y);
        this.ball = ball;
        try {
            sprite = GameConfig.INVISIBLE_BALL_BRICK_IMAGE;
        } catch (Exception e) {
            sprite = null; // Fallback in case sprite is missing
        }
    }

    /**
     * Called when the brick is hit by the ball.
     * <p>
     * Marks the brick as destroyed and triggers the invisibility effect.
     * </p>
     */
    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
    }

    /**
     * Called automatically after the brick is destroyed.
     * <p>
     * Triggers the invisibility effect on the associated ball.
     * </p>
     */
    @Override
    public void onDestroyed() {
        applyInvisibleEffect();
    }

    /**
     * Applies an invisibility effect to the ball for 5 seconds.
     * <p>
     * This method runs asynchronously in a separate thread so that it does not
     * block the game loop. After 5 seconds, the ball automatically becomes visible again.
     * </p>
     */
    private void applyInvisibleEffect() {
        if (ball != null) {
            ball.setVisible(false);

            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    ball.setVisible(true);
                } catch (InterruptedException e) {
                    ball.setVisible(true);
                }
            }).start();
        }
    }
}
