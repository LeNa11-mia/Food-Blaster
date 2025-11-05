package com.breakout.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    public static ImageIcon resize(ImageIcon src, int newWidth, int newHeight) {
        if (src == null || src.getImage() == null) {
            System.out.println("❌ ImageUtils: Source image null");
            return null;
        }

        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resized.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src.getImage(), 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return new ImageIcon(resized);
    }
}
