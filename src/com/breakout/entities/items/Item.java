package com.breakout.entities.items;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

import java.util.ArrayList;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update(double deltaTime) {
        // Falling down
        y += speed * deltaTime;
    }

    public abstract void applyEffect(Paddle paddle, GameManager gm);

    public static Item createRandomItem(double x, double y, GameManager gm) {
        final double BASE_SPEED = GameConfig.PADDLE_SPEED;
        final double MAX_SPEED_LIMIT = BASE_SPEED * 2;
        final double MIN_SPEED_LIMIT = BASE_SPEED * 0.5;
        final double BASE_WIDTH = GameConfig.PADDLE_WIDTH;
        final double MIN_WIDTH_LIMIT = BASE_WIDTH * 0.7;
        final double MAX_WIDTH_LIMIT = BASE_WIDTH * 1.5;
        final double MAX_BALL_WIDTH = GameConfig.BALL_WIDTH * 2.5;

        double currentPaddleSpeed = gm.getPaddle().getSpeed();
        double currentPaddleWidth = gm.getPaddle().getWidth();
        double currentBallWidth = gm.getBall().getWidth();

        List<BiFunction<Double, Double, Item>> factories = new ArrayList<>();

        factories.add(GameOverItem::new);

        if (currentPaddleWidth > MIN_WIDTH_LIMIT) {
            factories.add(PaddleShrinkItem::new);
        }

        if (currentPaddleWidth < MAX_WIDTH_LIMIT) {
            factories.add(PaddleExpandItem::new);
        }

        if (currentPaddleSpeed < MAX_SPEED_LIMIT) {
            factories.add(PaddleSpeedUpItem::new);
        }

        if (currentPaddleSpeed > MIN_SPEED_LIMIT) {
            factories.add(PaddleSlowDownItem::new);
        }

        if (currentBallWidth < MAX_BALL_WIDTH) {
            factories.add(BallBiggerItem::new);
        }

        if (factories.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        return factories.get(rand.nextInt(factories.size())).apply(x, y);
    }
}
