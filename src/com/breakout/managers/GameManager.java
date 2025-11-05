package com.breakout.managers;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.entities.*;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.bricks.FallingBrick;
import com.breakout.entities.bricks.UnbreakableBrick;
import com.breakout.entities.items.Item;
import com.breakout.saves.BallSave;
import com.breakout.saves.BrickSave;
import com.breakout.saves.GameSave;
import com.breakout.saves.PaddleSave;

import java.util.*;
import java.util.List;

/**
 * {@code GameManager} is responsible for managing the overall state of the game,
 * including game objects, scores, lives, level progression, and save/load operations.
 */
public class GameManager {
    private String screenMessage = null;
    private double messageTimer = 0.0;

    private final Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private final List<Item> activeItems;

    private int score;
    private int lives;
    private int currentLevel;

    private boolean paused = false;
    private boolean ballStarted = false;

    /**
     * Constructs the GameManager and initializes the core game objects.
     */
    public GameManager() {
        Level.unlockLevel(1); // Unlock the first level

        // Initialize game objects (using 'final' for ball and activeItems as they are the same instance)
        this.ball = new Ball(GameConfig.SCREEN_WIDTH / 2.0, GameConfig.SCREEN_HEIGHT / 2.0);
        this.paddle = new Paddle(GameConfig.SCREEN_WIDTH / 2.0 - 50, GameConfig.SCREEN_HEIGHT - 50);
        this.bricks = new ArrayList<>();
        this.activeItems = new ArrayList<>();
        this.lives = 1; // Only 1 life as per your requirement
    }

    /**
     * Displays a temporary message on the screen.
     *
     * @param message The message string to display.
     */
    public void showMessageOnScreen(final String message) {
        this.screenMessage = message;
        this.messageTimer = GameConfig.ITEM_MESSAGE_DURATION; // Start the timer
    }

    /**
     * Gets the current screen message being displayed.
     *
     * @return The message string, or {@code null} if no message is active.
     */
    public String getScreenMessage() {
        return this.screenMessage;
    }

    /**
     * Adds a new item to the list of active items.
     *
     * @param item The {@link Item} to add.
     */
    public void addItem(final Item item) {
        this.activeItems.add(item);
    }

    /**
     * Gets the list of currently active items.
     *
     * @return A list of {@link Item} objects.
     */
    public List<Item> getActiveItems() {
        return this.activeItems;
    }

    /**
     * Checks if the ball has been launched (started).
     *
     * @return {@code true} if the ball is moving, {@code false} otherwise.
     */
    public boolean hasBallStarted() {
        return this.ballStarted;
    }

    /**
     * Starts the ball movement.
     */
    public void startBall() {
        this.ballStarted = true;
    }

    /**
     * Starts a new game at the specified level.
     *
     * @param level The level number to start.
     */
    public void startGame(final int level) {
        this.currentLevel = level;

        // ADDED: Pass ball parameter for InvisibleBallBrick support
        this.bricks = Level.loadLevel(level, this.ball);
        resetBall();
        resetPaddle();
        this.lives = 1;
        this.score = 0;
        this.activeItems.clear();
        this.screenMessage = null;
        this.ballStarted = false;
    }

    /**
     * Continues the game from a saved file.
     */
    public void continueGame() {
        final GameSave savedGame = SaveManager.loadGame();
        if (savedGame != null) {
            loadSavedGame(savedGame);
            this.ballStarted = false; // Do not move immediately
            this.ball.setVelocity(savedGame.getBallData().getVelocityX(), savedGame.getBallData().getVelocityY());
        }
    }

    /**
     * Checks if a saved game exists.
     *
     * @return {@code true} if a saved game file is present.
     */
    public boolean canContinueGame() {
        return SaveManager.saveExists();
    }

    /**
     * Retrieves information about the saved game for display.
     *
     * @return A string containing saved game details or "No saved game".
     */
    public String getSaveInfo() {
        if (!canContinueGame()) {
            return "No saved game";
        }

        final GameSave savedGame = SaveManager.loadGame();
        if (savedGame != null) {
            return String.format("Level: %d - Score: %d - Lives: %d",
                    savedGame.getLevel(), savedGame.getScore(), savedGame.getLives());
        }
        return "Saved game";
    }

