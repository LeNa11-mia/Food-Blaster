package com.breakout.core;

import com.breakout.utils.ImageUtils;

import javax.swing.*;

/**
 * Base class for all game objects in the Breakout game.
 * Provides common functionality for position, size, sprite management,
 * collision detection, and game object updates.
 *
 * @author Breakout Team
 * @version 1.0
 */
public abstract class GameObject {
    /** X-coordinate position of the game object */
    protected double x;

    /** Y-coordinate position of the game object */
    protected double y;

    /** Width of the game object */
    protected double width;

    /** Height of the game object */
    protected double height;

    /** Sprite image representing the visual appearance of the object */
    protected ImageIcon sprite;

    /**
     * Constructs a new GameObject with specified position and dimensions.
     *
     * @param x the x-coordinate of the object's position
     * @param y the y-coordinate of the object's position
     * @param width the width of the object
     * @param height the height of the object
     */
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the x-coordinate of the object's position.
     *
     * @return double the x-coordinate
     */
    public double getX() { return x; }

    /**
     * Sets the x-coordinate of the object's position.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) { this.x = x; }

    /**
     * Gets the y-coordinate of the object's position.
     *
     * @return double the y-coordinate
     */
    public double getY() { return y; }

    /**
     * Sets the y-coordinate of the object's position.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) { this.y = y; }

    /**
     * Gets the width of the object.
     *
     * @return double the object width
     */
    public double getWidth() { return width; }

    /**
     * Sets the width of the object.
     *
     * @param width the new width
     */
    public void setWidth(double width) { this.width = width; }

    /**
     * Gets the height of the object.
     *
     * @return double the object height
     */
    public double getHeight() { return height; }

    /**
     * Sets the height of the object.
     *
     * @param height the new height
     */
    public void setHeight(double height) { this.height = height; }

    /**
     * Gets the sprite image of the object.
     *
     * @return ImageIcon the object's sprite
     */
    public ImageIcon getSprite() { return sprite; }

    /**
     * Abstract method to update the object's state based on elapsed time.
     * Must be implemented by all concrete game object classes.
     *
     * @param deltaTime the time elapsed since the last update in seconds
     */
    public abstract void update(double deltaTime);

    /**
     * Performs simple axis-aligned bounding box collision detection.
     * Checks if this object intersects with another game object.
     *
     * @param other the other game object to check collision with
     * @return boolean true if the objects intersect, false otherwise
     */
    public boolean intersects(GameObject other) {
        return x <= other.x + other.width &&
                x + width >= other.x &&
                y <= other.y + other.height &&
                y + height >= other.y;
    }

    /**
     * Resizes the object's sprite to new dimensions.
     * Updates both the visual sprite and the object's width/height properties.
     *
     * @param newWidth the new width for the sprite and object
     * @param newHeight the new height for the sprite and object
     */
    public void resizeSprite(double newWidth, double newHeight) {
        if (sprite == null || sprite.getImage() == null) {
            System.out.println("❌ Sprite null before resize");
            return;
        }
        this.width = newWidth;
        this.height = newHeight;
        this.sprite = ImageUtils.resize(sprite, (int)newWidth, (int)newHeight);
    }
}
