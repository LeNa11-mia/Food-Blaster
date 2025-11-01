package com.breakout.entities;

import java.awt.*;
import com.breakout.core.GameObject;

import javax.swing.*;

/**
 * Ball - handles movement and rendering.
 */
public class Ball extends GameObject {
    private double vx = 200; // X velocity
    private double vy = 200; // Y velocity

    public Ball(double x, double y) {
        super(x, y, 15, 15); // Ball size: 15x15 pixels
        sprite = new ImageIcon("assets/ball.png");
    }

    @Override
    public void update(double deltaTime) {
        // Move the ball
        x += vx * deltaTime;
        y += vy * deltaTime;
    }

    // Reverse direction when hitting walls
    public void bounceX() {
        vx = -vx;
    }

    public void bounceY() {
        vy = -vy;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }
}
