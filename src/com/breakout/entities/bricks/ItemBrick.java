package com.breakout.entities.bricks;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.entities.items.Item;
import com.breakout.interfaces.Destructible;

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
        spawnItem();
    }

    private void spawnItem() {
        Item item = Item.createRandomItem(
                getX() + getWidth() / 2 - GameConfig.ITEM_WIDTH / 2,
                getY() + getHeight() / 2 - GameConfig.ITEM_HEIGHT / 2
        );
        Game.getGame().getGm().addItem(item);
    }
}
