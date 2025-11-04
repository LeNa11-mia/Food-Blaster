package com.breakout.saves;

import java.io.Serializable;

public class BrickSave implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x, y;
    private int width, height;
    private boolean destroyed;
    private int hitPoints;
    private int maxHitPoints;
    private int color;
    private int points;

    public BrickSave() {}

    public BrickSave(int x, int y, int width, int height, boolean destroyed,
                     int hitPoints, int maxHitPoints, int color, int points) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = destroyed;
        this.hitPoints = hitPoints;
        this.maxHitPoints = maxHitPoints;
        this.color = color;
        this.points = points;
    }

    // Getters and Setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public boolean isDestroyed() { return destroyed; }
    public void setDestroyed(boolean destroyed) { this.destroyed = destroyed; }

    public int getHitPoints() { return hitPoints; }
    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

    public int getMaxHitPoints() { return maxHitPoints; }
    public void setMaxHitPoints(int maxHitPoints) { this.maxHitPoints = maxHitPoints; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
