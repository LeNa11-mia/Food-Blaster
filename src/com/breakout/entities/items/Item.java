package com.breakout.entities.items;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.entities.Paddle;
import com.breakout.managers.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * {@code Item} is the abstract base class for all falling items (power-ups/power-downs)
 * in the game.
 * <p>
 * Items automatically fall and apply an effect upon collision with the {@link Paddle}.
 * </p>
 */
public abstract class Item extends GameObject {
    /** The falling speed of the item, derived from the game configuration. */
    private final double speed = GameConfig.ITEM_FALLING_SPEED;

    /** The name of the item, used for display messages. */
    protected String name;

    /**
     * Initializes an item at the specified coordinates.
     *
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public Item(final double x, final double y) {
        super(x, y, GameConfig.ITEM_WIDTH, GameConfig.ITEM_HEIGHT);
        this.sprite = GameConfig.ITEM_IMAGE;
    }

    /**
     * Retrieves the name of the item.
     *
     * @return The name of the item (e.g., "PADDLE EXPAND").
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name The new name of the item.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Updates the state of the item.
     * <p>
     * The item's primary behavior is falling down the Y-axis.
     * </p>
     *
     * @param deltaTime The time elapsed since the previous update.
     */
    @Override
    public void update(final double deltaTime) {
        // Falling down
        this.y += this.speed * deltaTime;
    }

    /**
     * Abstract method to apply the item's effect to the game state.
     *
     * @param paddle The current paddle.
     * @param gm The game manager.
     */
    public abstract void applyEffect(Paddle paddle, GameManager gm);

    /**
     * Randomly creates a new item based on the current state of the game.
     * <p>
     * Item creation is conditional, based on the current size and speed of the
     * Paddle and Ball, to prevent generating useless items (e.g., no PaddleShrinkItem
     * if the Paddle is already too small).
     * </p>
     *
     * @param x The x-coordinate where the item will be created.
     * @param y The y-coordinate where the item will be created.
     * @param gm The game manager, used to fetch the current game state.
     * @return A newly created random {@link Item} or {@code null} if no valid item can be generated.
     */
    public static Item createRandomItem(final double x, final double y, final GameManager gm) {
        // Define limit constants (using final for local variables)
        final double BASE_SPEED = GameConfig.PADDLE_SPEED;
        final double MAX_SPEED_LIMIT = BASE_SPEED * 2;
        final double MIN_SPEED_LIMIT = BASE_SPEED * 0.5;
        final double BASE_WIDTH = GameConfig.PADDLE_WIDTH;
        final double MIN_WIDTH_LIMIT = BASE_WIDTH * 0.7;
        final double MAX_WIDTH_LIMIT = BASE_WIDTH * 1.5;
        final double MAX_BALL_WIDTH = GameConfig.BALL_WIDTH * 2.5;

        final double currentPaddleSpeed = gm.getPaddle().getSpeed();
        final double currentPaddleWidth = gm.getPaddle().getWidth();
        final double currentBallWidth = gm.getBall().getWidth();

        // List of "factories" (constructors) that can be used
        final List<BiFunction<Double, Double, Item>> factories = new ArrayList<>();

        // 1. Always add Game Over Item
        factories.add(GameOverItem::new);

        // 2. Add PaddleShrinkItem if the Paddle is not too small
        if (currentPaddleWidth > MIN_WIDTH_LIMIT) {
            factories.add(PaddleShrinkItem::new);
        }

        // 3. Add PaddleExpandItem if the Paddle is not too large
        if (currentPaddleWidth < MAX_WIDTH_LIMIT) {
            factories.add(PaddleExpandItem::new);
        }

        // 4. Add PaddleSpeedUpItem if the Paddle is not too fast
        if (currentPaddleSpeed < MAX_SPEED_LIMIT) {
            factories.add(PaddleSpeedUpItem::new);
        }

        // 5. Add PaddleSlowDownItem if the Paddle is not too slow
        if (currentPaddleSpeed > MIN_SPEED_LIMIT) {
            factories.add(PaddleSlowDownItem::new);
        }

        // 6. Add BallBiggerItem if the Ball is not too large
        if (currentBallWidth < MAX_BALL_WIDTH) {
            factories.add(BallBiggerItem::new);
        }

        // Check if no valid items can be created
        if (factories.isEmpty()) {
            return null;
        }

        // Randomly select a factory and create the object
        final Random rand = new Random();
        return factories.get(rand.nextInt(factories.size())).apply(x, y);
    }
}