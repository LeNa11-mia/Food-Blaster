package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends GUIPanel {
    private JLabel scoreLabel;
    private JLabel difficultyLabel;
    private static final Color RESTART_BG = new Color(139, 0, 0); // Dark red
    private static final Color MENU_BG = new Color(160, 82, 45); // Sienna
    private static final Color BORDER_COLOR = new Color(255, 182, 193); // Light pink
    private static final int CORNER_RADIUS = 25;

    public GameOverPanel() {
        super(Color.decode("#722F37")); // Cherry wine red

        backgroundImage = GameConfig.GAMEOVER_BACKGROUND;

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
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(Color.decode("#722F37"));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Vẽ chữ "Game Over" viết tay
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Sử dụng phương thức chung từ GUIPanel
        Font font = getHandwrittenFont(Font.BOLD, 72);

        g2d.setFont(font);
        g2d.setColor(RESTART_BG); // Dark red

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
        scoreLabel = createLabel(
                "Score: " + (Game.getGame().getGm() != null ? Game.getGame().getGm().getScore() : 0),
                Color.RED,
                new Font("Arial", Font.BOLD, 24)
        );
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Difficulty display
        difficultyLabel = createLabel(
                "Level: " + Game.getGame().getGm().getCurrentLevel(),
                new Color(218, 112, 161),
                new Font("Arial", Font.PLAIN, 18)
        );
        centerPanel.add(difficultyLabel);
    }

    public void updateInfo(int finalScore, int level) {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + finalScore);
        }
        if (difficultyLabel != null) {
            // Sửa lỗi code không nhất quán: "Level: " thay vì "Difficulty: "
            difficultyLabel.setText("Level: " + level);
        }
    }

    private void addButtons(JPanel centerPanel) {
        // Restart button - Sử dụng createRoundedButton của lớp cha
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

        // Menu button - Sử dụng createRoundedButton của lớp cha
        JButton menuBtn = createRoundedButton("MAIN MENU", MENU_BG, BORDER_COLOR, CORNER_RADIUS);
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 55));
        menuBtn.setPreferredSize(new Dimension(300, 55));
        menuBtn.addActionListener(e -> {
            Game.getGame().changeState(Defs.STATE_MENU);
        });
        centerPanel.add(menuBtn);
    }
}
