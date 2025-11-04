package com.breakout.entities.bricks;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.entities.items.GoodItem;
import com.breakout.entities.items.Item;
import com.breakout.interfaces.Destructible;
import com.breakout.managers.GameManager;

public class ItemBrick extends Brick implements Destructible {

    public ItemBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.ITEM_BRICK_IMAGE;
    }

    @Override
    public void hit() {
        hit = true;
        destroyed = true;
        onDestroyed();
    }

    @Override
    public void onDestroyed() {
        Item item = createAndNotifyItem();

        if (item != null) {
            Game.getGame().getGm().addItem(item);
        }
    }

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
