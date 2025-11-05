package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * A negative item that immediately ends the game when collected.
 * <p>
 * When this item is applied, the player's remaining lives are set to zero,
 * triggering an instant game over.
 */
public class GameOverItem extends BadItem {

    /**
     * Creates a new GameOverItem at the specified position.
     *
     * @param x the x-position where the item appears
     * @param y the y-position where the item appears
     */
    public GameOverItem(double x, double y) {
        super(x, y);
        name = "GAME OVER";
    }

    /**
     * Applies the game over effect.
     * <p>
     * This sets the player's lives to zero, forcing the game to end immediately.
     *
     * @param paddle the player's paddle (not used by this effect)
     * @param gm     the game manager that controls game state and lives
     */
    @Override
    public void applyEffect(Paddle paddle, GameManager gm) {
        gm.setLives(0);
        System.out.println("✅ " + name + " triggered — lives set to 0");
    }
}
