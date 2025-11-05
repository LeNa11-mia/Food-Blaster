package com.breakout.saves;

import java.io.Serializable;

/**
 * {@code BrickSave} is a simple data class used to persist the state of a single brick
 * (position, size, health, and status) for saving and loading game progress.
 * <p>
 * This class implements {@link Serializable} to allow easy binary saving to a file.
 * </p>
 */
public class BrickSave implements Serializable {
    /** The serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean destroyed;
    private int hitPoints;
    private int maxHitPoints;
    private int color;
    private int points;

    /**
     * Default constructor required for deserialization.
     */
    public BrickSave() {
        // Default constructor implementation remains empty as per original
    }

    /**
     * Initializes a new {@code BrickSave} instance with the current properties of a brick.
     *
     * @param x The current x-coordinate of the brick.
     * @param y The current y-coordinate of the brick.
     * @param width The width of the brick.
     * @param height The height of the brick.
     * @param destroyed The destruction status of the brick ({@code true} if destroyed).
     * @param hitPoints The current health/hit points remaining.
     * @param maxHitPoints The maximum health/hit points of the brick.
     * @param color The integer RGB value representing the color of the brick.
     * @param points The score value awarded for destroying the brick.
     */
    public BrickSave(final int x, final int y, final int width, final int height,
                     final boolean destroyed, final int hitPoints, final int maxHitPoints,
                     final int color, final int points) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = destroyed;
        this.hitPoints = hitPoints;
        this.maxHitPoints = maxHitPoints;
        this.color = color;
        this.points = points;
    }

    // --- Getters and Setters ---

    /**
     * Gets the current x-coordinate.
     * @return The x-coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Sets the current x-coordinate.
     * @param x The new x-coordinate.
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Gets the current y-coordinate.
     * @return The y-coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets the current y-coordinate.
     * @param y The new y-coordinate.
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Gets the width of the brick.
     * @return The width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the brick.
     * @return The height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Checks if the brick is destroyed.
     * @return {@code true} if the brick is destroyed.
     */
    public boolean isDestroyed() {
        return this.destroyed;
    }

    /**
     * Sets the destruction status of the brick.
     * @param destroyed The new destruction status.
     */
    public void setDestroyed(final boolean destroyed) {
        this.destroyed = destroyed;
    }

    /**
     * Gets the color value (integer RGB).
     * @return The color integer.
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Sets the color value (integer RGB).
     * @param color The new color integer.
     */
    public void setColor(final int color) {
        this.color = color;
    }
}