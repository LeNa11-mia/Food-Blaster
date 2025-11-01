package com.breakout.entities.items;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

import java.util.Random;

public abstract class Item extends GameObject {
    private double speed = GameConfig.ITEM_FALLING_SPEED;

    public Item(double x, double y) {
        super(x, y, GameConfig.ITEM_WIDTH, GameConfig.ITEM_HEIGHT);
        this.sprite = GameConfig.ITEM_IMAGE;
    }

    @Override
    public void update(double deltaTime) {
        // Falling down
        y += speed * deltaTime;
    }

    public abstract void applyEffect(Paddle paddle, GameManager gm);

    public static Item createRandomItem(double x, double y) {
        Random rand = new Random();
        // 2 types of items: Good và Bad
        int type = rand.nextInt(2);

        if (type == 0) {
            // Good item
            return GoodItem.createRandom(x, y);
        } else {
            // Bad item
            return BadItem.createRandom(x, y);
        }
    }
}
