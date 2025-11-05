package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.Level;

import javax.swing.*;
import java.awt.*;

public class GameModesPanel extends GUIPanel {

    private JButton[] buttons;

    public GameModesPanel() {
        super(Color.decode("#F3CFC6"));
        backgroundImage = GameConfig.GAMEMODES_BACKGROUND;

        setLayout(null); // Dùng absolute positioning

        // Các nút chọn level
        buttons = new JButton[GameConfig.TOTAL_LEVELS];

        // Tạo panel chứa các nút
        JPanel modesPanel = createModesPanel();
        modesPanel.setBounds(125, 280, 350, 320); // x, y, width, height
        add(modesPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Vẽ background image full màn hình
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(Color.decode("#FFC0CB"));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Vẽ chữ "Select Level" viết tay màu hồng ở phần trắng
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font font;
        try {
            font = new Font("Brush Script MT", Font.BOLD, 60);
        } catch (Exception e) {
            font = new Font("Segoe Script", Font.BOLD, 60);
        }

        g2d.setFont(font);
        g2d.setColor(new Color(255, 105, 180)); // Màu hồng pastel

        String text = "Select Level";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 150;

        g2d.drawString(text, x, y);

        g2d.dispose();
    }

    private JPanel createModesPanel() {
        JPanel modesPanel = new JPanel();
        modesPanel.setLayout(new BorderLayout()); // Các nút chọn level ở trung tâm, nút BACK ở dưới cùng
        modesPanel.setOpaque(false);

        JPanel levelGrid = new JPanel();
        levelGrid.setLayout(new GridLayout(2, 3, 25, 25));
        // Các nút chọn level sắp xếp dạng lưới, cách nhau 25 pixel
        levelGrid.setOpaque(false);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

        modesPanel.setOpaque(false);

        // Tạo các nút nhỏ gọn với màu sắc khác nhau
        JButton backBtn = createRoundedButton("← BACK", Color.decode("#D8BFD8"), Defs.GO_BACK);
        bottomPanel.add(backBtn);

        for (int i = 0; i < GameConfig.TOTAL_LEVELS; i++) {
            buttons[i] = createRoundedButton(Integer.toString(i + 1), Color.decode("#FFC0CB"), i + 1);
            levelGrid.add(buttons[i]);
        }

        modesPanel.add(levelGrid, BorderLayout.CENTER);
        modesPanel.add(bottomPanel, BorderLayout.SOUTH);

        return modesPanel;
    }

    private JButton createRoundedButton(String text, Color bgColor, int mode) {
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
                    int r = Math.min(255, bgColor.getRed() + 20);
                    int g1 = Math.min(255, bgColor.getGreen() + 20);
                    int b = Math.min(255, bgColor.getBlue() + 20);
                    currentColor = new Color(r, g1, b);
                } else {
                    currentColor = bgColor;
                }

                if (mode != Defs.GO_BACK && !Level.isLevelUnlocked(mode)) {
                    g2d.setColor(Color.LIGHT_GRAY); // Level chưa mở khóa có màu xám
                } else {
                    g2d.setColor(currentColor);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Viền hồng
                g2d.setColor(Color.PINK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

                // Vẽ chữ
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;
                g2d.drawString(getText(), x, y);

                g2d.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(300, 50)); // Nút nhỏ gọn hơn

        // Add action listeners
        button.addActionListener(e -> {
            if (mode == Defs.GO_BACK) {
                Game.getGame().changeState(Defs.STATE_MENU);
            }
            else if (Level.isLevelUnlocked(mode)) { // Kiểm tra xem level đã được mở khóa chưa
            Game.getGame().getGm().startGame(mode);
            Game.getGame().changeState(Defs.STATE_PLAYING);
        }
        });

        return button;
    }
}
