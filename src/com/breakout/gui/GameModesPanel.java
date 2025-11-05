package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.Level;

import javax.swing.*;
import java.awt.*;

public class GameModesPanel extends GUIPanel {

    private JButton[] buttons;
    private static final Color BACK_BG = Color.decode("#D8BFD8"); // Thistle
    private static final Color LEVEL_BG = Color.decode("#FFC0CB"); // Pink
    private static final Color BORDER_COLOR = Color.PINK; // Viền
    private static final int CORNER_RADIUS = 20;

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

        // Sử dụng phương thức chung từ GUIPanel
        Font font = getHandwrittenFont(Font.BOLD, 60);

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

        // Nút BACK sử dụng LevelButton
        LevelButton backBtn = new LevelButton("← BACK", BACK_BG, Defs.GO_BACK);
        backBtn.setFont(new Font("Arial", Font.BOLD, 20)); // Font nhỏ hơn cho nút BACK
        bottomPanel.add(backBtn);

        for (int i = 0; i < GameConfig.TOTAL_LEVELS; i++) {
            // Nút LEVEL sử dụng LevelButton
            buttons[i] = new LevelButton(Integer.toString(i + 1), LEVEL_BG, i + 1);
            levelGrid.add(buttons[i]);
        }

        modesPanel.add(levelGrid, BorderLayout.CENTER);
        modesPanel.add(bottomPanel, BorderLayout.SOUTH);

        return modesPanel;
    }

    private class LevelButton extends JButton {
        private final Color baseColor;
        private final int levelMode;

        public LevelButton(String text, Color bgColor, int mode) {
            super(text);
            this.baseColor = bgColor;
            this.levelMode = mode;
            setupButton(text);
            addActionListener(e -> handleAction());
        }

        private void setupButton(String text) {
            setFont(new Font("Arial", Font.BOLD, 24));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(300, 50));
        }

        private void handleAction() {
            if (levelMode == Defs.GO_BACK) {
                Game.getGame().changeState(Defs.STATE_MENU);
            } else if (Level.isLevelUnlocked(levelMode)) {
                Game.getGame().getGm().startGame(levelMode);
                Game.getGame().changeState(Defs.STATE_PLAYING);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            try {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color actualBgColor = baseColor;
                boolean isLocked = levelMode != Defs.GO_BACK && !Level.isLevelUnlocked(levelMode);

                if (isLocked) {
                    actualBgColor = Color.LIGHT_GRAY; // Level chưa mở khóa
                } else {
                    // Hiệu ứng hover/press (tương tự logic trong GUIPanel.createRoundedButton)
                    if (getModel().isPressed()) {
                        int r = Math.max(0, baseColor.getRed() - 30);
                        int g1 = Math.max(0, baseColor.getGreen() - 30);
                        int b = Math.max(0, baseColor.getBlue() - 30);
                        actualBgColor = new Color(r, g1, b);
                    } else if (getModel().isRollover()) {
                        int r = Math.min(255, baseColor.getRed() + 20);
                        int g1 = Math.min(255, baseColor.getGreen() + 20);
                        int b = Math.min(255, baseColor.getBlue() + 20);
                        actualBgColor = new Color(r, g1, b);
                    }
                }

                // Vẽ nền
                g2d.setColor(actualBgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);

                // Vẽ viền
                g2d.setColor(BORDER_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, CORNER_RADIUS, CORNER_RADIUS);

                // Vẽ chữ
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
    }
}
