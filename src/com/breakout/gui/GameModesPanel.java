package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.Level;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code GameModesPanel} represents the GUI screen where players can select
 * different game levels or return to the main menu.
 * <p>
 * This panel displays level buttons arranged in a grid layout and a "Back" button
 * for navigation. Each level button indicates whether it is unlocked or not.
 * </p>
 */
public class GameModesPanel extends GUIPanel {

    /** Buttons representing each level. */
    private JButton[] buttons;

    /** Background color for the "Back" button. */
    private static final Color BACK_BG = Color.decode("#D8BFD8"); // Thistle

    /** Background color for level buttons. */
    private static final Color LEVEL_BG = Color.decode("#FFC0CB"); // Pink

    /** Border color for rounded buttons. */
    private static final Color BORDER_COLOR = Color.PINK;

    /** Corner radius for rounded buttons. */
    private static final int CORNER_RADIUS = 20;

    /**
     * Constructs the {@code GameModesPanel} and initializes the level selection UI.
     */
    public GameModesPanel() {
        super(Color.decode("#F3CFC6"));
        backgroundImage = GameConfig.GAMEMODES_BACKGROUND;

        setLayout(null); // Use absolute positioning

        buttons = new JButton[GameConfig.TOTAL_LEVELS];

        JPanel modesPanel = createModesPanel();
        modesPanel.setBounds(125, 280, 350, 320); // x, y, width, height
        add(modesPanel);
    }

    /**
     * Paints the background, title text, and other components on the panel.
     *
     * @param g the {@link Graphics} context to draw with
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(Color.decode("#FFC0CB"));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw title text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font font = getHandwrittenFont(Font.BOLD, 60);
        g2d.setFont(font);
        g2d.setColor(new Color(255, 105, 180));

        String text = "Select Level";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 150;
        g2d.drawString(text, x, y);

        g2d.dispose();
    }

    /**
     * Creates a panel that contains all level buttons and the back button.
     *
     * @return a configured {@link JPanel} containing the level and back buttons
     */
    private JPanel createModesPanel() {
        JPanel modesPanel = new JPanel(new BorderLayout());
        modesPanel.setOpaque(false);

        JPanel levelGrid = new JPanel(new GridLayout(2, 3, 25, 25));
        levelGrid.setOpaque(false);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

        // Back button
        LevelButton backBtn = new LevelButton("← BACK", BACK_BG, Defs.GO_BACK);
        backBtn.setFont(new Font("Arial", Font.BOLD, 20));
        bottomPanel.add(backBtn);

        // Create level buttons
        for (int i = 0; i < GameConfig.TOTAL_LEVELS; i++) {
            buttons[i] = new LevelButton(Integer.toString(i + 1), LEVEL_BG, i + 1);
            levelGrid.add(buttons[i]);
        }

        modesPanel.add(levelGrid, BorderLayout.CENTER);
        modesPanel.add(bottomPanel, BorderLayout.SOUTH);
        return modesPanel;
    }

    /**
     * Inner class representing a styled rounded button for level selection and navigation.
     */
    private class LevelButton extends JButton {

        /** Base background color of the button. */
        private final Color baseColor;

        /** Mode of this button (either a level number or {@link Defs#GO_BACK}). */
        private final int levelMode;

        /**
         * Constructs a {@code LevelButton} with the given text, background color, and mode.
         *
         * @param text   the label text of the button
         * @param bgColor the base background color
         * @param mode   the level mode or action identifier
         */
        public LevelButton(String text, Color bgColor, int mode) {
            super(text);
            this.baseColor = bgColor;
            this.levelMode = mode;
            setupButton(text);
            addActionListener(e -> handleAction());
        }

        /**
         * Configures visual and behavioral properties of the button.
         *
         * @param text the text to display on the button
         */
        private void setupButton(String text) {
            setFont(new Font("Arial", Font.BOLD, 24));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(300, 50));
        }

        /**
         * Handles button actions:
         * <ul>
         *   <li>If it's the back button, returns to the main menu.</li>
         *   <li>If it's a level button, starts that level (if unlocked).</li>
         * </ul>
         */
        private void handleAction() {
            if (levelMode == Defs.GO_BACK) {
                Game.getGame().changeState(Defs.STATE_MENU);
            } else if (Level.isLevelUnlocked(levelMode)) {
                Game.getGame().getGm().startGame(levelMode);
                Game.getGame().changeState(Defs.STATE_PLAYING);
            }
        }

        /**
         * Custom rendering for rounded button visuals with hover and press effects.
         *
         * @param g the {@link Graphics} context to draw the button with
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            try {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color actualBgColor = baseColor;
                boolean isLocked = levelMode != Defs.GO_BACK && !Level.isLevelUnlocked(levelMode);

                if (isLocked) {
                    actualBgColor = Color.LIGHT_GRAY;
                } else {
                    if (getModel().isPressed()) {
                        actualBgColor = adjustColor(baseColor, -30);
                    } else if (getModel().isRollover()) {
                        actualBgColor = adjustColor(baseColor, +20);
                    }
                }

                // Draw background
                g2d.setColor(actualBgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);

                // Draw border
                g2d.setColor(BORDER_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, CORNER_RADIUS, CORNER_RADIUS);

                // Draw text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;
                g2d.drawString(getText(), x, y);
            } finally {
                g2d.dispose();
            }
        }

        /**
         * Adjusts a color's brightness by adding or subtracting a value from its RGB components.
         *
         * @param color the original color
         * @param delta the adjustment value (-30 for darker, +20 for lighter)
         * @return the adjusted {@link Color}
         */
        private Color adjustColor(Color color, int delta) {
            int r = Math.min(255, Math.max(0, color.getRed() + delta));
            int g = Math.min(255, Math.max(0, color.getGreen() + delta));
            int b = Math.min(255, Math.max(0, color.getBlue() + delta));
            return new Color(r, g, b);
        }
    }
}
