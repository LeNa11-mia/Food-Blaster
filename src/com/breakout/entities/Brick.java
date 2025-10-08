package com.breakout.entities;

import java.awt.*;
import com.breakout.core.GameObject;

/**
 * Brick - can be drawn and destroyed.
 */
public class Brick extends GameObject {
    private boolean destroyed = false;
    
    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    @Override
    public void update(double deltaTime) {
        // Bricks do not move
    }
    
    @Override
    public void draw(Graphics2D g) {
        if (!destroyed) {
            g.setColor(Color.decode("#fecbc2"));
            g.fillRect((int)x, (int)y, (int)width, (int)height);
            g.setColor(Color.BLACK);
            g.drawRect((int)x, (int)y, (int)width, (int)height);
        }
    }
    
    public void hit() {
        destroyed = true;
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
}
