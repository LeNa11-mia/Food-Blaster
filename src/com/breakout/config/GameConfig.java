package com.breakout.config;

import com.breakout.managers.SoundManager;

import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * Game constants
 */
public class GameConfig {
    // ----- SCREEN -----
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 700;
    public static final String WINDOW_TITLE = "Food Blaster";

    // ----- BALL -----
    public static final double BALL_WIDTH = 15;
    public static final double BALL_HEIGHT = 15;
    public static final ImageIcon BALL_IMAGE = new ImageIcon("src/com/breakout/resources.assets/ball.png");
    public static final double BALL_SPEED = 300;

    // ----- PADDLE -----
    public static final double PADDLE_WIDTH = 100;
    public static final double PADDLE_HEIGHT = 15;
    public static final double PADDLE_SPEED = 400;
    public static final double VELOCITY_TRANSFER_TO_BALL = 0.2; // Phần trăm của vận tốc truyền cho bóng khi va chạm
    public static final ImageIcon PADDLE_IMAGE = new ImageIcon("src/com/breakout/resources.assets/paddle.png");

    // ----- BRICK -----
    public static final double BRICK_WIDTH = 80;
    public static final double BRICK_HEIGHT = 20;
    public static final ImageIcon NORMAL_BRICK_IMAGE = new ImageIcon("src/com/breakout/resources.assets/brick.png");

    public static final int EXPLOSION_RADIUS = 1;    //explosion radius by bricks
    public static final ImageIcon EXPLOSIVE_BRICK_IMAGE = new ImageIcon("src/com/breakout/resources.assets/explosiveBrick.png");

    public static final double GRAVITY = 980; // Bricks fall faster over time
    public static final ImageIcon FALLING_BRICK_IMAGE =  new ImageIcon("src/com/breakout/resources.assets/rottenCake.png");

    public static final double ITEM_FALLING_SPEED = 170;
    public static final ImageIcon ITEM_BRICK_IMAGE = new ImageIcon("src/com/breakout/resources.assets/chocolateCake.png");

    public static final ImageIcon UNBREAKABLE_BRICK_IMAGE = null;

    // ----- ITEM -----
    public static final double ITEM_WIDTH = 60;
    public static final double ITEM_HEIGHT = 60;
    public static final ImageIcon ITEM_IMAGE = new ImageIcon("src/com/breakout/resources.assets/gameOverItem.png");

    // ----- SOUND EFFECTS PATH -----
    public static final String BRICK_HIT_SOUND_PATH = "src/com/breakout/resources.assets/ball-hit-brick.wav";
    public static final String WALL_HIT_SOUND_PATH = "src/com/breakout/resources.assets/ball-hit-wall.wav";
}
