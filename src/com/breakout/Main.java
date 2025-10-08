package com.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import com.breakout.entities.Ball;
import com.breakout.entities.Paddle;
import com.breakout.entities.Brick;
import com.breakout.core.GameObject;
/**
 * Main game class - Entry point for the application
 */
public class Main extends JPanel implements KeyListener {
    // Screen dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    // Game objects
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    
    // Controls
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    
    // Timing
    private long lastTime;
    
    public Main() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        // Create game objects
        ball = new Ball(WIDTH/2, HEIGHT/2);
        paddle = new Paddle(WIDTH/2 - 50, HEIGHT - 50);
        
        // Create bricks (5 rows x 10 columns)
        bricks = new ArrayList<>();
        double brickWidth = WIDTH / 10.0;
        double brickHeight = 20;
        
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                double x = col * brickWidth;
                double y = 50 + row * brickHeight;
                bricks.add(new Brick(x, y, brickWidth - 2, brickHeight - 2));
            }
        }
        
        lastTime = System.nanoTime();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw all objects
        ball.draw(g2d);
        paddle.draw(g2d);
        for (Brick brick : bricks) {
            brick.draw(g2d);
        }
        
        // Display instructions
        g2d.setColor(Color.WHITE);
        g2d.drawString("Press ← → or A D to move", 10, 20);
    }
    
    public void update() {
        // Calculate deltaTime
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;
        
        // Update ball
        ball.update(deltaTime);
        
        // Control paddle
        if (leftPressed) {
            paddle.moveLeft(deltaTime, WIDTH);
        }
        if (rightPressed) {
            paddle.moveRight(deltaTime, WIDTH);
        }
        
        // Ball hits left/right walls
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= WIDTH) {
            ball.bounceX();
        }
        
        // Ball hits top wall
        if (ball.getY() <= 0) {
            ball.bounceY();
        }
        
        // Ball falls below - reset
        if (ball.getY() > HEIGHT) {
            ball = new Ball(WIDTH/2, HEIGHT/2);
        }
        
        // Collision with paddle
        if (ball.intersects(paddle) && ball.getVy() > 0) {
            ball.bounceY();
        }
        
        // Collision with bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && ball.intersects(brick)) {
                brick.hit();
                ball.bounceY();
                break; // Only destroy one brick per collision
            }
        }
        
        repaint();
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