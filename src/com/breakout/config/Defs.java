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
    public static final int STATE_SETTING = 5;
    public static final int STATE_GAME_MODES = 6;

    // ----- LEVELS -----
    public static final int LEVEL_EASY = 1;
    public static final int LEVEL_MEDIUM = 2;
    public static final int LEVEL_HARD = 3;
    public static final int LEVEL_BOSS = 4;

    // ----- UI -----
    public static final int GO_BACK = -1;

    // ----- BRICK TYPES -----
    public static final int NORMAL_BRICK = 1;
    public static final int EXPLOSIVE_BRICK = 2;
    public static final int FALLING_BRICK = 3;
    public static final int ITEM_BRICK = 4;
    public static final int UNBREAKABLE_BRICK = 5;

    // ----- ITEM TYPES -----
}
