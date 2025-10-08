import java.util.ArrayList;
import java.util.List;

public class Level {
    private final List<Brick> bricks = new ArrayList<>();
    private final int width;
    private final int height;

    public Level(int width, int height, int[][] layout, int yOffset) {
        this.width = width;
        this.height = height;
        loadLayout(layout, yOffset);
    }

    private void loadLayout(int[][] layout, int yOffset) {
        for (int row = 0; row < layout.length; row++) {
            int[] cols = layout[row];
            for (int col = 0; col < cols.length; col++) {
                int hp = cols[col];
                if (hp > 0) {
                    // x = col, y = row + yOffset
                    bricks.add(new Brick(col, row + yOffset, hp));
                }
            }
        }
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public Brick getBrickAt(int x, int y) {
        for (Brick b : bricks) {
            if (!b.isDestroyed() && b.getX() == x && b.getY() == y) {
                return b;
            }
        }
        return null;
    }

    public boolean allDestroyed() {
        for (Brick b : bricks) {
            if (!b.isDestroyed()) {
                return false;
            }
        }
        return true;
    }
}