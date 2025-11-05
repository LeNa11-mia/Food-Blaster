package com.breakout.entities;

import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;

/**
 * Represents the ball in the game with movement and collision handling.
 */
public class Ball extends GameObject {
    private double vx = 0;
    private double vy = GameConfig.BALL_SPEED;
    private boolean visible = true;

    public Ball(double x, double y) {
        super(x, y, GameConfig.BALL_WIDTH, GameConfig.BALL_HEIGHT);
        sprite = GameConfig.BALL_IMAGE;
    }

    @Override
    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
    }

    /**
     * Handles collision detection and response from all sides of objects.
     *
     * @param obj the object to check collision with
     */
    public void collisionFromSides(GameObject obj) {
        double overlapX = Math.min(x + getWidth(), obj.getX() + obj.getWidth()) - Math.max(x, obj.getX());
        double overlapY = Math.min(y + getHeight(), obj.getY() + obj.getHeight()) - Math.max(y, obj.getY());

        if (overlapX == 0 || overlapY == 0) {
            return;
        }

        if (overlapX < overlapY) {
            bounceX();
        } else {
            if (obj instanceof Paddle) {
                collisionWithPaddle((Paddle) obj);
            } else {
                bounceY();
            }
        }
    }

    /**
     * Handles specialized collision response with the paddle.
     *
     * @param paddle the paddle object collided with
     */
    public void collisionWithPaddle(Paddle paddle) {
        setPaddleBounceVelocity(paddle);
        addPaddleVelocity(paddle);
    }

    private void setPaddleBounceVelocity(Paddle paddle) {
        double distance = (x + getWidth() / 2) - (paddle.getX() + paddle.getWidth() / 2);
        double sin = distance / (paddle.getWidth() / 2);
        sin = Math.max(-1, Math.min(1, sin));

        double angle = Math.toRadians(60) * sin;
        double newVx = Math.sin(angle) * GameConfig.BALL_SPEED;
        double newVy = -Math.cos(angle) * GameConfig.BALL_SPEED;
        setVelocity(newVx, newVy);
    }

    private void addPaddleVelocity(Paddle paddle) {
        setVelocity(vx + GameConfig.VELOCITY_TRANSFER_TO_BALL * paddle.getVx(), vy);
    }

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

    /**
     * Checks if the ball is currently visible.
     *
     * @return true if the ball is visible, false otherwise
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility state of the ball.
     *
     * @param visible the visibility state to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}