    /**
     * Updates the game state, handles input, movement, and collisions.
     *
     * @param deltaTime Time elapsed since the last update.
     * @param leftPressed {@code true} if the left movement key is pressed.
     * @param rightPressed {@code true} if the right movement key is pressed.
     */
    public void update(final double deltaTime, final boolean leftPressed, final boolean rightPressed) {
        if (this.paused) return;

        if (!this.ballStarted) return;

        // Check game end conditions
        if (isGameOver()) {
            // DO NOT save game on game over
            deleteSavedGame();
            Game.getGame().changeState(Defs.STATE_GAMEOVER);
            return; // Don't update if game is over
        } else if (isWin()) {
            // DO NOT save game on win
            Game.getGame().changeState(Defs.STATE_WIN);
            return;
        }

        // --- Collision and Boundary Checks ---

        // Ball hits left/right walls
        if (this.ball.getX() <= 0) {
            this.ball.setX(1); // Avoid getting stuck in the wall
            this.ball.bounceX();
            SoundManager.playWallHitSound();
        }
        if (this.ball.getX() + this.ball.getWidth() >= GameConfig.SCREEN_WIDTH - 12) {
            this.ball.setX(GameConfig.SCREEN_WIDTH - 12 - this.ball.getWidth() - 1);
            this.ball.bounceX();
            SoundManager.playWallHitSound();
        }

        // Ball hits top wall
        if (this.ball.getY() <= 0) {
            this.ball.setY(1);
            this.ball.bounceY();
            SoundManager.playWallHitSound();
        }

        // Ball falls below bottom border - GAME OVER
        if (this.ball.getY() > GameConfig.SCREEN_HEIGHT) {
            this.lives--;
            if (!isGameOver()) {
                // Reset ball if still have lives (though you have only 1 life)
                resetBall();
            }
        }

        // Collision with paddle
        if (this.ball.intersects(this.paddle) && this.ball.getVy() > 0) {
            this.ball.collisionFromSides(this.paddle);
            SoundManager.playWallHitSound();
        }

        // Collision with bricks
        for (final Brick brick : this.bricks) {
            if (!brick.isDestroyed() && !brick.isHit() && this.ball.intersects(brick)) {
                this.ball.collisionFromSides(brick);
                brick.hit();
                SoundManager.playBrickHitSound();
                break; // Only destroy one brick per collision
            }
        }

        // --- Timer Updates ---

        if (this.messageTimer > 0) {
            this.messageTimer -= deltaTime;
            if (this.messageTimer <= 0) {
                this.screenMessage = null; // Hide the message when timer runs out
            }
        }

        // --- Object Updates ---

        this.ball.update(deltaTime);
        this.paddle.update(deltaTime);

        // Update bricks (specific behavior for FallingBrick)
        for (final Brick brick : this.bricks) {
            if (brick instanceof FallingBrick) {
                brick.update(deltaTime);
                // Check if falling brick hits the paddle
                if (this.paddle.intersects(brick)) {
                    this.lives--;
                }
            }
        }

        // Update items (move and check collision with paddle)
        final Iterator<Item> iter = this.activeItems.iterator();
        while (iter.hasNext()) {
            final Item item = iter.next();
            item.update(deltaTime);
            if (item.intersects(this.paddle)) {
                item.applyEffect(this.paddle, this);
                iter.remove();
            } else if (item.getY() > GameConfig.SCREEN_HEIGHT) {
                iter.remove(); // Remove item when it falls off screen
            }
        }

        // Update score (based on destroyed bricks)
        int destroyedCount = 0;
        for (final Brick brick : this.bricks) {
            if (brick.isDestroyed()) {
                destroyedCount++;
            }
        }
        this.score = destroyedCount;
    }

    /**
     * Saves the current game state - ONLY called when the player manually saves.
     */
    public void saveCurrentGame() {
        final List<BrickSave> bricksData = new ArrayList<>();

        // Save the state of each brick
        for (final Brick brick : this.bricks) {
            bricksData.add(new BrickSave(
                    (int) Math.round(brick.getX()),      // x position (rounded to avoid data loss)
                    (int) Math.round(brick.getY()),      // y position (rounded to avoid data loss)
                    (int) Math.round(brick.getWidth()),  // width
                    (int) Math.round(brick.getHeight()), // height
                    brick.isDestroyed(),                 // destroyed
                    1,                                   // hitPoints (defaulted)
                    1,                                   // maxHitPoints (defaulted)
                    0xFF0000,                            // color (defaulted - red)
                    10                                   // points (defaulted)
            ));
        }

        final BallSave ballData = new BallSave(
                this.ball.getX(), this.ball.getY(), this.ball.getVx(), this.ball.getVy(), 15
        );

        final PaddleSave paddleData = new PaddleSave(
                this.paddle.getX(),
                this.paddle.getY(),
                (int) this.paddle.getWidth(),
                (int) this.paddle.getHeight(),
                this.paddle.getSpeed()
        );

        final GameSave gameSave = new GameSave(
                this.currentLevel, this.score, this.lives, this.currentLevel,
                ballData, paddleData, bricksData
        );

        SaveManager.saveGame(gameSave);

        System.out.println("Game saved successfully!");
    }

