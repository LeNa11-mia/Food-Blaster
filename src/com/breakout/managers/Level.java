package com.breakout.managers;

import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.entities.Ball;
import com.breakout.entities.bricks.*;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Level} manages the creation and tracking of game levels.
 * It loads brick layouts from {@link LevelData} and tracks which levels are unlocked.
 */
public class Level extends LevelData {

    /** Tracks the unlock status for all available levels. Array size is based on GameConfig.TOTAL_LEVELS. */
    private static final boolean[] levelUnlocked = new boolean[GameConfig.TOTAL_LEVELS];

    /**
     * Loads the brick layout for a specific level ID.
     *
     * @param id The ID number of the level to load (1 to GameConfig.TOTAL_LEVELS).
     * @param ball The game's {@link Ball} instance, required for certain brick types (like InvisibleBallBrick).
     * @return A {@link List} of {@link Brick} objects representing the level layout, or an empty list if the ID is invalid.
     */
    public static List<Brick> loadLevel(final int id, final Ball ball) {
        switch (id) {
            case 1:
                return createLevel(LevelData.level1, ball);
            case 2:
                return createLevel(LevelData.level2, ball);
            case 3:
                return createLevel(LevelData.level3, ball);
            case 4:
                return createLevel(LevelData.level4, ball);
            case 5:
                return createLevel(LevelData.level5, ball);
            case 6:
                return createLevel(LevelData.level6, ball);
            default:
                return new ArrayList<>();
        }
    }

    /**
     * Creates a list of brick objects based on the given level layout array.
     *
     * @param layout The 2D array representing the brick types and positions.
     * @param ball The {@link Ball} instance for bricks that require it (e.g., InvisibleBallBrick).
     * @return A fully populated {@link List} of {@link Brick} objects.
     */
    private static List<Brick> createLevel(final int[][] layout, final Ball ball) {
        final List<Brick> bricks = new ArrayList<>();
        final double offsetX = GameConfig.BRICK_WIDTH;
        final double offsetY = 85;
        final double spacing = 1;

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                final int type = layout[row][col];
                final double x = offsetX + col * (GameConfig.BRICK_WIDTH + spacing);
                final double y = offsetY + row * (GameConfig.BRICK_HEIGHT + spacing);

                Brick b = null;
                if (type == Defs.NORMAL_BRICK) {
                    b = new NormalBrick(x, y);
                } else if (type == Defs.EXPLOSIVE_BRICK) {
                    // ExplosiveBrick needs access to the whole list to trigger neighboring destruction
                    b = new ExplosiveBrick(x, y, bricks);
                } else if (type == Defs.UNBREAKABLE_BRICK) {
                    b = new UnbreakableBrick(x, y);
                } else if (type == Defs.FALLING_BRICK) {
                    b = new FallingBrick(x, y);
                } else if (type == Defs.ITEM_BRICK) {
                    b = new ItemBrick(x, y);
                } else if (type == Defs.INVISIBLE_BALL_BRICK) {
                    // InvisibleBallBrick needs the Ball instance to control its visibility
                    b = new InvisibleBallBrick(x, y, ball);
                }

                if (b != null) {
                    bricks.add(b);
                }
            }
        }
        return bricks;
    }

    /**
     * Unlocks a specific level, allowing it to be selected.
     *
     * @param id The ID number of the level to unlock.
     */
    public static void unlockLevel(final int id) {
        // ID is 1-based, array index is 0-based
        levelUnlocked[id - 1] = true;
    }

    /**
     * Checks if a specific level has been unlocked.
     *
     * @param id The ID number of the level to check.
     * @return {@code true} if the level is unlocked, {@code false} otherwise.
     */
    public static boolean isLevelUnlocked(final int id) {
        // ID is 1-based, array index is 0-based
        return levelUnlocked[id - 1];
    }
}