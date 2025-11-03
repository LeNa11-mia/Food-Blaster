package com.breakout.entities;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

import javax.swing.*;

/**
 * Ball - handles movement and rendering.
 */
public class Ball extends GameObject {
    private double vx = GameConfig.BALL_SPEED; // X velocity
    private double vy = GameConfig.BALL_SPEED; // Y velocity

    public Ball(double x, double y) {
        super(x, y, GameConfig.BALL_WIDTH, GameConfig.BALL_HEIGHT); // Ball size: 15x15 pixels
        sprite = GameConfig.BALL_IMAGE;
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
