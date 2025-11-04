package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

public class GameOverItem extends BadItem {
    public GameOverItem(double x, double y) {
        super(x, y);
        name = "GAME OVER";
    }

    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        gm.setLives(0);
        System.out.println("✅" + name);
    }
}