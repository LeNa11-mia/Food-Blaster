package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.GameManager;

import javax.swing.*;
import java.awt.*;

/**
 * {@code WinPanel} represents the victory screen shown after the player completes a level.
 * <p>
 * It displays the player's score, the current level, and provides buttons to:
 * <ul>
 *     <li>Proceed to the next level (if available)</li>
 *     <li>Restart the current level</li>
 *     <li>Return to the main menu</li>
 * </ul>
 * </p>
 *
 * <p>
 * This panel extends {@link GUIPanel}, inheriting helper methods for consistent UI creation,
 * such as rounded buttons and bordered labels.
 * </p>
 */
public class WinPanel extends GUIPanel {
    /** Displays the player's final score. */
    private JLabel scoreLabel;

    /** Displays the current difficulty or level. */
    private JLabel difficultyLabel;

    // === UI Color and Style Constants ===
    private static final Color NEXT_LEVEL_BG = Color.decode("#228B22"); // Forest green
    private static final Color RESTART_BG = Color.decode("#6B8E23");    // Olive drab
    private static final Color MENU_BG = Color.decode("#8B7355");       // Burlywood
    private static final Color BORDER_COLOR = Color.WHITE;              // White border
    private static final int CORNER_RADIUS = 15;                        // Slightly rounded corners

    /**
     * Constructs the {@code WinPanel} and initializes all UI components.
     * <p>
     * The panel layout includes a title ("YOU WIN!"), score and level display,
     * and a set of interactive buttons at the center.
     * </p>
     */
    public WinPanel() {
        super(Color.decode("#2D5016")); // Dark green background
        backgroundImage = GameConfig.WIN_BACKGROUND;

        // === Center Container ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.decode("#2D5016"));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));

        // Add labels and player info
        displayInfo(centerPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Add action buttons
        addButtons(centerPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Displays the title ("YOU WIN!"), player's score, and current level.
     * <p>
     * Also adds an instructional label at the bottom.
     * </p>
     *
     * @param centerPanel the main content panel where the info components are added
     */
    private void displayInfo(JPanel centerPanel) {
        // === Title ===
        JLabel titleLabel = createBorderedLabel(
                "YOU WIN!",
                Color.decode("#90EE90"),
                new Font("Courier", Font.BOLD, 48),
                BorderFactory.createEmptyBorder(80, 0, 30, 0)
        );
        add(titleLabel, BorderLayout.NORTH);

        // === Score Display ===
        scoreLabel = createLabel(
                "Score: " + (Game.getGame().getGm() != null ? Game.getGame().getGm().getScore() : 0),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 24)
        );
        centerPanel.add(scoreLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // === Level Display ===
        difficultyLabel = createLabel(
                "Level: " + Game.getGame().getGm().getCurrentLevel(),
                Color.WHITE,
                new Font("Arial", Font.PLAIN, 18)
        );
        centerPanel.add(difficultyLabel);

        // === Instruction Label ===
        JLabel instructionLabel = createBorderedLabel(
                "Click buttons to continue",
                Color.decode("#90EE90"),
                new Font("Arial", Font.ITALIC, 14),
                BorderFactory.createEmptyBorder(20, 0, 20, 0)
        );
        add(instructionLabel, BorderLayout.SOUTH);
    }

    /**
     * Updates the displayed score and level after the player finishes a game or level.
     *
     * @param finalScore the player's final score
     * @param level the current level number
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
     * Adds functional buttons to the Win Panel:
     * <ul>
     *     <li><b>Next Level</b> - proceeds to the next difficulty level (if available)</li>
     *     <li><b>Restart</b> - restarts the current level</li>
     *     <li><b>Main Menu</b> - returns to the main menu</li>
     * </ul>
     *
     * @param centerPanel the container panel to which buttons are added
     */
    private void addButtons(JPanel centerPanel) {
        GameManager gm = Game.getGame().getGm();
        int nextLevel = gm.getNextDifficulty();

        // === NEXT LEVEL BUTTON ===
        if (nextLevel > 0) {
            JButton nextBtn = createRoundedButton("NEXT LEVEL", NEXT_LEVEL_BG, BORDER_COLOR, CORNER_RADIUS);
            nextBtn.setFont(new Font("Arial", Font.BOLD, 18));
            nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            nextBtn.setMaximumSize(new Dimension(300, 50));
            nextBtn.addActionListener(e -> {
                gm.startGame(gm.getNextDifficulty());
                Game.getGame().changeState(Defs.STATE_PLAYING);
            });
            centerPanel.add(nextBtn);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        // === RESTART BUTTON ===
        JButton restartBtn = createRoundedButton("RESTART", RESTART_BG, BORDER_COLOR, CORNER_RADIUS);
        restartBtn.setFont(new Font("Arial", Font.BOLD, 18));
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setMaximumSize(new Dimension(300, 50));
        restartBtn.addActionListener(e -> {
            gm.startGame(gm.getCurrentLevel());
            Game.getGame().changeState(Defs.STATE_PLAYING);
        });
        centerPanel.add(restartBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // === MAIN MENU BUTTON ===
        JButton menuBtn = createRoundedButton("MAIN MENU", MENU_BG, BORDER_COLOR, CORNER_RADIUS);
        menuBtn.setFont(new Font("Arial", Font.BOLD, 18));
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 50));
        menuBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_MENU));
        centerPanel.add(menuBtn);
    }
}