    /**
     * Loads a previously saved game state.
     *
     * @param gameSave The {@link GameSave} object containing the saved data.
     */
    public void loadSavedGame(final GameSave gameSave) {
        if (gameSave == null) return;

        this.currentLevel = gameSave.getDifficulty(); // Assuming difficulty is level, though there is redundancy here
        this.score = gameSave.getScore();
        this.lives = gameSave.getLives();
        this.currentLevel = gameSave.getLevel();

        // Restore ball
        final BallSave ballData = gameSave.getBallData();
        this.ball.setPosition(ballData.getX(), ballData.getY());
        this.ball.setVelocity(ballData.getVelocityX(), ballData.getVelocityY());

        // Restore paddle
        final PaddleSave paddleData = gameSave.getPaddleData();
        this.paddle.setPosition(paddleData.getX(), paddleData.getY());
        this.paddle.setWidth(paddleData.getWidth());

        // Restore bricks - must load the level first
        loadLevelForSavedGame();

        // Restore state and position of each brick
        for (final BrickSave brickSave : gameSave.getBricks()) {
            // Find the corresponding brick based on position and size
            for (final Brick brick : this.bricks) {
                if ((int) brick.getX() == brickSave.getX() &&
                        (int) brick.getY() == brickSave.getY() &&
                        brick.getWidth() == brickSave.getWidth() &&
                        brick.getHeight() == brickSave.getHeight()) {

                    if (brickSave.isDestroyed()) {
                        brick.destroy();
                    }
                    break;
                }
            }
        }

        // Ensure ball is not moving immediately after loading
        this.ballStarted = false;
        this.ball.setVelocity(0, 0);
    }

    /**
     * Loads the appropriate level layout for a saved game.
     */
    private void loadLevelForSavedGame() {
        if (this.bricks == null || this.bricks.isEmpty()) {
            // ADDED: Pass ball parameter for InvisibleBallBrick support
            this.bricks = Level.loadLevel(this.currentLevel, this.ball);
        }
    }

    /**
     * Deletes the currently saved game file.
     */
    public void deleteSavedGame() {
        SaveManager.deleteSave();
    }

    /**
     * Gets the next level number.
     *
     * @return The next level number, or the current level if it's the maximum.
     */
    public int getNextDifficulty() {
        if (this.currentLevel < GameConfig.TOTAL_LEVELS) {
            this.currentLevel++;
        }
        return this.currentLevel;
    }

    /**
     * Resets the ball to its starting position and sets velocity to 0 (unstarted state).
     */
    private void resetBall() {
        // CHANGED: Maintain same ball instance for InvisibleBallBrick compatibility
        this.ball.setPosition(GameConfig.SCREEN_WIDTH / 2.0, GameConfig.SCREEN_HEIGHT / 2.0);
        this.ball.setVelocity(0, GameConfig.BALL_SPEED);
        this.ball.setVisible(true); // Ensure ball is visible after reset
        this.ballStarted = false;
    }

    /**
     * Resets the paddle to its original position and default size/speed.
     */
    private void resetPaddle() {
        this.paddle = new Paddle(GameConfig.SCREEN_WIDTH / 2.0 - 50, GameConfig.SCREEN_HEIGHT - 50);
    }

    /**
     * Checks if the player has won the current level (all breakable bricks destroyed).
     *
     * @return {@code true} if the level is won, {@code false} otherwise.
     */
    public boolean isWin() {
        for (final Brick brick : this.bricks) {
            if (!(brick instanceof UnbreakableBrick) && !brick.isDestroyed()) {
                return false;
            }
        }
        if (this.currentLevel < GameConfig.TOTAL_LEVELS) {
            Level.unlockLevel(this.currentLevel + 1); // Unlock the next level if available
        }
        return true;
    }

    // --- Getters and Setters ---

    public Ball getBall() {
        return this.ball;
    }

    public Paddle getPaddle() {
        return this.paddle;
    }

    public List<Brick> getBricks() {
        return this.bricks;
    }

    public int getScore() {
        return this.score;
    }

    /**
     * Checks if the game is over (lives <= 0).
     *
     * @return {@code true} if game is over.
     */
    public boolean isGameOver() {
        return this.lives <= 0;
    }

    /**
     * Sets the number of lives remaining.
     *
     * @param lives The new number of lives.
     */
    public void setLives(final int lives) {
        this.lives = lives;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }
}