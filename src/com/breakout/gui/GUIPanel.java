package com.breakout.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public abstract class GUIPanel extends JPanel {
    protected ImageIcon backgroundImage;
    public static Map<JButton, Color> originalColors = new HashMap<>();

    public void addButton(JButton button) {
        originalColors.put(button, button.getBackground());
    }

    public GUIPanel() {}

    public GUIPanel(Color backgroundColor) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
    }

    protected JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    protected JLabel createLabel(String text, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    protected JLabel createBorderedLabel(String text, Color color, Font font, Border border) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(font);
        label.setBorder(border);
        return label;
    }

    protected JButton createRoundedButton(String text, Color bgColor, Color borderColor, int cornerRadius) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                try {
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Màu nền với hiệu ứng hover/press
                    Color currentColor;
                    if (getModel().isPressed()) {
                        int r = Math.max(0, bgColor.getRed() - 30);
                        int g1 = Math.max(0, bgColor.getGreen() - 30);
                        int b = Math.max(0, bgColor.getBlue() - 30);
                        currentColor = new Color(r, g1, b);
                    } else if (getModel().isRollover()) {
                        int r = Math.min(255, bgColor.getRed() + 25);
                        int g1 = Math.min(255, bgColor.getGreen() + 25);
                        int b = Math.min(255, bgColor.getBlue() + 25);
                        currentColor = new Color(r, g1, b);
                    } else {
                        currentColor = bgColor;
                    }

                    g2d.setColor(currentColor);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

                    // Viền
                    g2d.setColor(borderColor);
                    g2d.setStroke(new BasicStroke(2)); // Độ dày viền mặc định 2px
                    g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, cornerRadius, cornerRadius);

                    // Vẽ chữ
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(getText());
                    int textHeight = fm.getAscent();
                    int x = (getWidth() - textWidth) / 2;
                    int y = (getHeight() + textHeight) / 2 - 3;
                    g2d.drawString(getText(), x, y);
                } finally {
                    g2d.dispose();
                }
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

    protected Font getHandwrittenFont(int style, int size) {
        try {
            return new Font("Brush Script MT", style, size);
        } catch (Exception e) {
            return new Font("Segoe Script", style, size);
        }
    }
}
