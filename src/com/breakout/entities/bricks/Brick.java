package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

/**
 * Brick - can be drawn and destroyed.
 */
public abstract class Brick extends GameObject {
    protected boolean hit;
    protected boolean destroyed;

    public Brick(double x, double y) {
        super(x, y, GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT);
        hit = false;
        destroyed = false;
    }

    @Override
    public void update(double deltaTime) {}

    public void destroy() {
        destroyed = true;
    }

    public abstract void hit(); // Mỗi loại gạch có hành vi khác nhau sau va chạm

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isHit() {
        return hit;
    }

    // Thêm method getter cho health (luôn trả về 1)
    public int getHealth() {
            return destroyed ? 0 : 1;
    }

    // Thêm method setter cho health (không cần làm gì vì health luôn là 1)
    public void setHealth(int health) {
        if (health <= 0) {
            destroyed = true;
        } else {
            destroyed = false;
        }
    }

    // Thêm method getter cho type
    // public abstract String getType();

    // Thêm method để tính row/col tạm thời (nếu cần)
    public int getRow() {
        return (int)(getY() / GameConfig.BRICK_HEIGHT);
    }

    public int getCol() {
        return (int)(getX() / GameConfig.BRICK_WIDTH);
    }
}
