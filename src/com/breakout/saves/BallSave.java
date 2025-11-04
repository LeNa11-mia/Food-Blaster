package com.breakout.saves;

import java.io.Serializable;

public class BallSave implements Serializable {
    private static final long serialVersionUID = 1L;

    private double x, y;
    private double velocityX, velocityY;
    private int diameter;

    public BallSave() {}

    public BallSave(double x, double y, double velocityX, double velocityY, int diameter) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.diameter = diameter;
    }

    // Getters and Setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getVelocityX() { return velocityX; }
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }

    public double getVelocityY() { return velocityY; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    public int getDiameter() { return diameter; }
    public void setDiameter(int diameter) { this.diameter = diameter; }
}
