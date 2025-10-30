package com.breakout.entities;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExplosiveBrick extends Brick {
    private final int explosionRadius;    //explosion radius by bricks
    private List<Brick> bricks;

    public ExplosiveBrick(double x, double y, List<Brick> bricks, int explosionRadius) {
        super(x, y);
        this.bricks = bricks;
        this.explosionRadius = explosionRadius;
        sprite = new ImageIcon("assets/explosiveBrick.png");
    }

    @Override
    public void hit() {
        super.hit();
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
            if (dx <= explosionRadius * Brick.WIDTH && dy <= explosionRadius * Brick.HEIGHT) {
                b.hit();
            }
        }
    }
}