package com.breakout.entities.items;

import com.breakout.entities.Ball;
import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * A positive item that increases the size of the ball when collected.
 * <p>
 * The ball is enlarged to 130% of its current size, which makes it easier
 * for the player to hit, but can also make ball movement feel slightly slower.
 */
public class BallBiggerItem extends GoodItem {

    /** The multiplier used to increase the ball's size. */
    private static final double SIZE_MULTIPLIER = 1.3;

    /**
     * Creates a new BallBiggerItem at the specified position.
     *
     * @param x the x-position where the item appears
     * @param y the y-position where the item appears
     */
    public BallBiggerItem(double x, double y) {
        super(x, y);
        name = "BALL BIGGER";
    }

    /**
     * Applies the ball size increase effect.
     * <p>
     * The ball's width and height are both scaled by {@code SIZE_MULTIPLIER}.
     *
     * @param paddle the player's paddle (not used in this effect)
     * @param gm     the game manager used to access the game ball
     */
    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        Ball ball = gm.getBall();

        double oldWidth = ball.getWidth();
        double oldHeight = ball.getHeight();

        double newWidth = oldWidth * SIZE_MULTIPLIER;
        double newHeight = oldHeight * SIZE_MULTIPLIER;

        ball.resizeSprite(newWidth, newHeight);

        System.out.println(
                "✅ " + name + " : " +
                        String.format("%.2f", oldWidth) + "x" + String.format("%.2f", oldHeight) +
                        " → " +
                        String.format("%.2f", newWidth) + "x" + String.format("%.2f", newHeight)
        );
    }
}
