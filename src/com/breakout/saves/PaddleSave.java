package com.breakout.saves;

import java.io.Serializable;

/**
 * {@code PaddleSave} is a simple data class used to persist the state of the game's
 * paddle (position, size, and speed) for saving and loading game progress.
 * <p>
 * This class implements {@link Serializable} to allow easy binary saving to a file.
 * </p>
 */
public class PaddleSave implements Serializable {
    /** The serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    private double x;
    private double y;
    private int width;
    private int height;
    private double velocity;

    /**
     * Default constructor required for deserialization.
     */
    public PaddleSave() {
        // Default constructor implementation remains empty as per original
    }

    /**
     * Initializes a new {@code PaddleSave} instance with the current properties of the paddle.
     *
     * @param x The current x-coordinate of the paddle.
     * @param y The current y-coordinate of the paddle.
     * @param width The current width of the paddle.
     * @param height The current height of the paddle.
     * @param velocity The current movement speed/velocity of the paddle.
     */
    public PaddleSave(final double x, final double y, final int width,
                      final int height, final double velocity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
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
     * Gets the current width.
     * @return The width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets the current height.
     * @return The height.
     */
    public int getHeight() {
        return this.height;
    }
}