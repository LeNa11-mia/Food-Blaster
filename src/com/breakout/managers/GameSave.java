package com.breakout.managers;

import java.io.Serializable;
import java.util.List;

public class GameSave implements Serializable {
    private static final long serialVersionUID = 1L;

    private String difficulty;
    private int score;
    private int lives;
    private int level;
    private long timestamp;
    private BallSave ballData;
    private PaddleSave paddleData;
    private List<BrickSave> bricksData;

    public GameSave(String difficulty, int score, int lives, int level,
                    BallSave ballData, PaddleSave paddleData, List<BrickSave> bricksData) {
        this.difficulty = difficulty;
        this.score = score;
        this.lives = lives;
        this.level = level;
        this.timestamp = System.currentTimeMillis();
        this.ballData = ballData;
        this.paddleData = paddleData;
        this.bricksData = bricksData;
    }

    // Getters
    public String getDifficulty() { return difficulty; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getLevel() { return level; }
    public long getTimestamp() { return timestamp; }
    public BallSave getBallData() { return ballData; }
    public PaddleSave getPaddleData() { return paddleData; }
    public List<BrickSave> getBricksData() { return bricksData; }
}
