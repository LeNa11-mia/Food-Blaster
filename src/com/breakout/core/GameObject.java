package com.breakout.core;

import com.breakout.utils.ImageUtils;

import javax.swing.*;

/**
 * Represents a base class for all game objects in the Breakout game.
 * <p>
 * Each game object has a position (x, y), dimensions (width, height), and an optional
 * {@link ImageIcon} sprite used for rendering. Subclasses must implement the
 * {@link #update(double)} method to define behavior on each frame update.
 * </p>
 */
public abstract class GameObject {

    /** X coordinate of the object (top-left corner). */
    protected double x;

    /** Y coordinate of the object (top-left corner). */
    protected double y;

    /** Width of the object in pixels. */
    protected double width;

    /** Height of the object in pixels. */
    protected double height;

    /** Sprite image representing the object. */
    protected ImageIcon sprite;

    /**
     * Constructs a new {@code GameObject} with the given position and size.
     *
     * @param x      The x-coordinate of the object.
     * @param y      The y-coordinate of the object.
     * @param width  The width of the object.
     * @param height The height of the object.
     */
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /** return The x-coordinate of the object. */
    public double getX() {
        return x;
    }

    /** @param x The new x-coordinate of the object. */
    public void setX(double x) {
        this.x = x;
    }

    /** @return The y-coordinate of the object. */
    public double getY() {
        return y;
    }

    /** @param y The new y-coordinate of the object. */
    public void setY(double y) {
        this.y = y;
    }

    /** @return The width of the object. */
    public double getWidth() {
        return width;
    }

    /** @param width The new width of the object. */
    public void setWidth(double width) {
        this.width = width;
    }

    /** @return The height of the object. */
    public double getHeight() {
        return height;
    }

    /** @param height The new height of the object. */
    public void setHeight(double height) {
        this.height = height;
    }

    /** @return The {@link ImageIcon} sprite representing the object. */
    public ImageIcon getSprite() {
        return sprite;
    }

    /**
     * Called once per frame to update the object's logic.
     *
     * @param deltaTime The time (in seconds) since the last update.
     */
    public abstract void update(double deltaTime);

    /**
     * Checks whether this object intersects another {@code GameObject}.
     *
     * @param other The other game object to check collision with.
     * @return {@code true} if the two objects intersect, {@code false} otherwise.
     */
    public boolean intersects(GameObject other) {
        return x <= other.x + other.width &&
                x + width >= other.x &&
                y <= other.y + other.height &&
                y + height >= other.y;
    }

    /**
     * Resizes the sprite image to the specified dimensions.
     * <p>
     * If the sprite is {@code null} or contains no image, this method logs an error
     * message and does nothing.
     * </p>
     *
     * @param newWidth  The new width for the sprite.
     * @param newHeight The new height for the sprite.
     */
    public void resizeSprite(double newWidth, double newHeight) {
        if (sprite == null || sprite.getImage() == null) {
            System.out.println("❌ Sprite null before resize");
            return;
        }
        this.width = newWidth;
        this.height = newHeight;
        this.sprite = ImageUtils.resize(sprite, (int) newWidth, (int) newHeight);
    }
}
