package com.breakout.entities;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

/**
 * Represents the player's paddle, which moves horizontally at the bottom of the screen.
 * <p>
 * The {@code Paddle} can move left and right in response to keyboard input
 * and is responsible for bouncing the ball back into play.
 * </p>
 */
public class Paddle extends GameObject {

    /** The movement speed of the paddle. */
    private double speed;

    /** The horizontal velocity of the paddle. */
    private double vx;

    /**
     * Constructs a {@code Paddle} object at the specified coordinates.
     *
     * @param x the x-coordinate of the paddle
     * @param y the y-coordinate of the paddle
     */
    public Paddle(double x, double y) {
        super(x, y, GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_HEIGHT);
        sprite = GameConfig.PADDLE_IMAGE;
        speed = GameConfig.PADDLE_SPEED;
        vx = 0;
    }

    /**
     * Returns the current speed of the paddle.
     *
     * @return the paddle speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets a new movement speed for the paddle.
     *
     * @param speed the new paddle speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Returns the current horizontal velocity of the paddle.
     *
     * @return the horizontal velocity
     */
    public double getVx() {
        return vx;
    }

    /**
     * Updates the paddle’s position based on user input and elapsed time.
     *
     * @param deltaTime the time elapsed since the last frame (in seconds)
     */
    @Override
    public void update(double deltaTime) {
        if (Game.getGame().getKeyListener().isLeftPressed()) {
            moveLeft(deltaTime, GameConfig.SCREEN_WIDTH);
        } else if (Game.getGame().getKeyListener().isRightPressed()) {
            moveRight(deltaTime, GameConfig.SCREEN_WIDTH);
        } else {
            vx = 0;
        }
    }

    /**
     * Moves the paddle left within the screen boundaries.
     *
     * @param deltaTime   the time elapsed since the last frame (in seconds)
     * @param screenWidth the width of the game screen
     */
    public void moveLeft(double deltaTime, double screenWidth) {
        vx = -speed;
        x += vx * deltaTime;
        if (x < 0) {
            x = 0;
        }
    }

    /**
     * Moves the paddle right within the screen boundaries.
     *
     * @param deltaTime   the time elapsed since the last frame (in seconds)
     * @param screenWidth the width of the game screen
     */
    public void moveRight(double deltaTime, double screenWidth) {
        vx = speed;
        x += vx * deltaTime;
        double maxX = screenWidth - width - 12;
        if (x > maxX) {
            x = maxX;
        }
    }

    /**
     * Sets the paddle’s position manually.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the paddle’s width.
     *
     * @param width the new width
     */
    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns the current paddle width.
     *
     * @return the paddle width
     */
    @Override
    public double getWidth() {
        return super.getWidth();
    }
}
