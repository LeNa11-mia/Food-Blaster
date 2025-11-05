package com.breakout.entities.bricks;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.entities.items.GoodItem;
import com.breakout.entities.items.Item;
import com.breakout.interfaces.Destructible;
import com.breakout.managers.GameManager;

/**
 * Represents a special brick that releases an item when destroyed.
 * The item can have either a positive or negative effect on the player.
 */
public class ItemBrick extends Brick implements Destructible {

    /**
     * Constructs an {@code ItemBrick} at the specified position.
     *
     * @param x the x-coordinate of the brick
     * @param y the y-coordinate of the brick
     */
    public ItemBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.ITEM_BRICK_IMAGE;
    }

    /**
     * Called when the brick is hit by the ball.
     * Marks the brick as destroyed and triggers item creation.
     */
    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
    }

    /**
     * Called when the brick is destroyed.
     * Creates a random item and adds it to the game if available.
     */
    @Override
    public void onDestroyed() {
        Item item = createAndNotifyItem();
        if (item != null) {
            Game.getGame().getGm().addItem(item);
        }
    }

    /**
     * Creates a random item at the brick's position and notifies the player
     * by displaying a message on screen.
     *
     * @return the created {@link Item}, or {@code null} if none is generated
     */
    private Item createAndNotifyItem() {
        GameManager gm = Game.getGame().getGm();

        Item item = Item.createRandomItem(
                getX() + getWidth() / 2 - GameConfig.ITEM_WIDTH / 2,
                getY() + getHeight() / 2 - GameConfig.ITEM_HEIGHT / 2,
                gm
        );

        if (item != null) {
            String icon = (item instanceof GoodItem) ? ":))" : ":((";
            String message = item.getName() + " " + icon;
            gm.showMessageOnScreen(message);
        }

        return item;
    }
}
