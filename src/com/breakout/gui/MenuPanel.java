package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.SaveManager;

import javax.swing.*;
import java.awt.*;

/**
 * The main menu panel displayed when the game starts.
 * <p>
 * Provides navigation options such as starting a new game,
 * continuing a saved game, or exiting the application.
 * It also handles background rendering and custom button layout.
 * </p>
 */
public class MenuPanel extends GUIPanel {

    /** Button used for continuing a saved game (only shown if a save exists). */
    private JButton continueButton;

    /** Default border color for all buttons in the menu. */
    private static final Color BORDER_COLOR = Color.PINK;

    /** Corner radius for rounded buttons. */
    private static final int CORNER_RADIUS = 20;

    /**
     * Constructs the main menu panel.
     * Initializes layout, background image, and button arrangement.
     */
    public MenuPanel() {
        super(Color.decode("#F3CFC6"));

        // Load the background image from configuration
        backgroundImage = GameConfig.MENU_BACKGROUND;

        // Use absolute positioning for precise placement
        setLayout(null);

        // Create and position the button panel
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(150, 400, 300, 150);
        add(buttonPanel);
    }

    /**
     * Custom rendering method for the panel.
     * Draws the background and the stylized game title text.
     *
     * @param g the Graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw background image to fill the screen
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Enable smooth text rendering
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Handwritten-style title
        Font font = getHandwrittenFont(Font.BOLD, 72);
        g2d.setFont(font);
        g2d.setColor(new Color(255, 105, 180)); // Soft pink color

        String text = "Food Blaster";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 120; // Position near the top white area

        g2d.drawString(text, x, y);
        g2d.dispose();
    }

    /**
     * Creates the panel containing all interactive buttons
     * (PLAY, CONTINUE if available, and EXIT).
     *
     * @return A JPanel containing menu buttons with grid layout.
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        boolean hasSave = SaveManager.saveExists();

        // Layout: 3 buttons if save exists, otherwise 2
        buttonPanel.setLayout(new GridLayout(hasSave ? 3 : 2, 1, 15, 20));
        buttonPanel.setOpaque(false);

        // --- PLAY button ---
        JButton playBtn = createRoundedButton("PLAY", Color.decode("#F8C8DC"), BORDER_COLOR, CORNER_RADIUS);
        playBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_GAME_MODES));
        buttonPanel.add(playBtn);

        // --- CONTINUE button (if save exists) ---
        if (hasSave) {
            continueButton = createRoundedButton("CONTINUE", Color.decode("#FFB6C1"), BORDER_COLOR, CORNER_RADIUS);
            continueButton.addActionListener(e -> Game.getGame().startContinueGame());
            buttonPanel.add(continueButton);
        }

        // --- EXIT button ---
        JButton exitBtn = createRoundedButton("EXIT", Color.decode("#D8BFD8"), BORDER_COLOR, CORNER_RADIUS);
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        return buttonPanel;
    }

    /**
     * Refreshes the entire menu by rebuilding its UI components.
     * Used when the save state changes (e.g., new save created or deleted).
     */
    public void updateMenu() {
        removeAll();
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(150, 400, 300, 150);
        add(buttonPanel);
        revalidate();
        repaint();
    }

    /**
     * Enables or disables the CONTINUE button depending on whether
     * the player can resume a saved game.
     *
     * @param enabled true to enable the button, false to disable it
     */
    public void setContinueButtonEnabled(boolean enabled) {
        if (continueButton != null) {
            continueButton.setEnabled(Game.getGame().getGm().canContinueGame());
        }
    }

    /**
     * Rebuilds and refreshes the menu state.
     * Used when reloading the main menu after gameplay.
     */
    public void refreshMenu() {
        updateMenu();
    }
}
