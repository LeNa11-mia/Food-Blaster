package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * {@code SettingPanel} represents the Settings screen of the Breakout game.
 * <p>
 * It allows players to adjust music and sound effects (SFX) volumes,
 * and provides buttons for continuing, saving & exiting, or returning to the main menu.
 * </p>
 *
 * <p>
 * The panel uses custom fonts and colors for a stylized UI and includes
 * rounded buttons and sliders for user interaction.
 * </p>
 */
public class SettingPanel extends GUIPanel {
    private JSlider musicSlider;
    private JSlider sfxSlider;
    private JLabel musicValueLabel;
    private JLabel sfxValueLabel;

    // Color constants for UI buttons
    private static final Color CONTINUE_BG = new Color(0x6EE7B7);
    private static final Color SAVE_EXIT_BG = new Color(0xA78BFA);
    private static final Color BACK_MENU_BG = new Color(0xFCA5A5);
    private static final Color BORDER_COLOR = Color.WHITE;
    private static final int CORNER_RADIUS = 25;

    /**
     * Constructs a new {@code SettingPanel} with all UI components initialized.
     */
    public SettingPanel() {
        super(Color.decode("#1a1a2e"));
        backgroundImage = GameConfig.SETTING_BACKGROUND;
        setLayout(new BorderLayout());
        setFocusable(true); // Must receive keyboard focus

        // === MAIN PANEL SETUP ===
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(50, 100, 50, 100));

