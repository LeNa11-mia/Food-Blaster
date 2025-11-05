package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.items.Item;

import java.awt.*;

/**
 * Main gameplay panel that renders the game objects and UI during gameplay.
 * Handles drawing of balls, paddles, bricks, items, and game information.
 * Manages the visual representation of the game state and player feedback.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class GameplayPanel extends GUIPanel {

    /**
     * Constructor initializes the gameplay panel with appropriate dimensions and background.
     * Sets up the visual environment for the main game action.
     */
    public GameplayPanel() {
        setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        setBackground(new Color(255, 214, 214));
        backgroundImage = GameConfig.GAMEPLAY_BACKGROUND;
    }

    /**
     * Draws a game object using its sprite image at its current position.
     * Renders the object's visual representation on the game canvas.
     *
     * @param obj the game object to draw
     * @param g2d the Graphics2D context for drawing
     */
    private void draw(GameObject obj, Graphics2D g2d) {
        if (obj.getSprite() != null && obj.getSprite().getImage() != null) {
            g2d.drawImage(obj.getSprite().getImage(),
                    (int) obj.getX(),
                    (int) obj.getY(),
                    (int) obj.getWidth(),
                    (int) obj.getHeight(),
                    null);
        }
    }

    /**
     * Draws all active game objects including ball, paddle, bricks, and items.
     * Only renders non-destroyed bricks and currently active items.
     *
     * @param g2d the Graphics2D context for drawing
     */
    public void drawObjects(Graphics2D g2d) {
        var gm = Game.getGame().getGm();

        // Draw ball
        draw(gm.getBall(), g2d);

        // Draw paddle
        draw(gm.getPaddle(), g2d);

        // Draw bricks (only non-destroyed ones)
        for (Brick brick : gm.getBricks()) {
            if (!brick.isDestroyed()) {
                draw(brick, g2d);
            }
        }

        // Draw active items
        for (Item item : gm.getActiveItems()) {
            draw(item, g2d);
        }
    }

    /**
     * Displays the control instructions at the top left of the screen.
     * Shows players how to control the paddle using keyboard inputs.
     *
     * @param g2d the Graphics2D context for drawing
     */
    public void drawInstructions(Graphics2D g2d) {
        g2d.setColor(Color.PINK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Press ← → or A D to move", 75, 35);
    }

    /**
     * Draws the current player score at the top right corner of the screen.
     * Displays the score in white text with proper positioning and formatting.
     *
     * @param g2d the Graphics2D context for drawing
     */
    public void drawScore(Graphics2D g2d) {
        var gm = Game.getGame().getGm();

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 22));

        String scoreText = "Score: " + gm.getScore();
        int textWidth = g2d.getFontMetrics().stringWidth(scoreText);

        g2d.drawString(scoreText, GameConfig.SCREEN_WIDTH - textWidth - 50, 35);
    }

    /**
     * Main painting method that renders the entire gameplay scene.
     * Draws background, game objects, UI elements, and item messages.
     * Applies anti-aliasing for smoother graphics.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw full-screen background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Draw all game objects
        drawObjects(g2d);

        // Display UI text elements
        drawInstructions(g2d);
        drawScore(g2d);

        // Draw item activation messages
        drawItemMessage(g2d);
    }

    /**
     * Displays item activation messages at the top center of the screen.
     * Shows temporary messages when special items are collected or activated.
     *
     * @param g2d the Graphics2D context for drawing
     */
    private void drawItemMessage(Graphics2D g2d) {
        String message = Game.getGame().getGm().getScreenMessage();

        if (message != null) {
            // Configure font and color
            Font font = new Font("Arial", Font.PLAIN, 12);
            g2d.setFont(font);

            // Calculate position to center the message
            FontMetrics fm = g2d.getFontMetrics();
            int x = (GameConfig.SCREEN_WIDTH - fm.stringWidth(message)) / 2;
            int y = 25;

            // Optional: Draw shadow/border effect
            // g2d.setColor(new Color(0, 0, 0, 150)); // Shadow
            // g2d.drawString(message, x + 2, y + 2);

            // Draw main message
            g2d.setColor(Color.BLACK);
            g2d.drawString(message, x, y);
        }
    }
}
