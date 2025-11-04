package com.breakout.entities.items;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public abstract class Item extends GameObject {
    private double speed = GameConfig.ITEM_FALLING_SPEED;
    protected String name;

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
        List<BiFunction<Double, Double, Item>> factories = List.of(
                PaddleSpeedUpItem::new,
                PaddleShrinkItem::new,
                PaddleSlowDownItem::new
        );

        Random rand = new Random();
        return factories.get(rand.nextInt(factories.size())).apply(x, y);
    }
}
