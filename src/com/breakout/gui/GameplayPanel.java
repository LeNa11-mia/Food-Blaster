package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.GameConfig;
import com.breakout.core.GameObject;
import com.breakout.entities.bricks.Brick;
import com.breakout.entities.items.Item;

import java.awt.*;

public class GameplayPanel extends GUIPanel {

    public GameplayPanel() {
        setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        setBackground(new Color(255, 214, 214));
        backgroundImage = GameConfig.GAMEPLAY_BACKGROUND;
    }

    private void draw(GameObject obj, Graphics2D g2d) {
        if (obj.getSprite() != null && obj.getSprite().getImage() != null) {
            g2d.drawImage(obj.getSprite().getImage(),
                    (int) obj.getX(),
                    (int) obj.getY(),
                    (int) obj.getWidth(),
                    (int) obj.getHeight(),
                    null);
        }
    }

    public void drawObjects(Graphics2D g2d) {
        var gm = Game.getGame().getGm();

        // Vẽ bóng
        draw(gm.getBall(), g2d);

        // Vẽ thanh paddle
        draw(gm.getPaddle(), g2d);

        // Vẽ gạch
        for (Brick brick : gm.getBricks()) {
            if (!brick.isDestroyed()) {
                draw(brick, g2d);
            }
        }

        // Vẽ item
        for (Item item : gm.getActiveItems()) {
            draw(item, g2d);
        }
    }

    public void drawInstructions(Graphics2D g2d) {
        g2d.setColor(Color.PINK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Press ← → or A D to move", 75, 35);
    }

    // ✅ Vẽ điểm góc phải trên
    public void drawScore(Graphics2D g2d) {
        var gm = Game.getGame().getGm();

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 22));

        String scoreText = "Score: " + gm.getScore();
        int textWidth = g2d.getFontMetrics().stringWidth(scoreText);

        g2d.drawString(scoreText, GameConfig.SCREEN_WIDTH - textWidth - 50, 35);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ background image full màn hình
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Draw all objects
        drawObjects(g2d);

        // Display UI text
        drawInstructions(g2d);
        drawScore(g2d);

        drawItemMessage(g2d);
    }

    private void drawItemMessage(Graphics2D g2d) {
        String message = Game.getGame().getGm().getScreenMessage();

        if (message != null) {
            // Cấu hình font và màu
            Font font = new Font("Arial", Font.PLAIN, 12);
            g2d.setFont(font);

            // Tính toán vị trí để căn giữa tin nhắn
            FontMetrics fm = g2d.getFontMetrics();
            int x = (GameConfig.SCREEN_WIDTH - fm.stringWidth(message)) / 2;
            int y = 25;

            // Vẽ hiệu ứng đổ bóng/viền (tùy chọn)
//            g2d.setColor(new Color(0, 0, 0, 150)); // Shadow
//            g2d.drawString(message, x + 2, y + 2);

            // Vẽ tin nhắn chính
            g2d.setColor(Color.BLACK);
            g2d.drawString(message, x, y);
        }
    }
}
