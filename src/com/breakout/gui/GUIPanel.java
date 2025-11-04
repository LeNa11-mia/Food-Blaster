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
}
