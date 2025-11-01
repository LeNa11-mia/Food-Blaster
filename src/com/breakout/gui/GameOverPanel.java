package com.breakout.gui;

import com.breakout.Game;
import com.breakout.managers.GameManager;
import com.breakout.managers.GameState;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends GUIPanel {

    private Image backgroundImage;

    public GameOverPanel(Game game) {
        super("#722F37");

        // Load background image
        loadBackgroundImage();

        setLayout(null); // Absolute positioning

        // Panel chứa nội dung chính
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBounds(150, 220, 300, 300); // Căn giữa hơn

        displayInfo(game.getGm(), contentPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Khoảng cách lớn hơn
        addButtons(game, contentPanel);

        add(contentPanel);
    }

    private void loadBackgroundImage() {
        try {
            java.net.URL imgURL = getClass().getResource("/com/breakout/resources.assets/gameOver.png");

            if (imgURL == null) {
                imgURL = getClass().getResource("/resources.assets/gameOver.png");
            }

            if (imgURL == null) {
                imgURL = getClass().getResource("/gameOver.png");
            }

            if (imgURL != null) {
                backgroundImage = new ImageIcon(imgURL).getImage();
                System.out.println("✓ GameOverPanel: Ảnh load thành công");
            } else {
                String[] filePaths = {
                        "src/com/breakout/resources.assets/gameOver.png",
                        "./src/com/breakout/resources.assets/gameOver.png"
                };

                for (String path : filePaths) {
                    java.io.File file = new java.io.File(path);
                    if (file.exists()) {
                        backgroundImage = new ImageIcon(file.getAbsolutePath()).getImage();
                        System.out.println("✓ GameOverPanel: Ảnh load từ file");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Lỗi load ảnh: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Vẽ background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(Color.decode("#722F37"));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Vẽ chữ "Game Over" viết tay
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font font;
        try {
            font = new Font("Brush Script MT", Font.BOLD, 72);
        } catch (Exception e) {
            font = new Font("Segoe Script", Font.BOLD, 72);
        }

        g2d.setFont(font);
        g2d.setColor(new Color(139, 0, 0)); // Dark red

        String text = "Game Over";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 175;

        g2d.drawString(text, x, y);

        g2d.dispose();
    }

    private void displayInfo(GameManager gm, JPanel centerPanel) {
        // Score display
        JLabel scoreLabel = new JLabel("Score: " + (gm != null ? gm.getScore() : 0));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        scoreLabel.setForeground(new Color(255, 105, 180));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Difficulty display
        JLabel difficultyLabel = new JLabel("Difficulty: " + gm.getDifficulty());
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        difficultyLabel.setForeground(new Color(218, 112, 161));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(difficultyLabel);
    }

    private void addButtons(Game game, JPanel centerPanel) {
        // Restart button
        JButton restartBtn = createRoundedButton("RESTART", new Color(139, 0, 0));
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setMaximumSize(new Dimension(300, 55));
        restartBtn.setPreferredSize(new Dimension(300, 55));
        restartBtn.addActionListener(e -> {
            game.changeState(GameState.PLAYING);
            game.getGm().startGame(game.getGm().getDifficulty());
        });
        centerPanel.add(restartBtn);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Menu button
        JButton menuBtn = createRoundedButton("MAIN MENU", new Color(160, 82, 45));
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 55));
        menuBtn.setPreferredSize(new Dimension(300, 55));
        menuBtn.addActionListener(e -> game.changeState(GameState.MENU));
        centerPanel.add(menuBtn);
    }

    private JButton createRoundedButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Màu nền với hiệu ứng
                Color currentColor;
                if (getModel().isPressed()) {
                    int r = Math.max(0, bgColor.getRed() - 30);
                    int g1 = Math.max(0, bgColor.getGreen() - 30);
                    int b = Math.max(0, bgColor.getBlue() - 30);
                    currentColor = new Color(r, g1, b);
                } else if (getModel().isRollover()) {
                    int r = Math.min(255, bgColor.getRed() + 30);
                    int g1 = Math.min(255, bgColor.getGreen() + 30);
                    int b = Math.min(255, bgColor.getBlue() + 30);
                    currentColor = new Color(r, g1, b);
                } else {
                    currentColor = bgColor;
                }

                g2d.setColor(currentColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Viền
                g2d.setColor(new Color(255, 182, 193)); // Light pink
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 25, 25);

                // Vẽ chữ
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 3;
                g2d.drawString(getText(), x, y);

                g2d.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
}
