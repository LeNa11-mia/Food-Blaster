package com.breakout.managers;

import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.entities.*;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.bricks.ExplosiveBrick;
import com.breakout.entities.bricks.FallingBrick;

import java.util.ArrayList;
import java.util.List;

public class Level extends LevelData {
    public static List<Brick> loadLevel(int id) {
        switch (id) {
            case Defs.LEVEL_EASY: return createLevel(LevelData.level1);
            case Defs.LEVEL_MEDIUM: return createLevel(LevelData.level2);
            case Defs.LEVEL_HARD: return createLevel(LevelData.level3);
            case Defs.LEVEL_BOSS: return createLevel(LevelData.level3);
            default: return new ArrayList<>();
        }
    }

    /**
     * Return a map of bricks including normal bricks and explosive bricks.
     * @param layout the layout of bricks
     */
    private static List<Brick> createLevel(int[][] layout) {
        List<Brick> bricks = new ArrayList<>();

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                int type = layout[row][col];
                double x = col * GameConfig.BRICK_WIDTH;
                double y = 50 + row * GameConfig.BRICK_HEIGHT;

                Brick b = null;
                if (type == Defs.NORMAL_BRICK) {
                    b = new Brick(x, y);
                }
                else if (type == Defs.EXPLOSIVE_BRICK) {
                    b = new ExplosiveBrick(x, y, bricks);
                }
                else if (type == 3) {
                    b = new FallingBrick(x, y);
                }
                if (b != null) bricks.add(b);
            }
        }
        return bricks;
    }
}
