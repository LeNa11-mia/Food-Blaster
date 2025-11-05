package com.breakout.saves;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * {@code GameSave} is the main data class that holds the complete snapshot of the game
 * state, including core statistics and the serialized state of all primary game objects.
 * <p>
 * This class implements {@link Serializable} to allow the entire game state to be
 * persisted to and restored from a file.
 * </p>
 */
public class GameSave implements Serializable {
    /** The serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    private int difficulty;
    private int level;
    private int score;
    private int lives;

    private BallSave ballData;
    private PaddleSave paddleData;
    private List<BrickSave> bricks;

    private Date saveDate;

    /**
     * Default constructor. Initializes all fields with default values, typically used
     * before loading data during deserialization.
     */
    public GameSave() {
        this.difficulty = 1;
        this.level = 1;
        this.score = 0;
        this.lives = 3;
        this.ballData = new BallSave();
        this.paddleData = new PaddleSave();
        this.bricks = new ArrayList<>();
        this.saveDate = new Date();
    }

    /**
     * Parameterized constructor used to create a save state from the current game data.
     *
     * @param difficulty The current difficulty setting of the game.
     * @param score The current player score.
     * @param lives The number of lives remaining.
     * @param level The current level number.
     * @param ballSave The serialized data for the ball's state.
     * @param paddleSave The serialized data for the paddle's state.
     * @param bricks The list of serialized data for all bricks.
     */
    public GameSave(final int difficulty, final int score, final int lives, final int level,
                    final BallSave ballSave, final PaddleSave paddleSave,
                    final List<BrickSave> bricks) {
        this.difficulty = difficulty;
        this.score = score;
        this.lives = lives;
        this.level = level;
        this.ballData = ballSave;
        this.paddleData = paddleSave;
        this.bricks = bricks;
        this.saveDate = new Date();
    }

    // --- Getters and Setters ---

    /**
     * Gets the current difficulty setting.
     * @return The difficulty level.
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Gets the current level number.
     * @return The level number.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Sets the current level number.
     * @param level The new level number.
     */
    public void setLevel(final int level) {
        this.level = level;
    }

    /**
     * Gets the current score.
     * @return The score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the number of lives remaining.
     * @return The number of lives.
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Gets the serialized ball data.
     * @return The {@link BallSave} object.
     */
    public BallSave getBallData() {
        return this.ballData;
    }

    /**
     * Gets the serialized paddle data.
     * @return The {@link PaddleSave} object.
     */
    public PaddleSave getPaddleData() {
        return this.paddleData;
    }

    /**
     * Gets the list of serialized brick data.
     * @return The list of {@link BrickSave} objects.
     */
    public List<BrickSave> getBricks() {
        return this.bricks;
    }

    /**
     * Sets the list of serialized brick data.
     * @param bricks The new list of {@link BrickSave} objects.
     */
    public void setBricks(final List<BrickSave> bricks) {
        this.bricks = bricks;
    }

    /**
     * Sets the timestamp of when the game was saved.
     * @param saveDate The new {@link Date} object.
     */
    public void setSaveDate(final Date saveDate) {
        this.saveDate = saveDate;
    }
}