        // === TITLE ===
        JLabel titleLabel = createHandwrittenLabel("Settings", Color.WHITE, 52);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(50));

        // === MUSIC SLIDER ===
        VolumeControlResult musicResult = createVolumePanel("Music Volume", 70);
        musicSlider = musicResult.slider;
        musicValueLabel = musicResult.valueLabel;
        mainPanel.add(musicResult.panel);
        mainPanel.add(Box.createVerticalStrut(30));

        // === SFX SLIDER ===
        VolumeControlResult sfxResult = createVolumePanel("Sound Effects", 80);
        sfxSlider = sfxResult.slider;
        sfxValueLabel = sfxResult.valueLabel;
        mainPanel.add(sfxResult.panel);
        mainPanel.add(Box.createVerticalStrut(50));

        // === BUTTONS ===
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        JButton continueBtn = createStyledRoundedButton("Continue", CONTINUE_BG);
        continueBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_PLAYING));
        buttonsPanel.add(continueBtn);
        buttonsPanel.add(Box.createVerticalStrut(15));

        JButton saveExitBtn = createStyledRoundedButton("SAVE & EXIT TO MENU", SAVE_EXIT_BG);
        saveExitBtn.addActionListener(e -> Game.getGame().saveAndExitToMenu());
        buttonsPanel.add(saveExitBtn);
        buttonsPanel.add(Box.createVerticalStrut(15));

        JButton menuBtn = createStyledRoundedButton("Back to Menu (No Save)", BACK_MENU_BG);
        menuBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_MENU));
        buttonsPanel.add(menuBtn);

        mainPanel.add(buttonsPanel);

        // === CENTER WRAPPER ===
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(mainPanel);
        add(centerWrapper, BorderLayout.CENTER);

        // === HINT LABEL ===
        JLabel hintLabel = createLabel(
                "Press ESC to return without saving | Press SPACE to toggle settings",
                new Color(255, 255, 255, 150),
                new Font("Times New Roman", Font.ITALIC, 14)
        );
        hintLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(hintLabel, BorderLayout.SOUTH);
    }

    /**
     * Creates a title label with a handwritten-style font.
     *
     * @param text the label text
     * @param color the text color
     * @param size the font size
     * @return a stylized {@link JLabel} for the title
     */
    private JLabel createHandwrittenLabel(String text, Color color, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(getHandwrittenFont(Font.PLAIN, size));
        return label;
    }

    /**
     * Creates a standard label using Times New Roman.
     *
     * @param text the label text
     * @param color the text color
     * @param size the font size
     * @param style the font style (e.g., {@link Font#BOLD})
     * @return a formatted {@link JLabel}
     */
    private JLabel createTimesNewRomanLabel(String text, Color color, int size, int style) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Times New Roman", style, size));
        return label;
    }

    /**
     * Draws the background image scaled to fit the entire panel.
     *
     * @param g the {@link Graphics} context to draw on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Helper class to return multiple components for volume control (panel, slider, and label).
     */
    private static class VolumeControlResult {
        JPanel panel;
        JSlider slider;
        JLabel valueLabel;

        VolumeControlResult(JPanel panel, JSlider slider, JLabel valueLabel) {
            this.panel = panel;
            this.slider = slider;
            this.valueLabel = valueLabel;
        }
    }

    /**
     * Creates a volume control panel with a label and slider.
     *
     * @param labelText the text label for the slider
     * @param defaultValue the default slider value (0–100)
     * @return a {@link VolumeControlResult} containing UI components
     */
    private VolumeControlResult createVolumePanel(String labelText, int defaultValue) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(600, 80));

        JPanel labelRow = new JPanel(new BorderLayout());
        labelRow.setOpaque(false);

        JLabel nameLabel = createTimesNewRomanLabel(labelText, Color.WHITE, 18, Font.BOLD);
        JLabel valueLabel = createTimesNewRomanLabel(defaultValue + "%", new Color(255, 105, 180), 20, Font.BOLD);
        labelRow.add(nameLabel, BorderLayout.WEST);
        labelRow.add(valueLabel, BorderLayout.EAST);
        panel.add(labelRow);
        panel.add(Box.createVerticalStrut(10));

        JSlider slider = new JSlider(0, 100, defaultValue);
        slider.setOpaque(false);
        slider.setForeground(new Color(255, 105, 180));
        slider.setBackground(new Color(58, 58, 80));

        // Custom look for slider track and thumb
        slider.setUI(new javax.swing.plaf.basic.BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Rectangle trackBounds = trackRect;
                g2d.setColor(new Color(58, 58, 80));
                g2d.fillRoundRect(trackBounds.x, trackBounds.y + trackBounds.height / 2 - 4,
                        trackBounds.width, 8, 5, 5);
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Rectangle thumbBounds = thumbRect;
                GradientPaint gradient = new GradientPaint(
                        thumbBounds.x, thumbBounds.y, new Color(255, 105, 180),
                        thumbBounds.x, thumbBounds.y + thumbBounds.height, new Color(255, 20, 147)
                );
                g2d.setPaint(gradient);
                g2d.fillOval(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
            }
        });

        slider.addChangeListener(e -> valueLabel.setText(slider.getValue() + "%"));
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setOpaque(false);
        sliderPanel.add(slider, BorderLayout.CENTER);
        panel.add(sliderPanel);

        return new VolumeControlResult(panel, slider, valueLabel);
    }

    /**
     * Creates a rounded button with Times New Roman font.
     *
     * @param text the button text
     * @param baseColor the base background color
     * @return a customized {@link JButton} with rounded corners
     */
    private JButton createStyledRoundedButton(String text, Color baseColor) {
        JButton button = createRoundedButton(text, baseColor, BORDER_COLOR, CORNER_RADIUS);
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setMaximumSize(new Dimension(600, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(new EmptyBorder(10, 30, 10, 30));
        return button;
    }

    /** @return the current music volume (0–100) */
    public int getMusicVolume() {
        return musicSlider.getValue();
    }

    /** @return the current SFX volume (0–100) */
    public int getSfxVolume() {
        return sfxSlider.getValue();
    }

    /**
     * Sets the music volume and updates the display label.
     *
     * @param volume the volume level (0–100)
     */
    public void setMusicVolume(int volume) {
        musicSlider.setValue(volume);
        musicValueLabel.setText(volume + "%");
    }

    /**
     * Sets the SFX volume and updates the display label.
     *
     * @param volume the volume level (0–100)
     */
    public void setSfxVolume(int volume) {
        sfxSlider.setValue(volume);
        sfxValueLabel.setText(volume + "%");
    }
}
