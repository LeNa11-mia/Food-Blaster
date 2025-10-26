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
    private Rectangle gameBounds;

    public Ball(double x, double y, Rectangle gameBounds) {
        super(x, y, 15, 15); // Ball size: 15x15 pixels
        this.gameBounds=gameBounds;
        sprite = new ImageIcon("assets/ball.png");
    }

    @Override
    public void update(double deltaTime) {
        // Move the ball
        x += vx * deltaTime;
        y += vy * deltaTime;
        correctWallCollision();
    }

    private void correctWallCollision() {
        //va chạm trái
        if (x < gameBounds.x) {
            x = gameBounds.x;  // Đẩy bóng ra khỏi tường
            bounceX();
        }
        // Va chạm tường PHẢI
        else if (x + width > gameBounds.x + gameBounds.width) {
            x = gameBounds.x + gameBounds.width - width;
            bounceX();
        }

        // Va chạm tường TRÊN
        else if (y < gameBounds.y) {
            y = gameBounds.y;  // Đẩy bóng ra khỏi tường
            bounceY();
        }
    }

    // Reverse direction when hitting walls
    public void bounceX() { vx = -vx; }
    public void bounceY() { vy = -vy; }

    public double getVx() { return vx; }
    public double getVy() { return vy; }
}
