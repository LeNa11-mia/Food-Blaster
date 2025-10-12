package com.breakout.managers;

import com.breakout.Main;
import com.breakout.core.GameObject;
import com.breakout.entities.*;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    private GameManager gm;

    public Renderer(GameManager gm) {
        this.gm = gm;
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        setBackground(Color.BLACK);
    }

    private void draw(GameObject obj, Graphics2D g2d) {
        g2d.drawImage(obj.getSprite().getImage(), (int) obj.getX(), (int) obj.getY(), null);
    }

    public void drawObjects(Graphics2D g2d) {
        draw(gm.getBall(), g2d);
        draw(gm.getPaddle(), g2d);
        for (Brick brick : gm.getBricks()) {
            if (!brick.isDestroyed()) {
                draw(brick, g2d);
            }
        }
    }

    public void drawInstructions(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.drawString("Press ← → or A D to move", 10, 20);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all objects
        drawObjects(g2d);

        // Display instructions
        drawInstructions(g2d);
    }
}