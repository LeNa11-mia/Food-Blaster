package com.breakout.gui;

import com.breakout.Game;
import com.breakout.managers.GameState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class SettingPanel extends GUIPanel {
    private Game game;
    private JSlider musicSlider;
    private JSlider sfxSlider;
    private JLabel musicValueLabel;
    private JLabel sfxValueLabel;
    private Image backgroundImage;

    public SettingPanel(Game game) {
        super("#1a1a2e");
        this.game = game;
        setLayout(new BorderLayout());

        // Quan trọng: Panel phải có thể nhận keyboard input
        setFocusable(true);

        // Load background image
        loadBackgroundImage();

        // Main panel with settings
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false); // Trong suốt để thấy background
        mainPanel.setBorder(new EmptyBorder(50, 100, 50, 100));

        // Title với font chữ viết tay (chỉ cho chữ "Settings")
        JLabel titleLabel = createHandwrittenLabel("Settings", Color.WHITE, 52);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(50));

        // Music Volume Control - sử dụng Times New Roman
        VolumeControlResult musicResult = createVolumePanel("Music Volume", 70);
        musicSlider = musicResult.slider;
        musicValueLabel = musicResult.valueLabel;
        mainPanel.add(musicResult.panel);
        mainPanel.add(Box.createVerticalStrut(30));

        // SFX Volume Control - sử dụng Times New Roman
        VolumeControlResult sfxResult = createVolumePanel("Sound Effects", 80);
        sfxSlider = sfxResult.slider;
        sfxValueLabel = sfxResult.valueLabel;
        mainPanel.add(sfxResult.panel);
        mainPanel.add(Box.createVerticalStrut(50));

        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        // Continue Button với bo góc và màu hồng - sử dụng Times New Roman
        JButton continueBtn = createRoundedButton("Continue", new Color(255, 105, 180)); // Màu hồng Hot Pink
        continueBtn.addActionListener(e -> {
            // Return to previous state (PLAYING)
            game.changeState(GameState.PLAYING);
        });
        buttonsPanel.add(continueBtn);
        buttonsPanel.add(Box.createVerticalStrut(15));

        // Back to Menu Button với bo góc và màu hồng - sử dụng Times New Roman
        JButton menuBtn = createRoundedButton("Back to Menu", new Color(255, 105, 180)); // Màu hồng Hot Pink
        menuBtn.addActionListener(e -> {
            game.changeState(GameState.MENU);
        });
        buttonsPanel.add(menuBtn);

        mainPanel.add(buttonsPanel);

        // Center everything
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(mainPanel);

        add(centerWrapper, BorderLayout.CENTER);

        // Hint at bottom - sử dụng Times New Roman
        JLabel hintLabel = createLabel("Press SPACE to toggle settings",
                new Color(255, 255, 255, 150),
                new Font("Times New Roman", Font.ITALIC, 14));
        hintLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(hintLabel, BorderLayout.SOUTH);
    }

    // Phương thức tạo label với font chữ viết tay (chỉ dùng cho title)
    private JLabel createHandwrittenLabel(String text, Color color, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(color);

        // Thử sử dụng các font chữ viết tay phổ biến
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

        // Fallback font nếu không tìm thấy font chữ viết tay
        if (handwrittenFont == null) {
            handwrittenFont = new Font("Arial", Font.ITALIC, size);
        }

        label.setFont(handwrittenFont);
        return label;
    }

    // Phương thức helper để tạo label với Times New Roman
    private JLabel createTimesNewRomanLabel(String text, Color color, int size, int style) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Times New Roman", style, size));
        return label;
    }

    private void loadBackgroundImage() {
        try {
            // Thử load ảnh từ resources
            java.net.URL imgURL = getClass().getResource("/com/breakout/resources.assets/setting.png");
            if (imgURL != null) {
                backgroundImage = new ImageIcon(imgURL).getImage();
                System.out.println("✓ SettingPanel: Ảnh load thành công từ: " + imgURL);
            } else {
                System.err.println("✗ SettingPanel: Không tìm thấy ảnh mainMenu.png");
            }
        } catch (Exception e) {
            System.err.println("✗ SettingPanel: Lỗi load ảnh: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ background image nếu có
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Scale ảnh để fit toàn màn hình
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Helper class để return nhiều giá trị
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

    private VolumeControlResult createVolumePanel(String labelText, int defaultValue) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(600, 80));

        // Label row with name and value - sử dụng Times New Roman
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
        slider.setForeground(new Color(255, 105, 180)); // Màu hồng
        slider.setBackground(new Color(58, 58, 80));

        // Custom UI for better appearance
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
                        thumbBounds.x, thumbBounds.y, new Color(255, 105, 180), // Màu hồng
                        thumbBounds.x, thumbBounds.y + thumbBounds.height, new Color(255, 20, 147) // Màu hồng đậm
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

    // Tạo button bo góc với màu hồng - sử dụng Times New Roman
    private JButton createRoundedButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền bo góc
                if (getModel().isPressed()) {
                    g2.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(baseColor.brighter());
                } else {
                    g2.setColor(baseColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(baseColor.brighter());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
            }
        };

        button.setForeground(Color.WHITE);
        // Sử dụng Times New Roman cho button
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

    public int getMusicVolume() {
        return musicSlider.getValue();
    }

    public int getSfxVolume() {
        return sfxSlider.getValue();
    }

    public void setMusicVolume(int volume) {
        musicSlider.setValue(volume);
        musicValueLabel.setText(volume + "%");
    }

    public void setSfxVolume(int volume) {
        sfxSlider.setValue(volume);
        sfxValueLabel.setText(volume + "%");
    }
}
