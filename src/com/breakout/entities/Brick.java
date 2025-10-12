package com.breakout.entities;

import java.awt.*;
import com.breakout.core.GameObject;

import javax.swing.*;

/**
 * Brick - can be drawn and destroyed.
 */
public class Brick extends GameObject {
    private boolean destroyed = false;
    
    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);
        sprite = new ImageIcon("assets/brick.png");
    }
    
    @Override
    public void update(double deltaTime) {
        // Bricks do not move
    }
    
    public void hit() {
        destroyed = true;
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
}
