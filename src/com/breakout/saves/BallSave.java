package com.breakout.saves;

import java.io.Serializable;

/**
 * {@code BallSave} is a simple data class used to persist the state of the game's
 * ball (position, velocity, and size) for saving and loading game progress.
 * <p>
 * This class implements {@link Serializable} to allow easy binary saving to a file.
 * </p>
 */
public class BallSave implements Serializable {
    /** The serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private int diameter;

    /**
     * Default constructor required for deserialization.
     */
    public BallSave() {
        // Default constructor implementation remains empty as per original
    }

    /**
     * Initializes a new {@code BallSave} instance with the current properties of the ball.
     *
     * @param x The current x-coordinate of the ball.
     * @param y The current y-coordinate of the ball.
     * @param velocityX The current horizontal velocity of the ball.
     * @param velocityY The current vertical velocity of the ball.
     * @param diameter The current diameter (size) of the ball.
     */
    public BallSave(final double x, final double y, final double velocityX, final double velocityY, final int diameter) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.diameter = diameter;
    }

    // --- Getters and Setters ---

    /**
     * Gets the current x-coordinate.
     * @return The x-coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Sets the current x-coordinate.
     * @param x The new x-coordinate.
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * Gets the current y-coordinate.
     * @return The y-coordinate.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets the current y-coordinate.
     * @param y The new y-coordinate.
     */
    public void setY(final double y) {
        this.y = y;
    }

    /**
     * Gets the current horizontal velocity.
     * @return The horizontal velocity (Vx).
     */
    public double getVelocityX() {
        return this.velocityX;
    }

    /**
     * Sets the current horizontal velocity.
     * @param velocityX The new horizontal velocity (Vx).
     */
    public void setVelocityX(final double velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * Gets the current vertical velocity.
     * @return The vertical velocity (Vy).
     */
    public double getVelocityY() {
        return this.velocityY;
    }

    /**
     * Sets the current vertical velocity.
     * @param velocityY The new vertical velocity (Vy).
     */
    public void setVelocityY(final double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * Gets the current diameter (size).
     * @return The diameter.
     */
    public int getDiameter() {
        return this.diameter;
    }

    /**
     * Sets the current diameter (size).
     * @param diameter The new diameter.
     */
    public void setDiameter(final int diameter) {
        this.diameter = diameter;
    }
}