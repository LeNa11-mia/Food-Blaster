package com.breakout.managers;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.entities.*;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.bricks.FallingBrick;
import com.breakout.entities.items.Item;
import com.breakout.saves.BallSave;
import com.breakout.saves.BrickSave;
import com.breakout.saves.GameSave;
import com.breakout.saves.PaddleSave;

import java.util.*;
import java.util.List;

/**
 * Main gameplay manager that controls game logic, state, and object interactions.
 * Handles ball physics, collisions, scoring, level progression, and save/load functionality.
 * Manages game objects including ball, paddle, bricks, and items.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class GameManager {
    /** Temporary message displayed on screen with timer */
    private String screenMessage = null;

    /** Timer for screen message display duration */
    private double messageTimer = 0.0;

    /** Duration in seconds for screen message display */
    private static final double MESSAGE_DURATION = 2.0;

    /** The game ball object */
    private Ball ball;

    /** The player's paddle object */
    private Paddle paddle;

    /** List of all bricks in the current level */
    private List<Brick> bricks;

    /** List of currently active items falling on screen */
    private List<Item> activeItems;

    /** Current player score */
    private int score;

    /** Current number of player lives */
    private int lives;

    /** Current level number */
    private int currentLevel;

    /** Game pause state */
    private boolean paused = false;

    /** Whether the ball has started moving */
    private boolean ballStarted = false;

    /**
     * Constructs a new GameManager and initializes game state.
     * Unlocks the first level and creates initial game objects.
     */
    public GameManager() {
        Level.unlockLevel(1); // Unlock first level

        // Initialize game objects
        ball = new Ball(GameConfig.SCREEN_WIDTH/2.0, GameConfig.SCREEN_HEIGHT/2.0);
        paddle = new Paddle(GameConfig.SCREEN_WIDTH/2.0 - 50, GameConfig.SCREEN_HEIGHT - 50);
        bricks = new ArrayList<>();
        activeItems = new ArrayList<>();
        lives = 1; // Only 1 life as per requirement
    }

    /**
     * Displays a temporary message on the game screen.
     *
     * @param message the text to display
     */
    public void showMessageOnScreen(String message) {
        this.screenMessage = message;
        this.messageTimer = MESSAGE_DURATION; // Start countdown
    }

    /**
     * Gets the current screen message.
     *
     * @return String the current message or null if no message
     */
    public String getScreenMessage() {
        return screenMessage;
    }

    /**
     * Adds an item to the active items list for updating and rendering.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        this.activeItems.add(item);
    }

    /**
     * Gets the list of currently active items.
     *
     * @return List<Item> the active items
     */
    public List<Item> getActiveItems() {
        return activeItems;
    }

    /**
     * Checks if the game is currently paused.
     *
     * @return boolean true if game is paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Toggles the game's pause state.
     */
    public void togglePause() {
        paused = !paused;
    }

    /**
     * Checks if the ball has started moving.
     *
     * @return boolean true if ball is in motion
     */
    public boolean hasBallStarted() {
        return ballStarted;
    }

    /**
     * Starts the ball movement.
     */
    public void startBall() {
        ballStarted = true;
    }

    /**
     * Starts a new game at the specified level.
     *
     * @param level the level number to start
     */
    public void startGame(int level) {
        currentLevel = level;

        bricks = Level.loadLevel(level);
        resetBall();
        resetPaddle();
        lives = 1;
        score = 0;
        activeItems.clear();
        ballStarted = false;
    }

    /**
     * Continues game from a saved state.
     * Loads saved game data and restores game state.
     */
    public void continueGame() {
        SaveManager.GameLoadResult result = SaveManager.loadGame();
        if (result.isSuccess()) {
            loadSavedGame(result.getGameSave());
            ballStarted = false; // Don't move immediately
            ball.setVelocity(result.getGameSave().getBallData().getVelocityX(),
                    result.getGameSave().getBallData().getVelocityY());
        }
    }

    /**
     * Checks if a saved game exists that can be continued.
     *
     * @return boolean true if a saved game exists
     */
    public boolean canContinueGame() {
        return SaveManager.saveExists();
    }

    /**
     * Gets information about the saved game for display purposes.
     *
     * @return String formatted save game information
     */
    public String getSaveInfo() {
        if (!canContinueGame()) {
            return "No saved game";
        }

        SaveManager.GameLoadResult result = SaveManager.loadGame();
        if (result.isSuccess()) {
            GameSave savedGame = result.getGameSave();
            return String.format("Level: %d - Score: %d - Lives: %d",
                    savedGame.getLevel(), savedGame.getScore(), savedGame.getLives());
        }
        return "Saved game";
    }

    /**
     * Main game update loop. Handles physics, collisions, and game state.
     *
     * @param deltaTime time elapsed since last update in seconds
     * @param leftPressed whether left movement key is pressed
     * @param rightPressed whether right movement key is pressed
     */
    public void update(double deltaTime, boolean leftPressed, boolean rightPressed) {
        if (paused) return;

        if (!ballStarted) return;

        // REMOVE automatic saveCurrentGame() calls
        if (isGameOver()) {
            // DON'T save game when game over
            deleteSavedGame();
            Game.getGame().changeState(Defs.STATE_GAMEOVER);
            return; // Don't update if game is over
        } else if (isWin()) {
            // DON'T save game when win
            Game.getGame().changeState(Defs.STATE_WIN);
            return;
        }

        // Ball hits left/right walls
        if (ball.getX() <= 0) {
            ball.setX(1); // Avoid getting stuck in wall
            ball.bounceX();
            SoundManager.playWallHitSound();
        }
        if (ball.getX() + ball.getWidth() >= GameConfig.SCREEN_WIDTH - 12) {
            ball.setX(GameConfig.SCREEN_WIDTH - 12 - ball.getWidth() - 1);
            ball.bounceX();
            SoundManager.playWallHitSound();
        }

        // Ball hits top wall
        if (ball.getY() <= 0) {
            ball.setY(1);
            ball.bounceY();
            SoundManager.playWallHitSound();
        }

        // Ball falls below bottom border - GAME OVER
        if (ball.getY() > GameConfig.SCREEN_HEIGHT) {
            lives--;
            if (!isGameOver()) {
                // Reset ball if still have lives (though you have only 1 life)
                resetBall();
            }
        }

        // Collision with paddle
        if (ball.intersects(paddle) && ball.getVy() > 0) {
            ball.collisionFromSides(paddle);
            SoundManager.playWallHitSound();
        }

        // Collision with bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && !brick.isHit() && ball.intersects(brick)) {
                ball.collisionFromSides(brick);
                brick.hit();
                SoundManager.playBrickHitSound();
                break; // Only destroy one brick per collision
            }
        }

        // Update message timer
        if (messageTimer > 0) {
            messageTimer -= deltaTime;
            if (messageTimer <= 0) {
                screenMessage = null; // Hide message when timer expires
            }
        }

        // Update ball
        ball.update(deltaTime);

        // Update paddle
        paddle.update(deltaTime);

        // Update falling bricks
        for (Brick brick : bricks) {
            if (brick instanceof FallingBrick) {
                brick.update(deltaTime);
                if (paddle.intersects(brick)) {
                    lives--;
                }
            }
        }

        // Update items
        Iterator<Item> iter = activeItems.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            item.update(deltaTime);
            if (item.intersects(paddle)) {
                item.applyEffect(paddle, this);
                iter.remove();
            } else if (item.getY() > GameConfig.SCREEN_HEIGHT) {
                iter.remove(); // Remove item when it falls off screen
            }
        }

        // Update score based on destroyed bricks
        int destroyedCount = 0;
        for (Brick brick : bricks) {
            if (brick.isDestroyed()) {
                destroyedCount++;
            }
        }
        this.score = destroyedCount;
    }

    /**
     * Saves the current game state - ONLY called when player actively saves.
     */
    public void saveCurrentGame() {
        List<BrickSave> bricksData = new ArrayList<>();

        // Save state of each brick
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            bricksData.add(new BrickSave(
                    (int)Math.round(brick.getX()),    // x position (rounded to avoid data loss)
                    (int)Math.round(brick.getY()),    // y position (rounded to avoid data loss)
                    (int)Math.round(brick.getWidth()),  // width
                    (int)Math.round(brick.getHeight()), // height
                    brick.isDestroyed(),              // destroyed
                    1,                               // hitPoints (default 1)
                    1,                               // maxHitPoints (default 1)
                    0xFF0000,                        // color (default - red)
                    10                                // points (default points)
            ));
        }

        BallSave ballData = new BallSave(
                ball.getX(), ball.getY(), ball.getVx(), ball.getVy(), 15
        );

        PaddleSave paddleData = new PaddleSave(
                paddle.getX(),
                paddle.getY(),
                (int) paddle.getWidth(),
                (int) paddle.getHeight(),
                paddle.getSpeed()
        );

        GameSave gameSave = new GameSave(
                currentLevel, score, lives, currentLevel,
                ballData, paddleData, bricksData
        );

        SaveManager.saveGame(gameSave);
        System.out.println("Game saved successfully!");
    }

    /**
     * Loads a saved game state.
     *
     * @param gameSave the saved game data to load
     */
    public void loadSavedGame(GameSave gameSave) {
        if (gameSave == null) return;

        currentLevel = gameSave.getDifficulty();
        score = gameSave.getScore();
        lives = gameSave.getLives();
        currentLevel = gameSave.getLevel();

        // Restore ball
        BallSave ballData = gameSave.getBallData();
        ball.setPosition(ballData.getX(), ballData.getY());
        ball.setVelocity(ballData.getVelocityX(), ballData.getVelocityY());

        // Restore paddle
        PaddleSave paddleData = gameSave.getPaddleData();
        paddle.setPosition(paddleData.getX(), paddleData.getY());
        paddle.setWidth(paddleData.getWidth());

        // Restore bricks - need to load level first
        loadLevelForSavedGame();

        // Restore state and position of each brick
        for (BrickSave brickSave : gameSave.getBricks()) {
            // Find brick at corresponding position
            for (Brick brick : bricks) {
                if ((int)brick.getX() == brickSave.getX() &&
                        (int)brick.getY() == brickSave.getY() &&
                        brick.getWidth() == brickSave.getWidth() &&
                        brick.getHeight() == brickSave.getHeight()) {

                    if (brickSave.isDestroyed()) {
                        brick.destroy();
                    }
                    break;
                }
            }
        }

        // Ensure ball starts moving
        ballStarted = false;
        ball.setVelocity(0, 0);
    }

    /**
     * Loads the appropriate level for a saved game.
     */
    private void loadLevelForSavedGame() {
        if (bricks == null || bricks.isEmpty()) {
            bricks = Level.loadLevel(currentLevel);
        }
    }

    /**
     * Deletes the saved game file.
     */
    public void deleteSavedGame() {
        SaveManager.deleteSave();
    }

    /**
     * Resets game to initial state - used when exiting without saving.
     */
    public void resetGame() {
        startGame(Defs.LEVEL_EASY);
    }

    /**
     * Gets the next difficulty level.
     *
     * @return int the next level number
     */
    public int getNextDifficulty() {
        if (currentLevel < Defs.LEVEL_BOSS) {
            currentLevel++;
        }
        return currentLevel;
    }

    /**
     * Resets the ball to starting position.
     */
    private void resetBall() {
        ball = new Ball(GameConfig.SCREEN_WIDTH/2.0, GameConfig.SCREEN_HEIGHT/2.0);
        ballStarted = false;
    }

    /**
     * Resets the paddle to starting position.
     */
    private void resetPaddle() {
        paddle = new Paddle(GameConfig.SCREEN_WIDTH/2.0 - 50, GameConfig.SCREEN_HEIGHT - 50);
    }

    /**
     * Spawns a random item at specified position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    private void spawnRandomItem(double x, double y) {
        //TODO: Item spawn logic (can be added later)
    }

    /**
     * Adds an extra ball to the game.
     */
    public void addExtraBall() {
        //TODO: Extra ball logic (can be added later)
    }

    /**
     * Enables laser power-up.
     */
    public void enableLaser() {
        // laserEnabled = true;
        // Laser shooting logic (can be added later)
    }

    /**
     * Advances to the next level.
     */
    private void nextLevel() {
        currentLevel++;
        // Level transition logic (can be added later)
    }

    /**
     * Checks if player has won the current level.
     *
     * @return boolean true if all bricks are destroyed
     */
    public boolean isWin() {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                return false;
            }
        }
        if (currentLevel < GameConfig.TOTAL_LEVELS) {
            Level.unlockLevel(currentLevel + 1); // Unlock next level if available
        }
        return true;
    }

    // Getters

    /**
     * Gets the ball object.
     */
    public Ball getBall() { return ball; }

    /**
     * Gets the paddle object.
     */
    public Paddle getPaddle() { return paddle; }

    /**
     * Gets the list of bricks.
     */
    public List<Brick> getBricks() { return bricks; }

    /**
     * Gets the current score.
     */
    public int getScore() { return score; }

    /**
     * Gets the current lives.
     */
    public int getLives() { return lives; }

    /**
     * Checks if game is over.
     */
    public boolean isGameOver() { return lives <= 0; }

    /**
     * Sets the number of lives.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Gets the current level number.
     */
    public int getCurrentLevel() { return currentLevel; }
}
