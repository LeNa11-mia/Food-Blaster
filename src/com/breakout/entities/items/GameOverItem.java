package com.breakout.entities.items;

import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

/**
 * {@code GameOverItem} is a negative item (BadItem) that immediately ends the game.
 * <p>
 * When the player collects this item with the {@link com.breakout.entities.Paddle},
 * their lives are set to zero, triggering the Game Over state.
 * </p>
 */
public class GameOverItem extends BadItem {

    /**
     * Initializes a new Game Over item at the specified coordinates.
     *
     * @param x The initial x-coordinate of the item.
     * @param y The initial y-coordinate of the item.
     */
    public GameOverItem(final double x, final double y) {
        super(x, y);
        this.name = "GAME OVER";
    }

    /**
     * Applies the game over effect.
     * <p>
     * This method sets the player's lives to zero via the {@link GameManager},
     * which causes the game over state to be triggered on the next system update.
     * </p>
     *
     * @param paddle The current paddle (not directly used in this effect).
     * @param gm The game manager, used to access and modify the lives count.
     */
    @Override
    public void applyEffect(final Paddle paddle, final GameManager gm) {
        // Set lives to zero to trigger the Game Over state
        gm.setLives(0);
        System.out.println("✅" + this.name);
    }
}