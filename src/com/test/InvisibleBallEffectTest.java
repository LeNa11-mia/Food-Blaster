package com.test;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.entities.Ball;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.bricks.InvisibleBallBrick;
import com.breakout.managers.GameManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test that colliding with the invisible ball brick ( witch potion brick )
 * correctly triggers the invisibility effect on the ball.
 */
class InvisibleBallEffectTest {

    private GameManager gameManager;

    @BeforeAll
    static void initGame() {
        final JFrame frame = new JFrame(GameConfig.WINDOW_TITLE);
        Game.initGame(frame);
    }

    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
        gameManager.startGame(1);
    }

    @Test
    void testBallInvisibility() throws InterruptedException {
        gameManager.startBall();

        Ball ball = gameManager.getBall();
        List<Brick> bricks = gameManager.getBricks();

        bricks.clear();
        InvisibleBallBrick testBrick = new InvisibleBallBrick(100, 50, ball);
        bricks.add(testBrick);

        assertTrue(ball.isVisible());

        ball.setPosition(
                testBrick.getX() + testBrick.getWidth() / 2.0 - ball.getWidth() / 2.0,
                testBrick.getY() + testBrick.getHeight() + 1.0
        );
        ball.setVelocity(0, -300);

        final int maxFrames = 1000;
        final double dt = 0.016;

        for (int i = 0; i < maxFrames; i++) {
            gameManager.update(dt, false, false);
            if (testBrick.isDestroyed()) {
                break;
            }
        }

        Thread.sleep(100);
        assertFalse(ball.isVisible());

        Thread.sleep(5000);
        assertTrue(ball.isVisible());
    }
}