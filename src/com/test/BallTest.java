package com.test;

import com.breakout.config.GameConfig;
import com.breakout.entities.Ball;
import com.breakout.entities.Paddle;
import com.breakout.entities.bricks.NormalBrick;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the {@link Ball class}.
 * Covers physics collisions of Ball - Paddle and Ball - Brick.
 */
class BallTest {

    private Ball ball;
    private Paddle paddle;
    private NormalBrick brick;

    @BeforeEach
    void setUp() {
        ball = new Ball(400, 300);
        paddle = new Paddle(350, 550);
        brick = new NormalBrick(200, 100);
    }

    /**
     * Verifies that the ball moves correctly after colliding
     * with the left or right edge of the object
     */
    @Test
    @DisplayName("BounceX reverses horizontal velocity")
    void testBounceX() {
        ball.setVelocity(5, 3);
        ball.bounceX();
        assertEquals(-5, ball.getVx()); //
    }

    /**
     * Verifies that the ball moves correctly after colliding
     * with the top or bottom edge of the object
     */
    @Test
    @DisplayName("BounceY reverses vertical velocity")
    void testBounceY() {
        ball.setVelocity(5, 3);
        ball.bounceY();
        assertEquals(-3, ball.getVy());
    }

    /**
     * Verifies that the ball moves with minimal horizontal velocity
     * after hitting the center
     */
    @Test
    @DisplayName("Center hit bounces ball upward with minimal horizontal velocity")
    void testPaddleCollisionCenter() {
        // Position ball at center of paddle
        ball.setPosition(
                paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2,
                paddle.getY() - ball.getHeight()
        );
        ball.setVelocity(0, 5);

        ball.collisionWithPaddle(paddle);

        assertTrue(ball.getVy() < 0, "Ball should bounce upward");
        assertTrue(Math.abs(ball.getVx()) < 2, "Horizontal velocity should be minimal");
    }

    /**
     * Verifies that the ball moves correctly after hitting
     * the left edge of the paddle
     */
    @Test
    @DisplayName("Left edge hit bounces ball left and upward")
    void testPaddleCollisionLeftEdge() {
        // Position ball at left edge
        ball.setPosition(
                paddle.getX(),
                paddle.getY() - ball.getHeight()
        );
        ball.setVelocity(0, 5);

        ball.collisionWithPaddle(paddle);

        assertTrue(ball.getVy() < 0, "Ball should bounce upward");
        assertTrue(ball.getVx() < 0, "Ball should bounce left");
    }

    /**
     * Verifies that the ball moves correctly after hitting
     * the right edge of the paddle
     */
    @Test
    @DisplayName("Right edge hit bounces ball right and upward")
    void testPaddleCollisionRightEdge() {
        // Position ball at right edge
        ball.setPosition(
                paddle.getX() + paddle.getWidth() - ball.getWidth(),
                paddle.getY() - ball.getHeight()
        );
        ball.setVelocity(0, 5);

        ball.collisionWithPaddle(paddle);

        assertTrue(ball.getVy() < 0, "Ball should bounce upward");
        assertTrue(ball.getVx() > 0, "Ball should bounce right");
    }

    /**
     * Verifies that moving paddle transfers velocity to ball
     */
    @Test
    @DisplayName("Moving paddle transfers velocity to ball")
    void testPaddleVelocityTransfer() {
        // Make paddle move right
        paddle.moveRight(1.0, GameConfig.SCREEN_WIDTH);
        double paddleVelocity = paddle.getVx();

        ball.setPosition(
                paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2,
                paddle.getY() - ball.getHeight()
        );
        ball.setVelocity(0, 5);

        ball.collisionWithPaddle(paddle);

        assertTrue(ball.getVx() > 0, "Ball should gain velocity from moving paddle");
        assertTrue(paddleVelocity > 0, "Paddle should be moving");
    }

    /**
     * Tests that ball regains its BALL_SPEED after paddle collision.
     */
    @Test
    @DisplayName("Ball speed stays consistent after paddle collision")
    void testBallSpeedConsistency() {
        ball.setPosition(
                paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2,
                paddle.getY() - ball.getHeight()
        );
        ball.setVelocity(3, 4);

        ball.collisionWithPaddle(paddle);

        double speed = Math.sqrt(ball.getVx() * ball.getVx() + ball.getVy() * ball.getVy());
        assertEquals(GameConfig.BALL_SPEED, speed, 0.5, "Speed should be normalized");
    }
}