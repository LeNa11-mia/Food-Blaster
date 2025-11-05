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
     * Improved collision handler:
     *
     * - Uses positional correction (push the ball outside the other object by the penetration amount).
     * - Reflects the velocity along the axis of collision (X or Y) to produce a correct bounce.
     * - Handles the "corner" / ambiguous case by using the ball center relative to the object's center.
     * - Applies a tiny epsilon displacement after correction to avoid immediate re-detection on the next frame.
     *
     * Why this fix:
     * - The original code only inverted velocity (bounceX/bounceY) but left the ball overlapping the object.
     *   That causes the ball to still be inside the brick on the next frame, so collision is detected again,
     *   and the ball can become stuck (repeated collisions every frame).
     * - By pushing the ball outside the object immediately (positional correction) we remove the overlap,
     *   so the next frame the ball is no longer colliding and normal movement resumes.
     */
    public void collisionFromSides(GameObject obj) {
        // compute overlap along each axis (positive = overlap amount)
        double overlapX = Math.min(x + getWidth(), obj.getX() + obj.getWidth()) - Math.max(x, obj.getX());
        double overlapY = Math.min(y + getHeight(), obj.getY() + obj.getHeight()) - Math.max(y, obj.getY());

        // no overlap -> nothing to do
        if (overlapX <= 0 || overlapY <= 0) {
            return;
        }

        // small epsilon to push the ball slightly outside to avoid re-collision due to float rounding
        final double EPS = 1e-6;

        // Decide collision axis: smaller penetration is the axis of minimum translation (MTV)
        // i.e., push the ball out along the smallest overlap direction.
        if (overlapX < overlapY) {
            // X-axis collision: move ball left or right depending on where it is relative to obj
            // Determine whether ball is to the left or right of obj center
            double ballCenterX = x + getWidth() / 2.0;
            double objCenterX = obj.getX() + obj.getWidth() / 2.0;

            if (ballCenterX < objCenterX) {
                // ball is on the left -> push it left
                x -= (overlapX + EPS);
            } else {
                // ball is on the right -> push it right
                x += (overlapX + EPS);
            }

            // reflect horizontal velocity
            // Use bounceX() to preserve existing logic (e.g. any special handling), but ensure magnitude consistency.
            bounceX();

            // If colliding with the paddle, transfer paddle momentum
            if (obj instanceof Paddle) {
                addPaddleVelocity((Paddle) obj);
            }
        } else if (overlapY < overlapX) {
            // Y-axis collision: move ball up or down depending on relative position
            double ballCenterY = y + getHeight() / 2.0;
            double objCenterY = obj.getY() + obj.getHeight() / 2.0;

            if (ballCenterY < objCenterY) {
                // ball is above -> push it upward
                y -= (overlapY + EPS);
            } else {
                // ball is below -> push it downward
                y += (overlapY + EPS);
            }

            // If it's a paddle, do paddle-specific collision (angle) handling
            if (obj instanceof Paddle) {
                collisionWithPaddle((Paddle) obj);
            } else {
                // normal brick or wall: reflect vertical velocity
                bounceY();
            }
        } else {
            // overlapX == overlapY (ambiguous/corner case)
            // Handle as a corner collision: compute nearest point and reflect using normal direction.
            double ballCenterX = x + getWidth() / 2.0;
            double ballCenterY = y + getHeight() / 2.0;
            double closestX = Math.max(obj.getX(), Math.min(ballCenterX, obj.getX() + obj.getWidth()));
            double closestY = Math.max(obj.getY(), Math.min(ballCenterY, obj.getY() + obj.getHeight()));

            double nx = ballCenterX - closestX;
            double ny = ballCenterY - closestY;
            double len = Math.hypot(nx, ny);

            if (len == 0) {
                // Degenerate case: ball center exactly on corner; fallback to axis based on velocity
                if (Math.abs(getVx()) > Math.abs(getVy())) {
                    // treat as X collision
                    if (ballCenterX < obj.getX() + obj.getWidth() / 2.0) {
                        x -= (overlapX + EPS);
                    } else {
                        x += (overlapX + EPS);
                    }
                    bounceX();
                    if (obj instanceof Paddle) addPaddleVelocity((Paddle) obj);
                } else {
                    // treat as Y collision
                    if (ballCenterY < obj.getY() + obj.getHeight() / 2.0) {
                        y -= (overlapY + EPS);
                    } else {
                        y += (overlapY + EPS);
                    }
                    if (obj instanceof Paddle) collisionWithPaddle((Paddle) obj);
                    else bounceY();
                }
            } else {
                // Normalize normal vector
                nx /= len;
                ny /= len;

                // Push ball out along the normal by the minimal penetration (use average of overlaps as safe estimate)
                double penetration = Math.min(overlapX, overlapY);
                x += nx * (penetration + EPS);
                y += ny * (penetration + EPS);

                // Reflect velocity along the normal: v' = v - 2*(v·n)*n
                double vxn = getVx();
                double vyn = getVy();
                double vDotN = vxn * nx + vyn * ny;
                double newVx = vxn - 2 * vDotN * nx;
                double newVy = vyn - 2 * vDotN * ny;
                setVelocity(newVx, newVy);

                // If it was a paddle, we still want paddle-specific adjustments
                if (obj instanceof Paddle) {
                    // collisionWithPaddle() will set the angle and add paddle momentum
                    collisionWithPaddle((Paddle) obj);
                }
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