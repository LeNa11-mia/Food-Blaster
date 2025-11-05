package com.breakout.entities.bricks;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.entities.items.GoodItem;
import com.breakout.entities.items.Item;
import com.breakout.interfaces.Destructible;
import com.breakout.managers.GameManager;

/**
 * Special brick that drops items when destroyed.
 * When hit by the ball, this brick generates a random item (either beneficial or detrimental)
 * that falls from the brick's position and can be collected by the paddle.
 * Implements the Destructible interface for standardized destruction behavior.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class ItemBrick extends Brick implements Destructible {

    /**
     * Constructs an item brick at the specified position.
     *
     * @param x the x-coordinate of the brick's position
     * @param y the y-coordinate of the brick's position
     */
    public ItemBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.ITEM_BRICK_IMAGE;
    }

    /**
     * Handles the brick being hit by the ball.
     * Marks the brick as hit and destroyed, then triggers item generation.
     */
    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
    }

    /**
     * Called when the brick is destroyed.
     * Creates a random item and adds it to the game world for collection.
     */
    @Override
    public void onDestroyed() {
        Item item = createAndNotifyItem();

        if (item != null) {
            Game.getGame().getGm().addItem(item);
        }
    }

    /**
     * Creates a random item and displays a notification message.
     * The item is positioned at the center of the destroyed brick.
     * Shows different emoticons for good vs bad items in the notification.
     *
     * @return Item the created random item, or null if creation failed
     */
    private Item createAndNotifyItem() {
        GameManager gm = Game.getGame().getGm();

        // Create item centered on the brick's position
        Item item = Item.createRandomItem(
                getX() + getWidth() / 2 - GameConfig.ITEM_WIDTH / 2,
                getY() + getHeight() / 2 - GameConfig.ITEM_HEIGHT / 2,
                gm
        );

        // Display notification message with appropriate emoticon
        if (item != null) {
            String icon = (item instanceof GoodItem) ? ":))" : ":((";
            String message = item.getName() + " " + icon;

            gm.showMessageOnScreen(message);
        }

        return item;
    }
}
