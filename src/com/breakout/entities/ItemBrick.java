package com.breakout.entities;

import com.breakout.config.GameConfig;
import com.breakout.items.Item;
import com.breakout.managers.GameManager;

public class ItemBrick extends Brick {
    private GameManager gm;

    public ItemBrick(double x, double y, GameManager gm) {
        super(x, y);
        this.gm = gm;
        sprite = GameConfig.ITEM_BRICK_IMAGE;
    }

    @Override
    public void hit() {
        super.hit(); // Đánh dấu gạch đã bị phá hủy
        spawnItem(); // Sinh ra vật phẩm
    }

    private void spawnItem() {
        // Sinh ra một vật phẩm ngẫu nhiên tại vị trí của gạch
        Item item = Item.createRandomItem(
                getX() + getWidth() / 2 - GameConfig.ITEM_WIDTH / 2, // Căn giữa item
                getY() + getHeight() / 2 - GameConfig.ITEM_HEIGHT / 2
        );

        gm.addItem(item);
    }
}
