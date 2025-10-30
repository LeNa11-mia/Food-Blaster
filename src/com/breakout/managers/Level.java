package com.breakout.managers;

import com.breakout.entities.bricks.Brick;
import com.breakout.entities.bricks.ExplosiveBrick;
import com.breakout.entities.bricks.FallingBrick;

import java.util.ArrayList;
import java.util.List;

public class Level extends LevelData {
    public static List<Brick> loadLevel(int id) {
        switch (id) {
            case 1: return createLevel(LevelData.level1);
            case 2: return createLevel(LevelData.level2);
            case 3: return createLevel(LevelData.level3);
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
                double x = col * Brick.WIDTH;
                double y = 50 + row * Brick.HEIGHT;

                Brick b = null;
                if (type == 1) {
                    b = new Brick(x, y);
                }
                else if (type == 2) {
                    b = new ExplosiveBrick(x, y, bricks, 1);
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
