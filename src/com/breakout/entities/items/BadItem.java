package com.breakout.entities.items;

import com.breakout.config.GameConfig;

import java.util.Random;

public abstract class BadItem extends Item {

    public BadItem(double x, double y) {
        super(x, y);
        this.sprite = GameConfig.ITEM_IMAGE;
    }
}
