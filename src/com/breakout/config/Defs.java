package com.breakout.config;

/**
 * Game definitions
 */
public class Defs {
    // ----- GAME STATES -----
    public static final int STATE_LOADING = 0; // Game isn't started
    public static final int STATE_MENU = 1;
    public static final int STATE_PLAYING = 2;
    public static final int STATE_WIN = 3;
    public static final int STATE_GAMEOVER = 4;

    // ----- LEVELS -----
    public static final int LEVEL_EASY = 1;
    public static final int LEVEL_MEDIUM = 2;
    public static final int LEVEL_HARD = 3;
    public static final int LEVEL_BOSS = 4;

    // ----- BRICK TYPES -----
    public static final int NORMAL_BRICK = 1;
    public static final int EXPLOSIVE_BRICK = 2;
    public static final int FALLING_BRICK = 3;

    // ----- ITEM TYPES -----
}
