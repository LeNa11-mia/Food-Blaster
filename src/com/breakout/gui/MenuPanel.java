package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.managers.SaveManager;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends GUIPanel {

    private Image backgroundImage;
    private JButton continueButton;

    public MenuPanel() {
        super(Color.decode("#F3CFC6"));

        // Load background image
        loadBackgroundImage();
        this.game = game;
        backgroundImage = new ImageIcon("src/com/breakout/resources.assets/mainMenu.png").getImage();

        setLayout(null); // Dùng absolute positioning để đặt chính xác vị trí

        // Tạo button panel và đặt ở phần màu hồng
        JPanel buttonPanel = createButtonPanel(game);
        buttonPanel.setBounds(150, 400, 300, 150); // x, y, width, height
        add(buttonPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

            // Vẽ background image full màn hình
            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
//            else {
//                g2d.setColor(Color.decode("#FFC0CB"));
//                g2d.fillRect(0, 0, getWidth(), getHeight());
//            }
//
//            // Vẽ chữ "Food Blaster" viết tay màu hồng ở phần trắng
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
//        } finally {
//            g2d.dispose();
//        }
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

        // Nút PLAY
        JButton playBtn = createRoundedButton("PLAY", Color.decode("#F8C8DC"));
        playBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_GAME_MODES));
        buttonPanel.add(playBtn);


        // Nếu có save → thêm CONTINUE ở giữa PLAY và EXIT
        if (hasSave) {
            continueButton = createRoundedButton("CONTINUE", Color.decode("#FFB6C1"));
            continueButton.addActionListener(e -> {
                if (Game.getGame().getGm() != null) {
                    Game.getGame().getGm().continueGame();
                }
                Game.getGame().changeState(Defs.STATE_PLAYING);
            });
            buttonPanel.add(continueButton);
        }

        // Create buttons with different colors
        JButton easyBtn = createButton("EASY", Color.decode("#F8C8DC"));
        addButton(easyBtn);
        JButton mediumBtn = createButton("MEDIUM", Color.decode("#FFC0CB"));
        addButton(mediumBtn);
        JButton hardBtn = createButton("HARD", Color.decode("#FAA0A0"));
        addButton(hardBtn);
        JButton bossBtn = createButton("BOSS FIGHTS", Color.decode("#F89880"));
        addButton(bossBtn);
        JButton exitBtn = createButton("EXIT", Color.decode("#D8BFD8"));
        addButton(exitBtn);

        // Add action listeners
        easyBtn.addActionListener(e -> {
            Game.getGame().getGm().startGame(Defs.LEVEL_EASY);
            Game.getGame().changeState(Defs.STATE_PLAYING);
        });
        mediumBtn.addActionListener(e -> {
            Game.getGame().getGm().startGame(Defs.LEVEL_MEDIUM);
            Game.getGame().changeState(Defs.STATE_PLAYING);
        });
        hardBtn.addActionListener(e -> {
            Game.getGame().getGm().startGame(Defs.LEVEL_HARD);
            Game.getGame().changeState(Defs.STATE_PLAYING);
        });
        bossBtn.addActionListener(e -> {
            Game.getGame().getGm().startGame(Defs.LEVEL_BOSS);
            Game.getGame().changeState(Defs.STATE_PLAYING);
        });
        JButton exitBtn = createRoundedButton("EXIT", Color.decode("#D8BFD8"));
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        return buttonPanel;
    }


    public void updateMenu() {
        removeAll(); // Xóa tất cả components cũ

        // Tạo lại button panel
        JPanel buttonPanel = createButtonPanel();
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

        button.setFont(new Font("Arial", Font.BOLD,20 ));
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
            continueButton.setEnabled(Game.getGame().getGm().canContinueGame());
        }
    }

    // Thêm method để cập nhật trạng thái menu
    public void refreshMenu() {
        updateMenu();
    }
}
