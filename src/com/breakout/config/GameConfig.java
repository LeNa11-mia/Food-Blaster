package com.breakout.config;

import javax.swing.*;

/**
 * Game constants
 */
public class GameConfig {
    // ----- SCREEN -----
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final String WINDOW_TITLE = "Food Blaster";

    // ----- BALL -----
    public static final int BALL_WIDTH = 15;
    public static final int BALL_HEIGHT = 15;
    public static final ImageIcon BALL_IMAGE = new ImageIcon("assets/ball.png");
    public static final double BALL_SPEED = 200;

    // ----- PADDLE -----
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 15;
    public static final ImageIcon PADDLE_IMAGE = new ImageIcon("assets/paddle.png");

    // ----- BRICK -----
    public static final int BRICK_WIDTH = 80;
    public static final int BRICK_HEIGHT = 20;
    public static final ImageIcon NORMAL_BRICK_IMAGE = new ImageIcon("assets/brick.png");

    public static final int EXPLOSION_RADIUS = 1;    //explosion radius by bricks
    public static final ImageIcon EXPLOSIVE_BRICK_IMAGE = new ImageIcon("assets/explosiveBrick.png");

    public static final double GRAVITY = 980; // Bricks fall faster over time
    public static final ImageIcon FALLING_BRICK_IMAGE =  new ImageIcon("assets/rottenCake.png");

    public static final double ITEM_FALLING_SPEED = 150;
    public static final ImageIcon ITEM_BRICK_IMAGE = new ImageIcon("assets/chocolateCake.png");

    // ----- ITEM -----
    public static final int ITEM_WIDTH = 0;
    public static final int ITEM_HEIGHT = 0;
    public static final ImageIcon ITEM_IMAGE = new ImageIcon("assets/gameOverItem.png");
    // Add items ...
}
