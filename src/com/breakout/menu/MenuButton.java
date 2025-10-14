package com.breakout.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MoơuseAdapter;
import java.awt.event.MouseEvent;

public class MenuButton extends JButton {

    private Color hoverColor;
    private Color normalColor;

    public MenuButton(String text, Color hoverColor, Color normalColor) {
        super(text);
        hoverColor=this.hoverColor;
        this.normalColor=normalColor;

        setPreferredSize(new Dimension(200, 50));
        setBackground(normalColor);
        setForeground(hoverColor);
        setFont(new Font("Times New Roman", Font.BOLD, 14));
        setBorder(BorderFactory.createBevelBorder(10,20,10,20));
        setFocusPainted(false);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }
        });
    }

}
