package com.breakout.saves;

import java.io.Serializable;

public class PaddleSave implements Serializable {
    private static final long serialVersionUID = 1L;

    private double x, y;
    private int width, height;
    private double velocity;

    public PaddleSave() {}

    public PaddleSave(double x, double y, int width, int height, double velocity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
    }

    // Getters and Setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public double getVelocity() { return velocity; }
    public void setVelocity(double velocity) { this.velocity = velocity; }
}
