package com.breakout.interfaces;

public interface Destructible {
    void hit();         // What happen when object is hit
    void onDestroyed(); // Effect when destroyed
    boolean isDestroyed();
}