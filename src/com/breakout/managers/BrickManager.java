public class BrickManager {
    private final LevelManager[] levels;
    private int current = 0;

    public BrickManager() {
        // define levels: each int[][] row uses width = 30
        // rows are compact (bricks starting at x=0)
        int[][] l1 = new int[][] {
                // 10 cols example; Game width = 30
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,1,2,1,2,1,2,1,1,0,0,1,1,2,1,2,1,1,1,0,0,1,1,1,2,1,1,0,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        int[][] l2 = new int[][] {
                {2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2,0,2}
        };
        int[][] l3 = new int[][] {
                {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2},
                {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}
        };

        levels = new LevelManager[] {
                new LevelManager(30, 20, l1, 1),
                new LevelManager(30, 20, l2, 1),
                new LevelManager(30, 20, l3, 1)
        };
    }

    public LevelManager getCurrentLevel() {
        return levels[current];
    }

    public int getCurrentLevelIndex() {
        return current;
    }

    public boolean nextLevel() {
        if (current + 1 < levels.length) {
            current++;
            return true;
        }
        return false;
    }
}
