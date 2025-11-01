package com.breakout.entities;

import java.awt.*;
import com.breakout.core.GameObject;

import javax.swing.*;

/**
 * Paddle - moves left and right.
 */
public class Paddle extends GameObject {
    private double speed = 400; // Movement speed

    public Paddle(double x, double y) {
        super(x, y, 100, 15); // Paddle size: 100x15 pixels
        sprite = new ImageIcon("assets/paddle.png");
    }

    @Override
    public void update(double deltaTime) {
    }

    public void moveLeft(double deltaTime, double screenWidth) {
        x -= speed * deltaTime;
        if (x < 0) x = 0; // Keep within screen bounds
    }

    public void moveRight(double deltaTime, double screenWidth) {
        x += speed * deltaTime;
        double maxX = screenWidth - width - 15;
        if (x > maxX) {
            x = maxX;
    }
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWidth() {
        return super.getWidth();
    }
}
