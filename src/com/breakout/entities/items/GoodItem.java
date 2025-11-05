package com.breakout.entities.items;

import com.breakout.config.GameConfig;

/**
 * Represents a positive (good) item that grants a beneficial effect when collected.
 * <p>
 * {@code GoodItem} is an abstract subclass of {@link Item}. It defines the shared
 * attributes and structure for items that provide positive effects to the player,
 * such as increasing paddle size, speed, or granting bonuses.
 * </p>
 */
public abstract class GoodItem extends Item {

    /**
     * Constructs a {@code GoodItem} at the specified position.
     *
     * @param x the x-coordinate of the item
     * @param y the y-coordinate of the item
     */
    public GoodItem(double x, double y) {
        super(x, y);
        this.sprite = GameConfig.ITEM_IMAGE;
    }
}
