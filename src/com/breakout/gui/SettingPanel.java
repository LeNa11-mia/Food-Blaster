package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Settings panel for configuring game options including audio volume and game preferences.
 * Provides sliders for music and sound effects volume with visual feedback.
 * Includes options to save settings, return to game, or exit to main menu.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class SettingPanel extends GUIPanel {
    /** Slider for controlling background music volume */
    private JSlider musicSlider;

    /** Slider for controlling sound effects volume */
    private JSlider sfxSlider;

    /** Label displaying current music volume percentage */
    private JLabel musicValueLabel;

    /** Label displaying current sound effects volume percentage */
    private JLabel sfxValueLabel;

    /**
     * Constructor initializes the settings panel with volume controls and navigation buttons.
     * Sets up the visual layout with custom styling and interactive components.
     */
    public SettingPanel() {
        super(Color.decode("#1a1a2e"));
        backgroundImage = GameConfig.SETTING_BACKGROUND;
        setLayout(new BorderLayout());

        // Important: Panel must be able to receive keyboard input
        setFocusable(true);

        // Main panel with settings
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false); // Transparent to show background
        mainPanel.setBorder(new EmptyBorder(50, 100, 50, 100));

        // Title with handwritten font (only for "Settings" text)
        JLabel titleLabel = createHandwrittenLabel("Settings", Color.WHITE, 52);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(50));

        // Music Volume Control - using Times New Roman
        VolumeControlResult musicResult = createVolumePanel("Music Volume", 70);
        musicSlider = musicResult.slider;
        musicValueLabel = musicResult.valueLabel;
        mainPanel.add(musicResult.panel);
        mainPanel.add(Box.createVerticalStrut(30));

        // SFX Volume Control - using Times New Roman
        VolumeControlResult sfxResult = createVolumePanel("Sound Effects", 80);
        sfxSlider = sfxResult.slider;
        sfxValueLabel = sfxResult.valueLabel;
        mainPanel.add(sfxResult.panel);
        mainPanel.add(Box.createVerticalStrut(50));

        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        // Continue Button with rounded corners and pink color - using Times New Roman
        JButton continueBtn = createRoundedButton("Continue", new Color(0x6EE7B7)); // Hot Pink color
        continueBtn.addActionListener(e -> {
            // Return to previous state (PLAYING)
            Game.getGame().changeState(Defs.STATE_PLAYING);
        });
        buttonsPanel.add(continueBtn);
        buttonsPanel.add(Box.createVerticalStrut(15));

        // SAVE & EXIT Button - Save game and return to menu
        JButton saveExitBtn = createRoundedButton("SAVE & EXIT TO MENU", new Color(0xA78BFA)); // Green color
        saveExitBtn.addActionListener(e -> {
            // Save game and return to menu
            Game.getGame().saveAndExitToMenu();
        });
        buttonsPanel.add(saveExitBtn);
        buttonsPanel.add(Box.createVerticalStrut(15));

        // Back to Menu Button (no save) - using Times New Roman
        JButton menuBtn = createRoundedButton("Back to Menu (No Save)", new Color(0xFCA5A5)); // Red-orange color
        menuBtn.addActionListener(e -> {
            // Return to menu without saving
            Game.getGame().changeState(Defs.STATE_MENU);
        });
        buttonsPanel.add(menuBtn);

        mainPanel.add(buttonsPanel);

        // Center everything
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(mainPanel);

        add(centerWrapper, BorderLayout.CENTER);

        // Hint at bottom - using Times New Roman
        JLabel hintLabel = createLabel("Press ESC to return without saving | Press SPACE to toggle settings",
                new Color(255, 255, 255, 150),
                new Font("Times New Roman", Font.ITALIC, 14));
        hintLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(hintLabel, BorderLayout.SOUTH);
    }

    /**
     * Creates a label with handwritten-style font for decorative titles.
     * Attempts to use various handwritten fonts with fallback options.
     *
     * @param text the text to display
     * @param color the text color
     * @param size the font size
     * @return JLabel the created handwritten-style label
     */
    private JLabel createHandwrittenLabel(String text, Color color, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(color);

        // Try to use common handwritten fonts
        String[] handwrittenFonts = {
                "Brush Script MT", "Lucida Handwriting", "Comic Sans MS",
                "Segoe Print", "Bradley Hand", "Ink Free"
        };

        Font handwrittenFont = null;
        for (String fontName : handwrittenFonts) {
            Font font = new Font(fontName, Font.PLAIN, size);
            if (font.getFamily().equals(fontName)) {
                handwrittenFont = font;
                break;
            }
        }

        // Fallback font if no handwritten font is found
        if (handwrittenFont == null) {
            handwrittenFont = new Font("Arial", Font.ITALIC, size);
        }

        label.setFont(handwrittenFont);
        return label;
    }

    /**
     * Creates a label with Times New Roman font for consistent typography.
     *
     * @param text the text to display
     * @param color the text color
     * @param size the font size
     * @param style the font style (Font.BOLD, Font.ITALIC, etc.)
     * @return JLabel the created Times New Roman label
     */
    private JLabel createTimesNewRomanLabel(String text, Color color, int size, int style) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Times New Roman", style, size));
        return label;
    }

    /**
     * Creates a styled label with specified font and color properties.
     *
     * @param text the display text
     * @param color the text color
     * @param font the font to use
     * @return JLabel the formatted label
     */
    protected JLabel createLabel(String text, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Custom painting method that renders the background image.
     * Applies bilinear interpolation for smooth image scaling.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image if available
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Scale image to fit full screen
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Helper class for returning multiple values from volume control creation.
     * Contains the panel, slider, and value label components.
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
     * Creates a volume control panel with label, slider, and percentage display.
     * Features custom-styled slider with visual feedback and real-time updates.
     *
     * @param labelText the description text for the volume control
     * @param defaultValue the initial volume percentage value
     * @return VolumeControlResult containing the created components
     */
    private VolumeControlResult createVolumePanel(String labelText, int defaultValue) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(600, 80));

        // Label row with name and value - using Times New Roman
        JPanel labelRow = new JPanel(new BorderLayout());
        labelRow.setOpaque(false);

        JLabel nameLabel = createTimesNewRomanLabel(labelText, Color.WHITE, 18, Font.BOLD);
        JLabel valueLabel = createTimesNewRomanLabel(defaultValue + "%", new Color(255, 105, 180), 20, Font.BOLD);

        labelRow.add(nameLabel, BorderLayout.WEST);
        labelRow.add(valueLabel, BorderLayout.EAST);

        panel.add(labelRow);
        panel.add(Box.createVerticalStrut(10));

        // Slider
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setOpaque(false);

        JSlider slider = new JSlider(0, 100, defaultValue);
        slider.setOpaque(false);
        slider.setForeground(new Color(255, 105, 180)); // Pink color
        slider.setBackground(new Color(58, 58, 80));

        // Custom UI for better appearance
        slider.setUI(new javax.swing.plaf.basic.BasicSliderUI(slider) {
            /**
             * Paints the slider track with custom rounded rectangle styling.
             */
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Rectangle trackBounds = trackRect;
                g2d.setColor(new Color(58, 58, 80));
                g2d.fillRoundRect(trackBounds.x, trackBounds.y + trackBounds.height / 2 - 4,
                        trackBounds.width, 8, 5, 5);
            }

            /**
             * Paints the slider thumb with gradient effect and circular shape.
             */
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Rectangle thumbBounds = thumbRect;
                GradientPaint gradient = new GradientPaint(
                        thumbBounds.x, thumbBounds.y, new Color(255, 105, 180), // Pink color
                        thumbBounds.x, thumbBounds.y + thumbBounds.height, new Color(255, 20, 147) // Dark pink
                );
                g2d.setPaint(gradient);
                g2d.fillOval(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
            }
        });

        // Update value label on slider change
        slider.addChangeListener(e -> {
            valueLabel.setText(slider.getValue() + "%");
            // Here you can add actual volume control logic
        });

        sliderPanel.add(slider, BorderLayout.CENTER);
        panel.add(sliderPanel);

        return new VolumeControlResult(panel, slider, valueLabel);
    }

    /**
     * Creates a rounded button with hover effects and custom styling.
     * Features gradient-like visual feedback on interaction.
     *
     * @param text the button text
     * @param baseColor the primary color of the button
     * @return JButton the created rounded button
     */
    private JButton createRoundedButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            /**
             * Custom painting for rounded buttons with visual states.
             * Handles normal, hover, and pressed states with color variations.
             */
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded background
                if (getModel().isPressed()) {
                    g2.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(baseColor.brighter());
                } else {
                    g2.setColor(baseColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;
                g2.drawString(getText(), x, y);
            }

            /**
             * Paints the button border with rounded corners.
             */
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(baseColor.brighter());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
            }
        };

        button.setForeground(Color.WHITE);
        // Use Times New Roman for button
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setMaximumSize(new Dimension(600, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 30, 10, 30));

        return button;
    }

    /**
     * Gets the current music volume setting.
     *
     * @return int the music volume percentage (0-100)
     */
    public int getMusicVolume() {
        return musicSlider.getValue();
    }

    /**
     * Gets the current sound effects volume setting.
     *
     * @return int the SFX volume percentage (0-100)
     */
    public int getSfxVolume() {
        return sfxSlider.getValue();
    }

    /**
     * Sets the music volume and updates the display.
     *
     * @param volume the music volume percentage (0-100)
     */
    public void setMusicVolume(int volume) {
        musicSlider.setValue(volume);
        musicValueLabel.setText(volume + "%");
    }

    /**
     * Sets the sound effects volume and updates the display.
     *
     * @param volume the SFX volume percentage (0-100)
     */
    public void setSfxVolume(int volume) {
        sfxSlider.setValue(volume);
        sfxValueLabel.setText(volume + "%");
    }
}
