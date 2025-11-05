package com.breakout.managers;

import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.entities.*;
import com.breakout.entities.bricks.*;

import java.util.ArrayList;
import java.util.List;

public class Level extends LevelData {

    private static boolean[] levelUnlocked = new boolean[GameConfig.TOTAL_LEVELS];

    public static List<Brick> loadLevel(int id, Ball ball) {
        switch (id) {
            case 1: return createLevel(LevelData.level1, ball);
            case 2: return createLevel(LevelData.level2, ball);
            case 3: return createLevel(LevelData.level3, ball);
            case 4: return createLevel(LevelData.level3, ball);
            case 5: return createLevel(LevelData.level3, ball);
            case 6: return createLevel(LevelData.level3, ball);
            default: return new ArrayList<>();
        }
    }

    /**
     * Return a map of bricks including normal bricks and explosive bricks.
     *
     * @param layout the layout of bricks
     * @param ball
     */
    private static List<Brick> createLevel(int[][] layout, Ball ball) {
        List<Brick> bricks = new ArrayList<>();
        double offsetX = GameConfig.BRICK_WIDTH;
        double offsetY = 85;
        double spacing = 1;

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                int type = layout[row][col];
                double x = offsetX + col * (GameConfig.BRICK_WIDTH + spacing);
                double y = offsetY + row * (GameConfig.BRICK_HEIGHT + spacing);

                Brick b = null;
                if (type == Defs.NORMAL_BRICK) {
                    b = new NormalBrick(x, y);
                }
                else if (type == Defs.EXPLOSIVE_BRICK) {
                    b = new ExplosiveBrick(x, y, bricks);
                }
                else if (type == Defs.FALLING_BRICK) {
                    b = new FallingBrick(x, y);
                }
                else if (type == Defs.ITEM_BRICK) {
                    b = new ItemBrick(x, y);
                }
                else if (type == Defs.INVISIBLE_BALL_BRICK) {
                    b = new InvisibleBallBrick(x, y, ball);
                }
                if (b != null) bricks.add(b);
            }
        }
        return bricks;
    }

    public static void unlockLevel(int id) {
        levelUnlocked[id - 1] = true;
    }

    public static boolean isLevelUnlocked(int id) {
        return levelUnlocked[id - 1];
    }
}
