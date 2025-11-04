package com.breakout.entities.bricks;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.entities.items.Item;
import com.breakout.managers.GameManager;

public class ItemBrick extends Brick {

    public ItemBrick(double x, double y) {
        super(x, y);
        sprite = GameConfig.ITEM_BRICK_IMAGE;
    }

    @Override
    public void onDestroyed() {
        spawnItem();
    }

    private void spawnItem() {
        GameManager gm = Game.getGame().getGm();
        Item item = Item.createRandomItem(
                getX() + getWidth() / 2 - GameConfig.ITEM_WIDTH / 2,
                getY() + getHeight() / 2 - GameConfig.ITEM_HEIGHT / 2
        );
        gm.addItem(item);
    }
}
