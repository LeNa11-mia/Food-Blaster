package com.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.breakout.managers.GameManager;
import com.breakout.managers.Renderer;

/**
 * Main game class - Entry point for the application
 */
public class Main extends JComponent implements KeyListener {
    // Screen dimensions
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GameManager gm = new GameManager();
    private Renderer renderer = new Renderer(gm);

    // Controls
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    
    // Timing
    private long lastTime;
    
    public Main() {
        gm.startGame();
        lastTime = System.nanoTime();
        setFocusable(true);
        addKeyListener(this);
    }

    public void update() {
        // Calculate deltaTime
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;
        gm.update(deltaTime, leftPressed, rightPressed);
        renderer.repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Breakout");
        Main game = new Main();

        frame.add(game);
        frame.add(game.renderer);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Simple game loop
        new Thread(() -> {
            while (true) {
                game.update();
                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}