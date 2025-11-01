package com.breakout.entities.items;

import com.breakout.config.GameConfig;

import java.util.Random;

public abstract class BadItem extends Item {
    public BadItem(double x, double y) {
        super(x, y);
        this.sprite = GameConfig.ITEM_IMAGE;
    }

    public static Item createRandom(double x, double y) {
        Random rand = new Random();
        int effectType = rand.nextInt(1); // Chỉ có 1 loại hiệu ứng xấu hiện tại

        switch (effectType) {
            case 0:
            default:
                return new PaddleShrinkItem(x, y);
        }
    }
}
