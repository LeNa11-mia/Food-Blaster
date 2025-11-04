package com.breakout.saves;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class GameSave implements Serializable {
    private static final long serialVersionUID = 1L;

    private int difficulty;
    private int level;
    private int score;
    private int lives;

    private BallSave ballData;
    private PaddleSave paddleData;
    private List<BrickSave> bricks;

    private Date saveDate;

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

    // ✅ Getters and Setters
    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }

    public BallSave getBallData() { return ballData; }
    public void setBallData(BallSave ballData) { this.ballData = ballData; }

    public PaddleSave getPaddleData() { return paddleData; }
    public void setPaddleData(PaddleSave paddleData) { this.paddleData = paddleData; }

    public List<BrickSave> getBricks() { return bricks; }
    public void setBricks(List<BrickSave> bricks) { this.bricks = bricks; }

    public Date getSaveDate() { return saveDate; }
    public void setSaveDate(Date saveDate) { this.saveDate = saveDate; }
}
