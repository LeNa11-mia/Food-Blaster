package com.breakout.saves;

import java.io.Serializable;

/**
 * Serializable data class for storing paddle state in game saves.
 * Contains all necessary information to restore a paddle's position,
 * dimensions, and movement characteristics.
 *
 * <p>This class is used by the SaveManager to persist and restore
 * paddle state between game sessions.</p>
 *
 * @author Breakout Team
 * @version 1.0
 */
public class PaddleSave implements Serializable {
    /** Serialization version UID for compatibility */
    private static final long serialVersionUID = 1L;

    /** X-coordinate position of the paddle */
    private double x;

    /** Y-coordinate position of the paddle */
    private double y;

    /** Width of the paddle in pixels */
    private int width;

    /** Height of the paddle in pixels */
    private int height;

    /** Movement velocity/speed of the paddle */
    private double velocity;

    /**
     * Default constructor for serialization.
     * Creates an empty paddle save object.
     */
    public PaddleSave() {}

    /**
     * Constructs a paddle save object with specified properties.
     *
     * @param x the x-coordinate position of the paddle
     * @param y the y-coordinate position of the paddle
     * @param width the width of the paddle in pixels
     * @param height the height of the paddle in pixels
     * @param velocity the movement velocity/speed of the paddle
     */
    public PaddleSave(double x, double y, int width, int height, double velocity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
    }

    // ----------- Getters and Setters -----------

    /**
     * Gets the x-coordinate position of the paddle.
     *
     * @return double the x-coordinate
     */
    public double getX() { return x; }

    /**
     * Sets the x-coordinate position of the paddle.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) { this.x = x; }

    /**
     * Gets the y-coordinate position of the paddle.
     *
     * @return double the y-coordinate
     */
    public double getY() { return y; }

    /**
     * Sets the y-coordinate position of the paddle.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) { this.y = y; }

    /**
     * Gets the width of the paddle in pixels.
     *
     * @return int the paddle width
     */
    public int getWidth() { return width; }

    /**
     * Sets the width of the paddle in pixels.
     *
     * @param width the new paddle width
     */
    public void setWidth(int width) { this.width = width; }

    /**
     * Gets the height of the paddle in pixels.
     *
     * @return int the paddle height
     */
    public int getHeight() { return height; }

    /**
     * Sets the height of the paddle in pixels.
     *
     * @param height the new paddle height
     */
    public void setHeight(int height) { this.height = height; }

    /**
     * Gets the movement velocity/speed of the paddle.
     *
     * @return double the paddle velocity
     */
    public double getVelocity() { return velocity; }

    /**
     * Sets the movement velocity/speed of the paddle.
     *
     * @param velocity the new paddle velocity
     */
    public void setVelocity(double velocity) { this.velocity = velocity; }
}
