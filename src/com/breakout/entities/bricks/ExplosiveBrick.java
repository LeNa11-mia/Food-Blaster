package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;

import java.util.List;

public class ExplosiveBrick extends Brick {
    private final int explosionRadius;    //explosion radius by bricks
    private List<Brick> bricks;

    public ExplosiveBrick(double x, double y, List<Brick> bricks) {
        super(x, y);
        this.bricks = bricks;
        this.explosionRadius = GameConfig.EXPLOSION_RADIUS;
        sprite = GameConfig.EXPLOSIVE_BRICK_IMAGE;
    }

    @Override
    public void onDestroyed() {
        explode();
    }

    /**
     * All bricks in explosion radius explode.
     */
    private void explode() {
        boolean exploded = true;

        double cx = getX();
        double cy = getY();

        for (Brick b : bricks) {
            if (b == this || b.isDestroyed()) {
                continue;
            }

            double dx = Math.abs(b.getX() - cx);
            double dy = Math.abs(b.getY() - cy);

            // If b in explosion radius (radius * brick's width or length)
            if (dx <= explosionRadius * GameConfig.BRICK_WIDTH && dy <= explosionRadius * GameConfig.BRICK_HEIGHT) {
                b.hit();
            }
        }
    }
}