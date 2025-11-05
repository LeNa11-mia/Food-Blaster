package com.breakout.config;

import com.breakout.managers.SoundManager;
import com.breakout.utils.ImageUtils;

import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * Game constants
 */
public class GameConfig {

    public static final int TOTAL_LEVELS = 6;

    // ----- SCREEN -----
    public static final int SCREEN_WIDTH = 625;
    public static final int SCREEN_HEIGHT = 700;
    public static final String WINDOW_TITLE = "Food Blaster";

    // ----- BACKGROUND IMAGES -----
    public static final ImageIcon MENU_BACKGROUND = new ImageIcon("assets/images/mainMenu.png");
    public static final ImageIcon GAMEMODES_BACKGROUND = new ImageIcon("assets/images/select.png");
    public static final ImageIcon GAMEPLAY_BACKGROUND = new ImageIcon("assets/images/gamePlay.png");
    public static final ImageIcon WIN_BACKGROUND = new ImageIcon("assets/images/win.png");
    public static final ImageIcon GAMEOVER_BACKGROUND = new ImageIcon("assets/images/gameOver.png");
    public static final ImageIcon SETTING_BACKGROUND = new ImageIcon("assets/images/setting.png");

    // ----- BALL -----
    public static final ImageIcon BALL_IMAGE = new ImageIcon("assets/images/ball.png");
    public static final double BALL_WIDTH = BALL_IMAGE.getIconWidth();
    public static final double BALL_HEIGHT = BALL_IMAGE.getIconHeight();
    public static final double BALL_SPEED = 300;

    // ----- PADDLE -----
    public static final double PADDLE_SPEED = 400;
    public static final double VELOCITY_TRANSFER_TO_BALL = 0.2; // Phần trăm của vận tốc truyền cho bóng khi va chạm
    public static final ImageIcon PADDLE_IMAGE = new ImageIcon("assets/images/paddle.png");
    public static final double PADDLE_WIDTH = PADDLE_IMAGE.getIconWidth();
    public static final double PADDLE_HEIGHT = PADDLE_IMAGE.getIconHeight();

    // ----- BRICK -----
    public static final ImageIcon NORMAL_BRICK_IMAGE = new ImageIcon("assets/images/normal-brick.png");
    public static final double BRICK_WIDTH = NORMAL_BRICK_IMAGE.getIconWidth();
    public static final double BRICK_HEIGHT = NORMAL_BRICK_IMAGE.getIconHeight();

    public static final int EXPLOSION_RADIUS = 1;    //explosion radius by bricks
    public static final ImageIcon EXPLOSIVE_BRICK_IMAGE = new ImageIcon("assets/images/explosive-brick.png");

    public static final double GRAVITY = 980; // Bricks fall faster over time
    public static final ImageIcon FALLING_BRICK_IMAGE = new ImageIcon("assets/images/falling-brick.png");

    public static final double ITEM_FALLING_SPEED = 170;
    public static final ImageIcon ITEM_BRICK_IMAGE = NORMAL_BRICK_IMAGE; // Ẩn vị trí Item Brick

    public static final ImageIcon UNBREAKABLE_BRICK_IMAGE = new ImageIcon("assets/images/unbreakable-brick.png");

    public static final ImageIcon INVISIBLE_BALL_BRICK_IMAGE = new ImageIcon("assets/images/invisibleBallBrick.png");

    // ----- ITEM -----
    public static final ImageIcon ITEM_IMAGE = new ImageIcon("assets/images/gameOverItem.png");
    public static final double ITEM_WIDTH = ITEM_IMAGE.getIconWidth();
    public static final double ITEM_HEIGHT = ITEM_IMAGE.getIconHeight();
    public static final double ITEM_MESSAGE_DURATION = 2.0;


    // ----- SOUND EFFECTS PATH -----
    public static final String BRICK_HIT_SOUND_PATH = "assets/sounds/ball-hit-brick.wav";
    public static final String WALL_HIT_SOUND_PATH = "assets/sounds/ball-hit-wall.wav";
}
