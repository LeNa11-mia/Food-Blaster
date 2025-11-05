package com.test;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.entities.Ball;
import com.breakout.entities.bricks.*;
import com.breakout.managers.GameManager;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {
    private GameManager gameManager;

    @BeforeAll
    static void initGame() {
        final JFrame frame = new JFrame(GameConfig.WINDOW_TITLE);
        Game.initGame(frame);
    }

    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
    }

    /**
     * Verifies that the first score when entering the game must be 0.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void testInitialScoreForAllLevels(int level) {
        // Act
        gameManager.startGame(level);

        // Assert
        assertEquals(0, gameManager.getScore());
    }

    /**
     * Verifies that collisions with a breakable brick increases player's score accurately.
     * Note: Since UnbreakableBrick.hit() does nothing by design, there's no need to test
     * that collisions with unbreakable bricks don't increase the score.
     */
    @ParameterizedTest
    @MethodSource("brickFunctionProvider")
    void testScoreAfterCollidingWithBrick(Function<Ball, Brick> brickFunction) {
        // Arrange
        initGame();
        gameManager.startBall();

        Ball ball = gameManager.getBall();
        List<Brick> bricks = gameManager.getBricks();

        // Clear existing bricks and place a single test brick with arbitrary position
        bricks.clear();
        Brick testBrick = brickFunction.apply(ball);
        bricks.add(testBrick);

        int scoreBefore = gameManager.getScore();

        // Position the ball centered under the test brick, 1px below
        ball.setPosition(
                testBrick.getX() + testBrick.getWidth() / 2.0 - ball.getWidth() / 2.0,
                testBrick.getY() + testBrick.getHeight() + 1.0
        );
        ball.setVelocity(0, -300); // launch upward

        // Simulate frames until the brick is destroyed
        final int maxFrames = 1000;
        final double dt = 0.016;
        boolean destroyed = false;
        for (int i = 0; i < maxFrames; i++) {
            gameManager.update(dt, false, false);
            if (testBrick.isDestroyed()) {
                destroyed = true;
                break;
            }
        }

        // Assert: brick should be destroyed and score should increase exactly by 1
        assertTrue(destroyed);
        assertEquals(scoreBefore + 1, gameManager.getScore());
    }

    /**
     * Method source for parameterized test.
     * Each entry wraps a function that creates a new Brick instance at a given position.
     * JUnit retrieves these via Stream<Arguments> and runs the test once per entry.
     */
    private static Stream<Arguments> brickFunctionProvider() {
        return Stream.of(
                Arguments.of((Function<Ball, Brick>) ball -> new NormalBrick(100, 100)),
                Arguments.of((Function<Ball, Brick>) ball -> new FallingBrick(150, 100)),
                Arguments.of((Function<Ball, Brick>) ball -> new ItemBrick(200, 100)),
                Arguments.of((Function<Ball, Brick>) ball -> new InvisibleBallBrick(250, 100, ball))
        );
    }


}