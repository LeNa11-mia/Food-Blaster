package com.breakout.gui;

import com.breakout.Game;
import com.breakout.managers.GameState;

import javax.swing.*;
import java.awt.*;

public class GameModesPanel extends GUIPanel {

    private Image backgroundImage;

    public GameModesPanel(Game game) {
        super("#F3CFC6");

        // Load background image
        loadBackgroundImage();

        setLayout(null); // Dùng absolute positioning

        // Tạo panel chứa các nút
        JPanel modesPanel = createModesPanel(game);
        modesPanel.setBounds(125, 280, 350, 320); // x, y, width, height
        add(modesPanel);
    }

    private void loadBackgroundImage() {
        try {
            java.net.URL imgURL = getClass().getResource("/com/breakout/resources.assets/select.png");

            if (imgURL == null) {
                imgURL = getClass().getResource("/resources.assets/select.png");
            }

            if (imgURL == null) {
                imgURL = getClass().getResource("/select.png");
            }

            if (imgURL != null) {
                backgroundImage = new ImageIcon(imgURL).getImage();
                System.out.println("✓ GameModesPanel: Ảnh load thành công từ: " + imgURL);
            } else {
                System.out.println("✗ Không tìm thấy ảnh trong classpath");
                String[] filePaths = {
                        "src/com/breakout/resources.assets/select.png",
                        "./src/com/breakout/resources.assets/select.png"
                };

                for (String path : filePaths) {
                    java.io.File file = new java.io.File(path);
                    if (file.exists()) {
                        backgroundImage = new ImageIcon(file.getAbsolutePath()).getImage();
                        System.out.println("✓ Ảnh load từ file: " + file.getAbsolutePath());
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

        // Vẽ background image full màn hình
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
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

    private JPanel createModesPanel(Game game) {
        JPanel modesPanel = new JPanel();
        modesPanel.setLayout(new GridLayout(5, 1, 0, 15)); // 5 nút, cách nhau 15px
        modesPanel.setOpaque(false);

        // Tạo các nút nhỏ gọn với màu sắc khác nhau
        JButton easyBtn = createRoundedButton("EASY", Color.decode("#F8C8DC"), game, "EASY");
        JButton mediumBtn = createRoundedButton("MEDIUM", Color.decode("#FFC0CB"), game, "MEDIUM");
        JButton hardBtn = createRoundedButton("HARD", Color.decode("#FAA0A0"), game, "HARD");
        JButton bossBtn = createRoundedButton("BOSS FIGHTS", Color.decode("#F89880"), game, "BOSS");
        JButton backBtn = createRoundedButton("← BACK", Color.decode("#D8BFD8"), game, "BACK");

        modesPanel.add(easyBtn);
        modesPanel.add(mediumBtn);
        modesPanel.add(hardBtn);
        modesPanel.add(bossBtn);
        modesPanel.add(backBtn);

        return modesPanel;
    }

    private JButton createRoundedButton(String text, Color bgColor, Game game, String mode) {
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

                g2d.setColor(currentColor);
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

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(300, 50)); // Nút nhỏ gọn hơn

        // Add action listeners
        button.addActionListener(e -> {
            if (mode.equals("BACK")) {
                game.changeState(GameState.MENU);
            } else {
                game.getGm().startGame(mode);
                game.changeState(GameState.PLAYING);
            }
        });

        return button;
    }
}
