package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.entities.Ball;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.items.Item;

import java.awt.*;

/**
 * {@code GameplayPanel} is responsible for rendering the main game screen.
 * <p>
 * It handles drawing all core game entities — including the paddle, ball, bricks,
 * and active items — as well as displaying HUD information such as score and controls.
 * </p>
 */
public class GameplayPanel extends GUIPanel {

    /**
     * Constructs the gameplay panel, initializing its size, background color,
     * and background image.
     */
    public GameplayPanel() {
        setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        setBackground(new Color(255, 214, 214));
        backgroundImage = GameConfig.GAMEPLAY_BACKGROUND;
    }

    /**
     * Draws a single game object on the screen.
     *
     * @param obj  the {@link GameObject} to draw
     * @param g2d  the graphics context
     */
    private void draw(GameObject obj, Graphics2D g2d) {
        if (obj.getSprite() != null && obj.getSprite().getImage() != null) {
            g2d.drawImage(
                    obj.getSprite().getImage(),
                    (int) obj.getX(),
                    (int) obj.getY(),
                    (int) obj.getWidth(),
                    (int) obj.getHeight(),
                    null
            );
        }
    }

    /**
     * Draws the ball if it is visible.
     *
     * @param ball the {@link Ball} to render
     * @param g2d  the graphics context
     */
    private void drawBall(Ball ball, Graphics2D g2d) {
        if (ball.isVisible() && ball.getSprite() != null && ball.getSprite().getImage() != null) {
            g2d.drawImage(
                    ball.getSprite().getImage(),
                    (int) ball.getX(),
                    (int) ball.getY(),
                    (int) ball.getWidth(),
                    (int) ball.getHeight(),
                    null
            );
        }
    }

    /**
     * Draws all game objects: ball, paddle, bricks, and active items.
     *
     * @param g2d the graphics context
     */
    public void drawObjects(Graphics2D g2d) {
        var gm = Game.getGame().getGm();

        drawBall(gm.getBall(), g2d);
        draw(gm.getPaddle(), g2d);

        for (Brick brick : gm.getBricks()) {
            if (!brick.isDestroyed()) {
                draw(brick, g2d);
            }
        }

        for (Item item : gm.getActiveItems()) {
            draw(item, g2d);
        }
    }

    /**
     * Draws player control instructions on the top-left corner.
     *
     * @param g2d the graphics context
     */
    public void drawInstructions(Graphics2D g2d) {
        g2d.setColor(Color.PINK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Press ← → or A D to move", 75, 35);
    }

    /**
     * Draws the player's score in the top-right corner.
     *
     * @param g2d the graphics context
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
     * Paints all visual elements of the gameplay screen:
     * background, game objects, UI text, and item messages.
     *
     * @param g the {@link Graphics} context used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Draw background
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Draw all game objects and UI elements
        drawObjects(g2d);
        drawInstructions(g2d);
        drawScore(g2d);
        drawItemMessage(g2d);
    }

    /**
     * Draws the current on-screen item message (e.g., power-up notifications).
     *
     * @param g2d the graphics context
     */
    private void drawItemMessage(Graphics2D g2d) {
        String message = Game.getGame().getGm().getScreenMessage();

        if (message != null) {
            Font font = new Font("Arial", Font.PLAIN, 12);
            g2d.setFont(font);

            FontMetrics fm = g2d.getFontMetrics();
            int x = (GameConfig.SCREEN_WIDTH - fm.stringWidth(message)) / 2;
            int y = 74;

            g2d.setColor(Color.BLACK);
            g2d.drawString(message, x, y);
        }
    }
}
