package com.breakout.gui;

import com.breakout.Game;
import com.breakout.managers.GameState;
import com.breakout.managers.SaveManager;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends GUIPanel {

    private Image backgroundImage;
    private JButton continueButton;
    private Game game;

    public MenuPanel(Game game) {
        super("#F3CFC6");
        this.game = game;

        // Load background image
        loadBackgroundImage();

        setLayout(null); // Dùng absolute positioning để đặt chính xác vị trí

        // Tạo button panel và đặt ở phần màu hồng
        JPanel buttonPanel = createButtonPanel(game);
        buttonPanel.setBounds(150, 350, 300, 250); // x, y, width, height
        add(buttonPanel);
    }

    private void loadBackgroundImage() {
        try {
            java.net.URL imgURL = getClass().getResource("/com/breakout/resources.assets/mainMenu.png");

            if (imgURL == null) {
                imgURL = getClass().getResource("/resources.assets/mainMenu.png");
            }

            if (imgURL == null) {
                imgURL = getClass().getResource("/mainMenu.png");
            }

            if (imgURL != null) {
                backgroundImage = new ImageIcon(imgURL).getImage();
                System.out.println("✓ Ảnh load thành công từ: " + imgURL);
            } else {
                System.out.println("✗ Không tìm thấy ảnh trong classpath");
                String[] filePaths = {
                        "src/com/breakout/resources.assets/mainMenu.png",
                        "./src/com/breakout/resources.assets/mainMenu.png"
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

        try {
            // Vẽ background image full màn hình
            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g2d.setColor(Color.decode("#FFC0CB"));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            // Vẽ chữ "Food Blaster" viết tay màu hồng ở phần trắng
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Font font;
            try {
                // Thử dùng font viết tay
                font = new Font("Brush Script MT", Font.BOLD, 72);
            } catch (Exception e) {
                font = new Font("Segoe Script", Font.BOLD, 72);
            }

            g2d.setFont(font);
            g2d.setColor(new Color(255, 105, 180)); // Màu hồng pastel

            String text = "Food Blaster";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int x = (getWidth() - textWidth) / 2;
            int y = 120; // Đặt ở phần màu trắng (khoảng 150px từ trên)

            g2d.drawString(text, x, y);

            //drawSaveInfo(g2d);
        } finally {
            g2d.dispose();
        }
    }

//    private void drawSaveInfo(Graphics2D g2d) {
//        String saveInfo = SaveManager.getSaveInfo();
//        if (saveInfo != null) {
//            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
//            g2d.setColor(Color.WHITE);
//
//            // Vẽ nền cho text
//            g2d.setColor(new Color(0, 0, 0, 150)); // Màu đen trong suốt
//            int textWidth = g2d.getFontMetrics().stringWidth("Saved: " + saveInfo);
//            int x = (getWidth() - textWidth) / 2;
//            int y = 180;
//            g2d.fillRoundRect(x - 10, y - 15, textWidth + 20, 25, 10, 10);
//
//            // Vẽ text
//            g2d.setColor(Color.WHITE);
//            g2d.drawString("Saved: " + saveInfo, x, y);
//        }
//    }

    private JPanel createButtonPanel(Game game) {
        JPanel buttonPanel = new JPanel();

        boolean hasSave = SaveManager.saveExists();

        // Nếu có save → 3 nút, nếu không → 2 nút
        if (hasSave) {
            buttonPanel.setLayout(new GridLayout(3, 1, 15, 20));
        } else {
            buttonPanel.setLayout(new GridLayout(2, 1, 15, 20));
        }

        buttonPanel.setOpaque(false);

        // Nút PLAY
        JButton playBtn = createRoundedButton("PLAY", Color.decode("#F8C8DC"));
        playBtn.addActionListener(e -> game.changeState(GameState.GAME_MODES));
        buttonPanel.add(playBtn);

        // Nếu có save → thêm CONTINUE ở giữa PLAY và EXIT
        if (hasSave) {
            continueButton = createRoundedButton("CONTINUE", Color.decode("#FFB6C1"));
            continueButton.addActionListener(e -> {
                if (game.getGm() != null) {
                    game.getGm().continueGame();
                }
                game.changeState(GameState.PLAYING);
            });
            buttonPanel.add(continueButton);
        }

        // Nút EXIT
        JButton exitBtn = createRoundedButton("EXIT", Color.decode("#D8BFD8"));
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        return buttonPanel;
    }


    public void updateMenu() {
        removeAll(); // Xóa tất cả components cũ

        // Load lại background
        loadBackgroundImage();

        // Tạo lại button panel
        JPanel buttonPanel = createButtonPanel(game);
        buttonPanel.setBounds(150, 350, 300, 250);
        add(buttonPanel);

        revalidate();
        repaint();
    }

    private JButton createRoundedButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                try {
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Vẽ nền bo góc với màu phù hợp
                    Color currentColor;
                    if (getModel().isPressed()) {
                        // Khi nhấn: màu đậm hơn một chút (không chói)
                        int r = Math.max(0, bgColor.getRed() - 30);
                        int g1 = Math.max(0, bgColor.getGreen() - 30);
                        int b = Math.max(0, bgColor.getBlue() - 30);
                        currentColor = new Color(r, g1, b);
                    } else if (getModel().isRollover()) {
                        // Khi hover: sáng nhẹ hơn một chút
                        int r = Math.min(255, bgColor.getRed() + 20);
                        int g1 = Math.min(255, bgColor.getGreen() + 20);
                        int b = Math.min(255, bgColor.getBlue() + 20);
                        currentColor = new Color(r, g1, b);
                    } else {
                        currentColor = bgColor;
                    }

                    g2d.setColor(currentColor);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                    // Vẽ viền hồng
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
                } finally {
                    g2d.dispose();
                }
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 50));

        return button;
    }

    // Thêm method để enable/disable nút continue nếu cần
    public void setContinueButtonEnabled(boolean enabled) {
        if (continueButton != null) {
            continueButton.setEnabled(game.getGm().canContinueGame());
        }
    }

    // Thêm method để cập nhật trạng thái menu
    public void refreshMenu() {
        updateMenu();
    }
}
