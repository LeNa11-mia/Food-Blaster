package com.breakout.managers;

import java.io.Serializable;

public class BrickSave implements Serializable {
    private int index;
    private int row, col;
    private int health;
    private boolean destroyed;
    private String type;

    public BrickSave(int index, int row, int col, int health, boolean destroyed, String type) {
        this.index = index;
        this.row = row;
        this.col = col;
        this.health = health;
        this.destroyed = destroyed;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }
}
