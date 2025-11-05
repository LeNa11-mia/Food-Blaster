package com.breakout.saves;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Serializable data class representing the complete game state for saving and loading.
 * Contains all necessary information to restore a game session including player progress,
 * object states, and game configuration.
 *
 * <p>This class serves as the main container for game persistence, storing the state
 * of all major game entities and player statistics.</p>
 *
 * @author Breakout Team
 * @version 1.0
 */
public class GameSave implements Serializable {
    /** Serialization version UID for compatibility */
    private static final long serialVersionUID = 1L;

    /** Game difficulty setting */
    private int difficulty;

    /** Current level number */
    private int level;

    /** Player's current score */
    private int score;

    /** Number of remaining lives */
    private int lives;

    /** Saved state of the ball object */
    private BallSave ballData;

    /** Saved state of the paddle object */
    private PaddleSave paddleData;

    /** List of saved brick states */
    private List<BrickSave> bricks;

    /** Timestamp when the game was saved */
    private Date saveDate;

    /**
     * Default constructor for serialization.
     * Creates a new game save with default initial values.
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
     * Constructs a game save object with specified game state.
     *
     * @param difficulty the game difficulty setting
     * @param score the player's current score
     * @param lives the number of remaining lives
     * @param level the current level number
     * @param ballSave the saved ball state
     * @param paddleSave the saved paddle state
     * @param bricks the list of saved brick states
     */
    public GameSave(int difficulty, int score, int lives, int level,
                    BallSave ballSave, PaddleSave paddleSave, List<BrickSave> bricks) {
        this.difficulty = difficulty;
        this.score = score;
        this.lives = lives;
        this.level = level;
        this.ballData = ballSave;
        this.paddleData = paddleSave;
        this.bricks = bricks;
        this.saveDate = new Date();
    }

    // ----------- Getters and Setters -----------

    /**
     * Gets the game difficulty setting.
     *
     * @return int the difficulty level
     */
    public int getDifficulty() { return difficulty; }

    /**
     * Sets the game difficulty setting.
     *
     * @param difficulty the new difficulty level
     */
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    /**
     * Gets the current level number.
     *
     * @return int the current level
     */
    public int getLevel() { return level; }

    /**
     * Sets the current level number.
     *
     * @param level the new level number
     */
    public void setLevel(int level) { this.level = level; }

    /**
     * Gets the player's current score.
     *
     * @return int the current score
     */
    public int getScore() { return score; }

    /**
     * Sets the player's current score.
     *
     * @param score the new score value
     */
    public void setScore(int score) { this.score = score; }

    /**
     * Gets the number of remaining lives.
     *
     * @return int the number of lives
     */
    public int getLives() { return lives; }

    /**
     * Sets the number of remaining lives.
     *
     * @param lives the new number of lives
     */
    public void setLives(int lives) { this.lives = lives; }

    /**
     * Gets the saved ball state data.
     *
     * @return BallSave the ball state information
     */
    public BallSave getBallData() { return ballData; }

    /**
     * Sets the saved ball state data.
     *
     * @param ballData the new ball state information
     */
    public void setBallData(BallSave ballData) { this.ballData = ballData; }

    /**
     * Gets the saved paddle state data.
     *
     * @return PaddleSave the paddle state information
     */
    public PaddleSave getPaddleData() { return paddleData; }

    /**
     * Sets the saved paddle state data.
     *
     * @param paddleData the new paddle state information
     */
    public void setPaddleData(PaddleSave paddleData) { this.paddleData = paddleData; }

    /**
     * Gets the list of saved brick states.
     *
     * @return List<BrickSave> the list of brick states
     */
    public List<BrickSave> getBricks() { return bricks; }

    /**
     * Sets the list of saved brick states.
     *
     * @param bricks the new list of brick states
     */
    public void setBricks(List<BrickSave> bricks) { this.bricks = bricks; }

    /**
     * Gets the timestamp when the game was saved.
     *
     * @return Date the save timestamp
     */
    public Date getSaveDate() { return saveDate; }

    /**
     * Sets the timestamp when the game was saved.
     *
     * @param saveDate the new save timestamp
     */
    public void setSaveDate(Date saveDate) { this.saveDate = saveDate; }
}
