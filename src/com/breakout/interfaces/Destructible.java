package com.breakout.interfaces;

/**
 * Represents an object in the game that can be destroyed.
 * <p>
 * Classes implementing this interface should define what happens
 * when the object is destroyed
 * </p>
 */
public interface Destructible {

    /**
     * Called when the object is destroyed.
     * <p>
     * Implementations can define custom behavior.
     * </p>
     */
    void onDestroyed(); // Effect when destroyed
}