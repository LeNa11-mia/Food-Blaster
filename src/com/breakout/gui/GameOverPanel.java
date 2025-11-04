package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.gui.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends GUIPanel {
    private JLabel scoreLabel;
    private JLabel difficultyLabel;

    private Image backgroundImage;

    public GameOverPanel() {
        super(Color.decode("#722F37")); // Cherry wine red

        backgroundImage = new ImageIcon("src/com/breakout/resources.assets/gameOver.png").getImage();

        setLayout(null); // Absolute positioning

        // Panel chứa nội dung chính
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBounds(150, 220, 300, 300); // Căn giữa hơn

        displayInfo(contentPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Khoảng cách lớn hơn
        addButtons(contentPanel);

        add(contentPanel);
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

    private void displayInfo(JPanel centerPanel) {
        // Score display
        scoreLabel = new JLabel("Score: " + (Game.getGame().getGm() != null ? Game.getGame().getGm().getScore() : 0));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        scoreLabel.setForeground(new Color(255, 105, 180));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel = createLabel(
                "Score: " + (Game.getGame().getGm() != null ? Game.getGame().getGm().getScore() : 0),
                Color.RED,
                new Color(255, 105, 180),
                new Font("Arial", Font.BOLD, 24)
        );
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Difficulty display
        difficultyLabel = createLabel(
                "Difficulty: " + Game.getGame().getGm().getDifficultyName(),
                new Color(218, 112, 161),
                new Font("Arial", Font.PLAIN, 18)
        );
        centerPanel.add(difficultyLabel);
    }

    public void updateInfo(int finalScore, String difficultyName) {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + finalScore);
        }
        if (difficultyLabel != null) {
            difficultyLabel.setText("Difficulty: " + difficultyName);
        }
    }

    private void addButtons(JPanel centerPanel) {
        // Restart button
        JButton restartBtn = createRoundedButton("RESTART", new Color(139, 0, 0));
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setMaximumSize(new Dimension(300, 55));
        restartBtn.setPreferredSize(new Dimension(300, 55));
        restartBtn.addActionListener(e -> {
            Game.getGame().changeState(Defs.STATE_PLAYING);
            Game.getGame().getGm().startGame(Game.getGame().getGm().getCurrentDifficulty());
        });
        centerPanel.add(restartBtn);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Menu button
        JButton menuBtn = createRoundedButton("MAIN MENU", new Color(160, 82, 45));
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 55));
        menuBtn.setPreferredSize(new Dimension(300, 55));
        menuBtn.addActionListener(e -> {
            Game.getGame().changeState(Defs.STATE_MENU);
//            MenuPanel menuPanel = new MenuPanel(Game.getGame());
//            javax.swing.SwingUtilities.invokeLater(() -> {
//                Game.getGame().getFrame().setContentPane(menuPanel);
//                menuPanel.updateMenu();
//            });
        });
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
