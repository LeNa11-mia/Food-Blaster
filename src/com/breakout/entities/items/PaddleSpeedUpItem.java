package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * Beneficial item that increases the paddle's movement speed when collected.
 * Extends GoodItem to provide positive gameplay effects.
 * Temporarily boosts paddle mobility for better ball control.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class PaddleSpeedUpItem extends GoodItem {
    /** Multiplier applied to the paddle's current speed */
    private static final double SPEED_MULTIPLIER = 1.3;

    /**
     * Constructs a paddle speed-up item at the specified position.
     *
     * @param x the x-coordinate of the item's spawn position
     * @param y the y-coordinate of the item's spawn position
     */
    public PaddleSpeedUpItem(double x, double y) {
        super(x, y);
        name = "PADDLE SPEED UP";
    }

    /**
     * Applies the speed boost effect to the paddle when collected.
     * Increases the paddle's movement speed by the defined multiplier.
     * Provides console feedback about the speed change.
     *
     * @param paddle the paddle entity that collected the item
     * @param gm the game manager for accessing game state and logic
     */
    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        double oldSpeed = paddle.getSpeed();
        double newSpeed = oldSpeed * SPEED_MULTIPLIER;

        paddle.setSpeed(newSpeed);

        System.out.println("✅" + name + " : " + oldSpeed
                + " → " + newSpeed);
    }
}
