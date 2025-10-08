public class Brick extends GameObject {
    public final static int NORMAL_BRICK = 1;
    public final static int STRONG_BRICK = 2;

    private int type;
    private int hitPoints;
    private boolean destroyed = false;

    public Brick(double x, double y, int type) {
        super(x, y);
        this.type = type;
        hitPoints = type;
    }

    public void takeHit() {
        hitPoints--;
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    public char getSymbol() {
        if (destroyed) return ' ';
        switch (type) {
            case NORMAL_BRICK: return '*';
            case STRONG_BRICK: return '#';
            default: return ' ';
        }
    }

    @Override
    public void update(double dt) { }
}