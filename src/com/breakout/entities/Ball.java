package com.breakout.entities;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

/**
 * Ball - handles movement and rendering.
 */
public class Ball extends GameObject {
    private double vx = 0; // X velocity
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

    // Xử lý va chạm từ các hướng
    public void collisionFromSides(GameObject obj) {
        double overlapX = Math.min(x + getWidth(), obj.getX() + obj.getWidth()) - Math.max(x, obj.getX());
        // Mép phải nhỏ hơn - mép trái lớn hơn = phần trùng nhau theo trục X
        double overlapY = Math.min(y + getHeight(), obj.getY() + obj.getHeight()) - Math.max(y, obj.getY());
        // Mép trên nhỏ hơn - mép dưới lớn hơn = phần trùng nhau theo trục Y

        if (overlapX == 0 || overlapY == 0) {
            return;
        }

        if (overlapX < overlapY) { // Mới va chạm theo trục X
            bounceX();
        } else {
            // Bóng chạm mặt trên của paddle có cách tính va chạm riêng
            if (obj instanceof Paddle) {
                collisionWithPaddle((Paddle) obj);
            } else {
                bounceY();
            }
        }
    }

    // Xử lý va chạm với paddle
    public void collisionWithPaddle(Paddle paddle) {
        setPaddleBounceVelocity(paddle); // Tính góc bật tùy vào vị trí va chạm
        addPaddleVelocity(paddle); // Vận tốc của paddle cũng ảnh hưởng đến bóng
    }

    private void setPaddleBounceVelocity(Paddle paddle) {
        // Khoảng cách từ tâm bóng đến giữa paddle
        double distance = (x + getWidth() / 2) - (paddle.getX() + paddle.getWidth() / 2);

        double sin = distance / (paddle.getWidth() / 2); // Góc giữa paddle và bóng bật lên
        sin = Math.max(-1, Math.min(1, sin)); // Sin trong khoảng [-1, 1]

        double angle = Math.toRadians(60) * sin; // Góc bật trong khoảng [-60, 60]

        double newVx = Math.sin(angle) * GameConfig.BALL_SPEED;
        double newVy = -Math.cos(angle) * GameConfig.BALL_SPEED; // Trục y hướng xuống
        setVelocity(newVx, newVy);
    }

    private void addPaddleVelocity(Paddle paddle) {
        setVelocity(vx + GameConfig.VELOCITY_TRANSFER_TO_BALL * paddle.getVx(), vy);
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
