package com.breakout.entities.items;

import com.breakout.config.GameConfig;

/**
 * Represents a negative (bad) item that provides a harmful effect when collected.
 * <p>
 * {@code BadItem} is an abstract subclass of {@link Item}. It defines the shared
 * behavior and properties of items that negatively impact the player.
 * </p>
 */
public abstract class BadItem extends Item {

    /**
     * Constructs a {@code BadItem} at the specified coordinates.
     *
     * @param x the x-coordinate of the item
     * @param y the y-coordinate of the item
     */
    public BadItem(double x, double y) {
        super(x, y);
        this.sprite = GameConfig.ITEM_IMAGE;
    }
}
