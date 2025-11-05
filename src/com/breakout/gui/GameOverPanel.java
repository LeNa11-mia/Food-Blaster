package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;

import javax.swing.*;
import java.awt.*;

/**
 * {@code GameOverPanel} represents the screen displayed when the player loses the game.
 * <p>
 * This panel shows the player's final score, the level reached, and provides
 * two main options:
 * <ul>
 *   <li><b>RESTART</b> — restart the current level.</li>
 *   <li><b>MAIN MENU</b> — return to the main menu.</li>
 * </ul>
 * It also renders a handwritten-style "Game Over" title.
 * </p>
 */
public class GameOverPanel extends GUIPanel {

    /** Label displaying the player's final score. */
    private JLabel scoreLabel;

    /** Label displaying the current level number. */
    private JLabel difficultyLabel;

    /** Background color for the restart button (dark red). */
    private static final Color RESTART_BG = new Color(139, 0, 0);

    /** Background color for the main menu button (sienna). */
    private static final Color MENU_BG = new Color(160, 82, 45);

    /** Border color for buttons (light pink). */
    private static final Color BORDER_COLOR = new Color(255, 182, 193);

    /** Corner radius used for rounded buttons. */
    private static final int CORNER_RADIUS = 25;

    /**
     * Constructs the Game Over screen and initializes its layout, labels, and buttons.
     */
    public GameOverPanel() {
        super(Color.decode("#722F37")); // Cherry wine red
        backgroundImage = GameConfig.GAMEOVER_BACKGROUND;
        setLayout(null); // Use absolute positioning

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBounds(150, 220, 300, 300); // Center positioning

        displayInfo(contentPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Extra spacing
        addButtons(contentPanel);

        add(contentPanel);
    }

    /**
     * Paints the background and title "Game Over" in a handwritten font style.
     *
     * @param g the {@link Graphics} context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(Color.decode("#722F37"));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw "Game Over" handwritten text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font font = getHandwrittenFont(Font.BOLD, 72);
        g2d.setFont(font);
        g2d.setColor(RESTART_BG);

        String text = "Game Over";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 175;
        g2d.drawString(text, x, y);

        g2d.dispose();
    }

    /**
     * Displays the player's score and level info on the given panel.
     *
     * @param centerPanel the panel to which the labels are added
     */
    private void displayInfo(JPanel centerPanel) {
        scoreLabel = createLabel(
                "Score: " + (Game.getGame().getGm() != null ? Game.getGame().getGm().getScore() : 0),
                Color.RED,
                new Font("Arial", Font.BOLD, 24)
        );
        centerPanel.add(scoreLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        difficultyLabel = createLabel(
                "Level: " + Game.getGame().getGm().getCurrentLevel(),
                new Color(218, 112, 161),
                new Font("Arial", Font.PLAIN, 18)
        );
        centerPanel.add(difficultyLabel);
    }

    /**
     * Updates the displayed score and level information after the game ends.
     *
     * @param finalScore the player's final score
     * @param level      the level reached
     */
    public void updateInfo(int finalScore, int level) {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + finalScore);
        }
        if (difficultyLabel != null) {
            difficultyLabel.setText("Level: " + level);
        }
    }

    /**
     * Adds the restart and main menu buttons to the given panel.
     *
     * @param centerPanel the panel where buttons are placed
     */
    private void addButtons(JPanel centerPanel) {
        // Restart button
        JButton restartBtn = createRoundedButton("RESTART", RESTART_BG, BORDER_COLOR, CORNER_RADIUS);
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setMaximumSize(new Dimension(300, 55));
        restartBtn.setPreferredSize(new Dimension(300, 55));
        restartBtn.addActionListener(e -> {
            Game.getGame().changeState(Defs.STATE_PLAYING);
            Game.getGame().getGm().startGame(Game.getGame().getGm().getCurrentLevel());
        });
        centerPanel.add(restartBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Main Menu button
        JButton menuBtn = createRoundedButton("MAIN MENU", MENU_BG, BORDER_COLOR, CORNER_RADIUS);
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 55));
        menuBtn.setPreferredSize(new Dimension(300, 55));
        menuBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_MENU));
        centerPanel.add(menuBtn);
    }
}
