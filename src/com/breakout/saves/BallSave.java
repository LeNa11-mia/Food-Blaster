package com.breakout.saves;

import java.io.Serializable;

/**
 * Serializable data class for storing ball state in game saves.
 * Contains all necessary information to restore a ball's position,
 * velocity, and physical properties.
 *
 * <p>This class is used by the SaveManager to persist and restore
 * ball state between game sessions, maintaining the ball's movement
 * trajectory and position.</p>
 *
 * @author Breakout Team
 * @version 1.0
 */
public class BallSave implements Serializable {
    /** Serialization version UID for compatibility */
    private static final long serialVersionUID = 1L;

    /** X-coordinate position of the ball */
    private double x;

    /** Y-coordinate position of the ball */
    private double y;

    /** Horizontal velocity component of the ball */
    private double velocityX;

    /** Vertical velocity component of the ball */
    private double velocityY;

    /** Diameter of the ball in pixels */
    private int diameter;

    /**
     * Default constructor for serialization.
     * Creates an empty ball save object.
     */
    public BallSave() {}

    /**
     * Constructs a ball save object with specified properties.
     *
     * @param x the x-coordinate position of the ball
     * @param y the y-coordinate position of the ball
     * @param velocityX the horizontal velocity component
     * @param velocityY the vertical velocity component
     * @param diameter the diameter of the ball in pixels
     */
    public BallSave(double x, double y, double velocityX, double velocityY, int diameter) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.diameter = diameter;
    }

    // ----------- Getters and Setters -----------

    /**
     * Gets the x-coordinate position of the ball.
     *
     * @return double the x-coordinate
     */
    public double getX() { return x; }

    /**
     * Sets the x-coordinate position of the ball.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) { this.x = x; }

    /**
     * Gets the y-coordinate position of the ball.
     *
     * @return double the y-coordinate
     */
    public double getY() { return y; }

    /**
     * Sets the y-coordinate position of the ball.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) { this.y = y; }

    /**
     * Gets the horizontal velocity component of the ball.
     *
     * @return double the horizontal velocity
     */
    public double getVelocityX() { return velocityX; }

    /**
     * Sets the horizontal velocity component of the ball.
     *
     * @param velocityX the new horizontal velocity
     */
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }

    /**
     * Gets the vertical velocity component of the ball.
     *
     * @return double the vertical velocity
     */
    public double getVelocityY() { return velocityY; }

    /**
     * Sets the vertical velocity component of the ball.
     *
     * @param velocityY the new vertical velocity
     */
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    /**
     * Gets the diameter of the ball in pixels.
     *
     * @return int the ball diameter
     */
    public int getDiameter() { return diameter; }

    /**
     * Sets the diameter of the ball in pixels.
     *
     * @param diameter the new ball diameter
     */
    public void setDiameter(int diameter) { this.diameter = diameter; }
}
