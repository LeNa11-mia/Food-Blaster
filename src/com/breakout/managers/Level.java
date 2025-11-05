package com.breakout.managers;

import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.bricks.ExplosiveBrick;
import com.breakout.entities.bricks.FallingBrick;
import com.breakout.entities.bricks.NormalBrick;
import com.breakout.entities.bricks.ItemBrick;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the creation and management of game levels.
 *
 * <p>This class loads brick layouts based on predefined level data
 * and provides utilities for unlocking and checking level progress.</p>
 */
public class Level extends LevelData {

    /** Tracks which levels have been unlocked. */
    private static boolean[] levelUnlocked = new boolean[GameConfig.TOTAL_LEVELS];

    /**
     * Loads the brick layout for a specific level.
     *
     * @param id The level index to load (1-based).
     * @return A list of Brick objects that make up the level.
     *         Returns an empty list if the level ID is invalid.
     */
    public static List<Brick> loadLevel(int id) {
        switch (id) {
            case 1: return createLevel(LevelData.level1);
            case 2: return createLevel(LevelData.level2);
            case 3: return createLevel(LevelData.level3);
            case 4: return createLevel(LevelData.level3); // Placeholder for future levels
            case 5: return createLevel(LevelData.level3);
            case 6: return createLevel(LevelData.level3);
            default: return new ArrayList<>();
        }
    }

    /**
     * Converts a 2D integer layout into actual {@link Brick} objects.
     *
     * <p>Brick types are determined by encoded values defined in {@link Defs}.</p>
     *
     * @param layout A 2D array describing the level's brick arrangement.
     * @return A list of instantiated Brick objects positioned on the game field.
     */
    private static List<Brick> createLevel(int[][] layout) {
        List<Brick> bricks = new ArrayList<>();
        double offsetX = GameConfig.BRICK_WIDTH;
        double offsetY = 75;
        double spacing = 1;

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                int type = layout[row][col];
                double x = offsetX + col * (GameConfig.BRICK_WIDTH + spacing);
                double y = offsetY + row * (GameConfig.BRICK_HEIGHT + spacing);

                Brick brick = null;

                switch (type) {
                    case Defs.NORMAL_BRICK:
                        brick = new NormalBrick(x, y);
                        break;
                    case Defs.EXPLOSIVE_BRICK:
                        brick = new ExplosiveBrick(x, y, bricks);
                        break;
                    case Defs.FALLING_BRICK:
                        brick = new FallingBrick(x, y);
                        break;
                    case Defs.ITEM_BRICK:
                        brick = new ItemBrick(x, y);
                        break;
                }

                if (brick != null) {
                    bricks.add(brick);
                }
            }
        }
        return bricks;
    }

    /**
     * Marks a level as unlocked.
     *
     * @param id The level number to unlock (1-based).
     */
    public static void unlockLevel(int id) {
        levelUnlocked[id - 1] = true;
    }

    /**
     * Checks whether a level is unlocked.
     *
     * @param id The level index (1-based).
     * @return {@code true} if the level is unlocked, {@code false} otherwise.
     */
    public static boolean isLevelUnlocked(int id) {
        return levelUnlocked[id - 1];
    }
}
