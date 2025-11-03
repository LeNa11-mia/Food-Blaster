package com.breakout.entities.bricks;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.interfaces.Destructible;

/**
 * Brick - can be drawn and destroyed.
 */
public class Brick extends GameObject implements Destructible {
    protected boolean hit;
    protected boolean destroyed;

    public Brick(double x, double y) {
        super(x, y, GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT);
        sprite = GameConfig.NORMAL_BRICK_IMAGE;
        destroyed = false;
        hit = false;
    }

    @Override
    public void update(double deltaTime) {
        // Bricks do not move
    }

    @Override
    public void hit() {
        destroyed = true;
        hit = true;
        onDestroyed();
        // Bricks are destroyed when hit (default)
    }

    @Override
    public void onDestroyed() {} // No effect

    public void destroy() {
        destroyed = true;
    }

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
    public String getType() {
        return "NORMAL";
    }

    // Thêm method để tính row/col tạm thời (nếu cần)
    public int getRow() {
        return (int)(getY() / GameConfig.BRICK_HEIGHT);
    }

    public int getCol() {
        return (int)(getX() / GameConfig.BRICK_WIDTH);
    }
}
