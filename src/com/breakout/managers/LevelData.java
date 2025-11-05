package com.breakout.managers;

/**
 * {@code LevelData} is a static utility class that stores the raw 2D array
 * layouts for all predefined levels in the game.
 * <p>
 * Each integer in the arrays corresponds to a specific brick type
 * (e.g., 0 for empty space, 1 for NormalBrick, 2 for ExplosiveBrick, etc.).
 * </p>
 */
public class LevelData {

    // NOTE: The mapping of integers to brick types is assumed to be defined in com.breakout.config.Defs

    /**
     * Layout for Level 1.
     * <p>Primarily composed of Normal Bricks (1).</p>
     */
    public static final int[][] level1 = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };


    /**
     * Layout for Level 2.
     * <p>Introduces Explosive Bricks (2).</p>
     */
    public static final int[][] level2 = {
            {1, 1, 1, 2, 1, 1, 1, 1},
            {1, 2, 1, 1, 1, 1, 2, 1},
            {2, 1, 1, 1, 2, 1, 1, 2},
            {1, 1, 2, 1, 1, 2, 1, 1},
            {1, 2, 1, 1, 1, 1, 2, 1},
            {1, 1, 1, 2, 1, 1, 1, 1},
            {2, 1, 1, 1, 2, 1, 1, 2},
            {1, 1, 1, 1, 1, 1, 1, 1}
    };

    /**
     * Layout for Level 3.
     * <p>Introduces Unbreakable Bricks (3).</p>
     */
    public static final int[][] level3 = {
            {3, 1, 1, 2, 1, 1, 1, 3},
            {1, 2, 1, 1, 3, 1, 2, 1},
            {2, 1, 3, 1, 2, 1, 1, 2},
            {1, 1, 2, 3, 1, 2, 1, 1},
            {1, 2, 1, 1, 1, 3, 2, 1},
            {1, 1, 1, 2, 1, 1, 1, 1},
            {2, 1, 1, 1, 2, 1, 3, 2},
            {1, 1, 1, 1, 1, 1, 1, 1}
    };

    /**
     * Layout for Level 4.
     * <p>Introduces Falling Bricks (4).</p>
     */
    public static final int[][] level4 = {
            {3, 1, 4, 2, 1, 4, 1, 3},
            {1, 2, 1, 4, 3, 4, 2, 1},
            {2, 4, 3, 1, 2, 1, 4, 2},
            {1, 1, 2, 3, 1, 2, 1, 4},
            {4, 2, 1, 1, 4, 3, 2, 1},
            {1, 4, 1, 2, 1, 4, 1, 1},
            {2, 1, 4, 1, 2, 1, 3, 2},
            {4, 4, 4, 4, 4, 4, 4, 4}
    };

    /**
     * Layout for Level 5.
     * <p>Introduces Item Bricks (5).</p>
     */
    public static final int[][] level5 = {
            {3, 1, 4, 2, 5, 4, 1, 3},
            {1, 2, 5, 4, 3, 4, 2, 1},
            {2, 4, 3, 1, 2, 5, 4, 2},
            {1, 5, 2, 3, 1, 2, 5, 4},
            {4, 2, 1, 5, 4, 3, 2, 1},
            {1, 4, 5, 2, 5, 4, 1, 1},
            {2, 1, 4, 1, 2, 5, 3, 2},
            {4, 4, 4, 4, 4, 4, 4, 4}
    };

    /**
     * Layout for Level 6.
     * <p>Introduces Invisible Ball Bricks (6).</p>
     */
    public static final int[][] level6 = {
            {6, 3, 4, 2, 5, 4, 6, 3},
            {1, 6, 5, 4, 3, 4, 2, 6},
            {2, 4, 3, 6, 2, 5, 4, 2},
            {6, 5, 2, 3, 6, 2, 5, 4},
            {4, 2, 6, 5, 4, 3, 2, 1},
            {1, 4, 6, 2, 5, 4, 6, 1},
            {2, 6, 4, 1, 2, 5, 3, 2},
            {6, 4, 4, 6, 4, 4, 6, 4}
    };
}