package com.breakout.saves;

import java.io.Serializable;

/**
 * Serializable data class for storing brick state in game saves.
 * Contains all necessary information to restore a brick's position,
 * dimensions, visual properties, and destruction state.
 *
 * <p>This class is used by the SaveManager to persist and restore
 * brick states between game sessions, maintaining level progress
 * and game continuity.</p>
 *
 * @author Breakout Team
 * @version 1.0
 */
public class BrickSave implements Serializable {
    /** Serialization version UID for compatibility */
    private static final long serialVersionUID = 1L;

    /** X-coordinate position of the brick */
    private int x;

    /** Y-coordinate position of the brick */
    private int y;

    /** Width of the brick in pixels */
    private int width;

    /** Height of the brick in pixels */
    private int height;

    /** Flag indicating if the brick has been destroyed */
    private boolean destroyed;

    /** Current hit points remaining for the brick */
    private int hitPoints;

    /** Maximum hit points the brick can have */
    private int maxHitPoints;

    /** Color value of the brick (RGB integer representation) */
    private int color;

    /** Point value awarded when the brick is destroyed */
    private int points;

    /**
     * Default constructor for serialization.
     * Creates an empty brick save object.
     */
    public BrickSave() {}

    /**
     * Constructs a brick save object with specified properties.
     *
     * @param x the x-coordinate position of the brick
     * @param y the y-coordinate position of the brick
     * @param width the width of the brick in pixels
     * @param height the height of the brick in pixels
     * @param destroyed whether the brick has been destroyed
     * @param hitPoints current hit points remaining
     * @param maxHitPoints maximum possible hit points
     * @param color RGB color value of the brick
     * @param points point value awarded when destroyed
     */
    public BrickSave(int x, int y, int width, int height, boolean destroyed,
                     int hitPoints, int maxHitPoints, int color, int points) {
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

    // ----------- Getters and Setters -----------

    /**
     * Gets the x-coordinate position of the brick.
     *
     * @return int the x-coordinate
     */
    public int getX() { return x; }

    /**
     * Sets the x-coordinate position of the brick.
     *
     * @param x the new x-coordinate
     */
    public void setX(int x) { this.x = x; }

    /**
     * Gets the y-coordinate position of the brick.
     *
     * @return int the y-coordinate
     */
    public int getY() { return y; }

    /**
     * Sets the y-coordinate position of the brick.
     *
     * @param y the new y-coordinate
     */
    public void setY(int y) { this.y = y; }

    /**
     * Gets the width of the brick in pixels.
     *
     * @return int the brick width
     */
    public int getWidth() { return width; }

    /**
     * Sets the width of the brick in pixels.
     *
     * @param width the new brick width
     */
    public void setWidth(int width) { this.width = width; }

    /**
     * Gets the height of the brick in pixels.
     *
     * @return int the brick height
     */
    public int getHeight() { return height; }

    /**
     * Sets the height of the brick in pixels.
     *
     * @param height the new brick height
     */
    public void setHeight(int height) { this.height = height; }

    /**
     * Checks if the brick has been destroyed.
     *
     * @return boolean true if the brick is destroyed, false otherwise
     */
    public boolean isDestroyed() { return destroyed; }

    /**
     * Sets the destroyed state of the brick.
     *
     * @param destroyed the new destroyed state
     */
    public void setDestroyed(boolean destroyed) { this.destroyed = destroyed; }

    /**
     * Gets the current hit points remaining for the brick.
     *
     * @return int the current hit points
     */
    public int getHitPoints() { return hitPoints; }

    /**
     * Sets the current hit points for the brick.
     *
     * @param hitPoints the new hit points value
     */
    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

    /**
     * Gets the maximum hit points the brick can have.
     *
     * @return int the maximum hit points
     */
    public int getMaxHitPoints() { return maxHitPoints; }

    /**
     * Sets the maximum hit points for the brick.
     *
     * @param maxHitPoints the new maximum hit points value
     */
    public void setMaxHitPoints(int maxHitPoints) { this.maxHitPoints = maxHitPoints; }

    /**
     * Gets the color value of the brick.
     *
     * @return int RGB color value
     */
    public int getColor() { return color; }

    /**
     * Sets the color value of the brick.
     *
     * @param color the new RGB color value
     */
    public void setColor(int color) { this.color = color; }

    /**
     * Gets the point value awarded when the brick is destroyed.
     *
     * @return int the point value
     */
    public int getPoints() { return points; }

    /**
     * Sets the point value awarded when the brick is destroyed.
     *
     * @param points the new point value
     */
    public void setPoints(int points) { this.points = points; }
}
