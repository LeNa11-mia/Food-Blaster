package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.entities.Ball;
import com.breakout.interfaces.Destructible;

import java.awt.*;

/**
 * A special brick that makes the ball invisible for 10 seconds when hit.
 */
public class InvisibleBallBrick extends Brick implements Destructible {
    private final Ball ball;

    /**
     * Constructs an InvisibleBallBrick at the specified position.
     *
     * @param x the x-coordinate of the brick
     * @param y the y-coordinate of the brick
     * @param ball the ball instance to apply the invisibility effect to
     */
    public InvisibleBallBrick(double x, double y, Ball ball) {
        super(x, y);
        this.ball = ball;
        try {
            sprite = GameConfig.INVISIBLE_BALL_BRICK_IMAGE;
        } catch (Exception e) {
            sprite = null; // Will use fallback color
        }
    }

    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
    }

    @Override
    public void onDestroyed() {
        applyInvisibleEffect();
    }

    /**
     * Applies the invisibility effect to the ball for 10 seconds.
     */
    private void applyInvisibleEffect() {
        if (ball != null) {
            ball.setVisible(false);

            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                    ball.setVisible(true);
                } catch (InterruptedException e) {
                    ball.setVisible(true);
                }
            }).start();
        }
    }

}