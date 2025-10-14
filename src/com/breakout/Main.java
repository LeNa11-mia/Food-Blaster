package com.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import com.breakout.entities.Ball;
import com.breakout.entities.Paddle;
import com.breakout.entities.Brick;

/**
 * Main game class - Entry point for the applicationơ
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

    // Game state
    private boolean gameRunning = false;
    private boolean gameOver = false;
    private String currentDifficulty = "EASY";
    private int lives = 1; // Chỉ có 1 mạng
    private int score = 0;

    // Menu components
    private JFrame frame;ơ
    private JPanel menuPanel;
    private JButton easyBtn, mediumBtn, hardBtn, bossBtn, exitBtn;

    public Main() {
        initializeMenu();
    }

    private void initializeMenu() {
        frame = new JFrame("Food Blaster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        createMenuPanel();
        frame.setContentPane(menuPanel);
        frame.setVisible(true);
    }

    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBackground(Color.BLACK);

        // Title
        JLabel titleLabel = new JLabel("FOOD BLASTER", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 100, 150));
        buttonPanel.setBackground(Color.BLACK);

        // Create buttons với màu sắc khác nhau
        easyBtn = createMenuButton("EASY", Color.GREEN);
        mediumBtn = createMenuButton("MEDIUM", Color.YELLOW);
        hardBtn = createMenuButton("HARD", Color.ORANGE);
        bossBtn = createMenuButton("BOSS FIGHTS", Color.RED);
        exitBtn = createMenuButton("EXIT", Color.GRAY);

        // Add action listeners
        easyBtn.addActionListener(e -> startGame("EASY"));
        mediumBtn.addActionListener(e -> startGame("MEDIUM"));
        hardBtn.addActionListener(e -> startGame("HARD"));
        bossBtn.addActionListener(e -> startGame("BOSS"));
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(easyBtn);
        buttonPanel.add(mediumBtn);
        buttonPanel.add(hardBtn);
        buttonPanel.add(bossBtn);
        buttonPanel.add(exitBtn);

        menuPanel.add(buttonPanel, BorderLayout.CENTER);

        // Instructions
        JLabel instructionLabel = new JLabel("Use ← → or A D keys to move paddle", SwingConstants.CENTER);
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        menuPanel.add(instructionLabel, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hiệu ứng hover
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

    private void startGame(String difficulty) {
        currentDifficulty = difficulty;
        lives = 1; // Reset về 1 mạng
        score = 0;
        gameRunning = true;
        gameOver = false;

        // Khởi tạo game panel
        initializeGamePanel();
        frame.setContentPane(this);
        frame.revalidate();
        requestFocusInWindow(); // Để nhận sự kiện bàn phím

        // Bắt đầu game loop
        startGameLoop();
    }

    private void initializeGamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Tạo game objects dựa trên độ khó
        initializeGameObjects();

        lastTime = System.nanoTime();
    }

    private void initializeGameObjects() {
        // Tạo ball với tốc độ dựa trên độ khó
        double ballSpeed = getBallSpeedByDifficulty();
        ball = new Ball(WIDTH/2, HEIGHT/2);
        // Giả sử Ball class có method setSpeed
        // ball.setSpeed(ballSpeed);

        // Tạo paddle với kích thước dựa trên độ khó
        double paddleWidth = getPaddleWidthByDifficulty();
        paddle = new Paddle(WIDTH/2 - paddleWidth/2, HEIGHT - 50);
        // Giả sử Paddle class có method setWidth
        // paddle.setWidth(paddleWidth);

        // Tạo bricks với số lượng và độ bền dựa trên độ khó
        bricks = createBricksByDifficulty();
    }

    private double getBallSpeedByDifficulty() {
        switch (currentDifficulty) {
            case "EASY": return 200.0;
            case "MEDIUM": return 300.0;
            case "HARD": return 400.0;
            case "BOSS": return 350.0;
            default: return 200.0;
        }
    }

    private double getPaddleWidthByDifficulty() {
        switch (currentDifficulty) {
            case "EASY": return 120.0;
            case "MEDIUM": return 100.0;
            case "HARD": return 80.0;
            case "BOSS": return 90.0;
            default: return 100.0;
        }
    }

    private List<Brick> createBricksByDifficulty() {
        List<Brick> newBricks = new ArrayList<>();
        double brickWidth = WIDTH / 10.0;
        double brickHeight = 20;

        int rows = getBrickRowsByDifficulty();
        int cols = 10;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = col * brickWidth;
                double y = 50 + row * brickHeight;
                Brick brick = new Brick(x, y, brickWidth - 2, brickHeight - 2);

                // Set brick health based on difficulty
                if (currentDifficulty.equals("BOSS") && row == rows - 1) {
                    // brick.setHealth(3); // Boss bricks are tougher
                }

                newBricks.add(brick);
            }
        }

        return newBricks;
    }

    private int getBrickRowsByDifficulty() {
        switch (currentDifficulty) {
            case "EASY": return 4;
            case "MEDIUM": return 5;
            case "HARD": return 6;
            case "BOSS": return 5;
            default: return 5;
        }
    }

    private void startGameLoop() {
        new Thread(() -> {
            while (gameRunning) {
                update();
                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Kiểm tra game over
                if (lives <= 0) {
                    gameRunning = false;
                    showGameOver();
                }

                // Kiểm tra win condition
                if (checkWinCondition()) {
                    gameRunning = false;
                    showWinScreen();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all objects
        if (ball != null) ball.draw(g2d);
        if (paddle != null) paddle.draw(g2d);
        if (bricks != null) {
            for (Brick brick : bricks) {
                if (!brick.isDestroyed()) {
                    brick.draw(g2d);
                }
            }
        }

        // Display game info
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Difficulty: " + currentDifficulty, 10, 20);
        g2d.drawString("Lives: " + lives, 10, 40);
        g2d.drawString("Score: " + score, 10, 60);
        g2d.drawString("Press ← → or A D to move", 10, HEIGHT - 10);

        // Game over message
        if (gameOver) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            String gameOverText = "GAME OVER";
            int textWidth = g2d.getFontMetrics().stringWidth(gameOverText);
            g2d.drawString(gameOverText, (WIDTH - textWidth) / 2, HEIGHT / 2);

            g2d.setFont(new Font("Arial", Font.PLAIN, 24));
            String restartText = "Press R to restart or M for menu";
            int restartWidth = g2d.getFontMetrics().stringWidth(restartText);
            g2d.drawString(restartText, (WIDTH - restartWidth) / 2, HEIGHT / 2 + 40);
        }
    }

    public void update() {
        if (!gameRunning || gameOver) return;

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

        // Ball falls below - mất mạng
        if (ball.getY() > HEIGHT) {
            loseLife();
            if (lives > 0) {
                resetBall();
            }
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
                score += 10;
                break; // Only destroy one brick per collision
            }
        }

        repaint();
    }

    private void loseLife() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
        }
    }

    private void resetBall() {
        ball = new Ball(WIDTH/2, HEIGHT/2);
    }

    private boolean checkWinCondition() {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    private void showGameOver() {
        SwingUtilities.invokeLater(() -> {
            int option = JOptionPane.showOptionDialog(frame,
                    "Game Over! Your score: " + score + "\nDifficulty: " + currentDifficulty,
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Play Again", "Main Menu"},
                    "Play Again");

            if (option == 0) {
                // Play again với cùng độ khó
                startGame(currentDifficulty);
            } else {
                // Quay về menu chính
                showMainMenu();
            }
        });
    }

    private void showWinScreen() {
        SwingUtilities.invokeLater(() -> {
            int option = JOptionPane.showOptionDialog(frame,
                    "You Win! Your score: " + score + "\nDifficulty: " + currentDifficulty,
                    "Congratulations!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Next Level", "Main Menu"},
                    "Next Level");

            if (option == 0) {
                // Tăng độ khó hoặc chơi lại
                if (currentDifficulty.equals("EASY")) {
                    startGame("MEDIUM");
                } else if (currentDifficulty.equals("MEDIUM")) {
                    startGame("HARD");
                } else {
                    startGame("BOSS");
                }
            } else {
                showMainMenu();
            }
        });
    }

    private void showMainMenu() {
        frame.setContentPane(menuPanel);
        frame.revalidate();
        frame.repaint();
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
        if (gameOver) {
            if (key == KeyEvent.VK_R) {
                startGame(currentDifficulty);
            }
            if (key == KeyEvent.VK_M) {
                showMainMenu();
            }
        }
        if (key == KeyEvent.VK_ESCAPE) {
            showMainMenu();
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
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }
}
