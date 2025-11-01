package com.breakout.entities;

import com.breakout.core.GameObject;

import javax.swing.*;

/**
 * Brick - can be drawn and destroyed.
 */
public class Brick extends GameObject {
    private boolean destroyed = false;
    public static final int WIDTH = 60;
    public static final int HEIGHT = 24;

    public Brick(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        sprite = new ImageIcon("assets/brick.png");
    }

    @Override
    public void update(double deltaTime) {
        // Bricks do not move
    }

    public void hit() {
        destroyed = true;
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
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
        return (int)(getY() / HEIGHT);
    }

    public int getCol() {
        return (int)(getX() / WIDTH);
    }
}
