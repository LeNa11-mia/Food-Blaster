package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.SaveManager;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends GUIPanel {

    private JButton continueButton;
    private static final Color BORDER_COLOR = Color.PINK; // Màu viền thống nhất
    private static final int CORNER_RADIUS = 20;

    public MenuPanel() {
        super(Color.decode("#F3CFC6"));

        // Load background image
        backgroundImage = GameConfig.MENU_BACKGROUND;

        setLayout(null); // Dùng absolute positioning để đặt chính xác vị trí

        // Tạo button panel và đặt ở phần màu hồng
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(150, 400, 300, 150); // x, y, width, height
        add(buttonPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Vẽ background image full màn hình
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Vẽ chữ "Food Blaster" viết tay màu hồng ở phần trắng
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Sử dụng phương thức chung từ GUIPanel
        Font font = getHandwrittenFont(Font.BOLD, 72);

        g2d.setFont(font);
        g2d.setColor(new Color(255, 105, 180)); // Màu hồng pastel

        String text = "Food Blaster";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 120; // Đặt ở phần màu trắng (khoảng 150px từ trên)

        g2d.drawString(text, x, y);

        g2d.dispose();
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        boolean hasSave = SaveManager.saveExists();

        // Nếu có save → 3 nút, nếu không → 2 nút
        if (hasSave) {
            buttonPanel.setLayout(new GridLayout(3, 1, 15, 20));
        } else {
            buttonPanel.setLayout(new GridLayout(2, 1, 15, 20));
        }

        buttonPanel.setOpaque(false);

        JButton playBtn = createRoundedButton("PLAY", Color.decode("#F8C8DC"), BORDER_COLOR, CORNER_RADIUS);
        playBtn.addActionListener(e -> {
            Game.getGame().changeState(Defs.STATE_GAME_MODES);
        });
        buttonPanel.add(playBtn);

        // Nếu có save → thêm CONTINUE ở giữa PLAY và EXIT
        if (hasSave) {
            continueButton = createRoundedButton("CONTINUE", Color.decode("#FFB6C1"), BORDER_COLOR, CORNER_RADIUS);
            continueButton.addActionListener(e -> {
                // Sử dụng phương thức startContinueGame() từ Game class
                Game.getGame().startContinueGame();
            });
            buttonPanel.add(continueButton);
        }

        JButton exitBtn = createRoundedButton("EXIT", Color.decode("#D8BFD8"), BORDER_COLOR, CORNER_RADIUS);
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        return buttonPanel;
    }


    public void updateMenu() {
        removeAll(); // Xóa tất cả components cũ

        // Tạo lại button panel
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(150, 400, 300, 150);
        add(buttonPanel);

        revalidate();
        repaint();
    }

    // Thêm method để enable/disable nút continue nếu cần
    public void setContinueButtonEnabled(boolean enabled) {
        if (continueButton != null) {
            continueButton.setEnabled(Game.getGame().getGm().canContinueGame());
        }
    }

    // Thêm method để cập nhật trạng thái menu
    public void refreshMenu() {
        updateMenu();
    }
}
