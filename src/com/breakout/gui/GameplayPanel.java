package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.entities.bricks.Brick;

import java.awt.*;

public class GameplayPanel extends GUIPanel {

    public GameplayPanel() {
        setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        setBackground(new Color(255, 214, 214));
    }

    private void draw(GameObject obj, Graphics2D g2d) {
        g2d.drawImage(obj.getSprite().getImage(), (int) obj.getX(), (int) obj.getY(), null);
    }

    public void drawObjects(Graphics2D g2d) {
        draw(Game.getGame().getGm().getBall(), g2d);
        draw(Game.getGame().getGm().getPaddle(), g2d);
        for (Brick brick : Game.getGame().getGm().getBricks()) {
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
