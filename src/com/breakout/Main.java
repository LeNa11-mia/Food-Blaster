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

    private GameManager gm;
    private Renderer renderer;

    // Controls
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    // Timing
    private long lastTime;

    // Game state
    private boolean gameStarted = false;
    private Thread gameThread;
    private String currentDifficulty = "EASY";
    private boolean showingGameOver = false;
    private boolean showingWin = false;

    // Menu components
    private JFrame frame;
    private JPanel menuPanel;
    private JPanel gameOverPanel;
    private JPanel winPanel;
    private JButton easyBtn, mediumBtn, hardBtn, bossBtn, exitBtn;

    public Main(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);
    }

    public JPanel createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBackground(Color.decode("#F3CFC6"));

        // Title
        JLabel titleLabel = new JLabel("FOOD BLASTER", SwingConstants.CENTER);
        titleLabel.setForeground(Color.decode("#C9A9A6"));
        titleLabel.setFont(new Font("Courier", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 100, 150));
        buttonPanel.setBackground(Color.decode("#E0BFB8"));

        // Create buttons with different colors
        easyBtn = createMenuButton("EASY", Color.decode("#F8C8DC"));
        mediumBtn = createMenuButton("MEDIUM", Color.decode("#FFC0CB"));
        hardBtn = createMenuButton("HARD", Color.decode("#FAA0A0"));
        bossBtn = createMenuButton("BOSS FIGHTS", Color.decode("#F89880"));
        exitBtn = createMenuButton("EXIT", Color.decode("#D8BFD8"));

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

        return menuPanel;
    }

    private JPanel createGameOverPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#722F37")); // Cherry wine red

        // Title
        JLabel titleLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        titleLabel.setForeground(Color.decode("#FFB6C1"));
        titleLabel.setFont(new Font("Courier", Font.BOLD, 48));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 30, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.decode("#722F37"));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));

        // Score display
        JLabel scoreLabel = new JLabel("Score: " + (gm != null ? gm.getScore() : 0));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel difficultyLabel = new JLabel("Difficulty: " + currentDifficulty);
        difficultyLabel.setForeground(Color.WHITE);
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(difficultyLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons
        JButton restartBtn = createMenuButton("RESTART", Color.decode("#8B0000")); // Dark red
        JButton menuBtn = createMenuButton("MAIN MENU", Color.decode("#A0522D")); // Sienna

        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        restartBtn.setMaximumSize(new Dimension(300, 50));
        menuBtn.setMaximumSize(new Dimension(300, 50));

        // Click listeners
        restartBtn.addActionListener(e -> startGame(currentDifficulty));
        menuBtn.addActionListener(e -> returnToMenu());

        centerPanel.add(restartBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(menuBtn);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Instructions that suggest what player can do after game over
        JLabel instructionLabel = new JLabel("Click buttons to continue", SwingConstants.CENTER);
        instructionLabel.setForeground(Color.decode("#FFB6C1"));
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(instructionLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createWinPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#2D5016")); // Dark green

        // Title
        JLabel titleLabel = new JLabel("YOU WIN!", SwingConstants.CENTER);
        titleLabel.setForeground(Color.decode("#90EE90"));
        titleLabel.setFont(new Font("Courier", Font.BOLD, 48));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 30, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.decode("#2D5016"));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));

        // Score display
        JLabel scoreLabel = new JLabel("Score: " + (gm != null ? gm.getScore() : 0));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel difficultyLabel = new JLabel("Difficulty: " + currentDifficulty);
        difficultyLabel.setForeground(Color.WHITE);
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(difficultyLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons
        String nextLevel = getNextDifficulty();
        if (nextLevel != null) {
            JButton nextBtn = createMenuButton("NEXT LEVEL", Color.decode("#228B22")); // Forest green
            nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            nextBtn.setMaximumSize(new Dimension(300, 50));
            nextBtn.addActionListener(e -> startGame(nextLevel));
            centerPanel.add(nextBtn);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JButton restartBtn = createMenuButton("RESTART", Color.decode("#6B8E23")); // Olive drab
        JButton menuBtn = createMenuButton("MAIN MENU", Color.decode("#8B7355")); // Burlywood

        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        restartBtn.setMaximumSize(new Dimension(300, 50));
        menuBtn.setMaximumSize(new Dimension(300, 50));

        // Add click listeners
        restartBtn.addActionListener(e -> startGame(currentDifficulty));
        menuBtn.addActionListener(e -> returnToMenu());

        centerPanel.add(restartBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(menuBtn);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Instructions at bottom
        JLabel instructionLabel = new JLabel("Click buttons to continue", SwingConstants.CENTER);
        instructionLabel.setForeground(Color.decode("#90EE90"));
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(instructionLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createMenuButton(String text, Color color) {
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

    private void startGame(String difficulty) {
        currentDifficulty = difficulty;
        showingGameOver = false;
        showingWin = false;

        // Initialize game manager and renderer
        gm = new GameManager();
        renderer = new Renderer(gm);

        gm.startGame();

        lastTime = System.nanoTime();
        gameStarted = true;

        // Switch from menu to game
        frame.getContentPane().removeAll();
        frame.add(this);
        frame.add(renderer);
        frame.revalidate();
        frame.repaint();
        requestFocusInWindow();

        // Start game loop
        startGameLoop();
    }

    private void startGameLoop() {
        if (gameThread != null && gameThread.isAlive()) {
            return; // Already running, preempt multiple loops
        }

        // ( explain for co-members: The argument is a lambda expression (() -> { ... })
        // which defines the code that the thread will run.
        gameThread = new Thread(() -> {
            while (gameStarted) {
                update();
                // this try-catch handles the rare case that the thread is interrupted during sleep
                try {
                    // ~60 FPS, pauses the loop for about 16 milliseconds.
                    //1000 ms / 16 ms ≈ 60 updates per second, ~ 60 frames per second,
                    // keeps the loop from running too fast and burning CPU unnecessarily.
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    public void update() {
        if (!gameStarted || gm == null) return;

        // Calculate deltaTime
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        gm.update(deltaTime, leftPressed, rightPressed);
        renderer.repaint();

        // Check game over
        if (gm.isGameOver() && !showingGameOver) {
            showingGameOver = true;
            gameStarted = false;
            showGameOverScreen();
        }

        // Check win condition
        if (gm.isWin() && !showingWin) {
            showingWin = true;
            gameStarted = false;
            showWinScreen();
        }
    }

    private void showGameOverScreen() {
        SwingUtilities.invokeLater(() -> {
            gameOverPanel = createGameOverPanel();
            frame.getContentPane().removeAll();
            frame.add(gameOverPanel);
            frame.revalidate();
            frame.repaint();
            gameOverPanel.setFocusable(true);
            gameOverPanel.requestFocusInWindow();
            gameOverPanel.addKeyListener(this);
        });
    }

    private void showWinScreen() {
        SwingUtilities.invokeLater(() -> {
            winPanel = createWinPanel();
            frame.getContentPane().removeAll();
            frame.add(winPanel);
            frame.revalidate();
            frame.repaint();
            winPanel.setFocusable(true);
            winPanel.requestFocusInWindow();
            winPanel.addKeyListener(this);
        });
    }

    private String getNextDifficulty() {
        switch (currentDifficulty) {
            case "EASY": return "MEDIUM";
            case "MEDIUM": return "HARD";
            case "HARD": return "BOSS";
            case "BOSS": return null;
            default: return null;
        }
    }

    public void returnToMenu() {
        gameStarted = false;
        showingGameOver = false;
        showingWin = false;

        frame.getContentPane().removeAll();

        createMenuPanel();
        frame.setContentPane(menuPanel);
        frame.setVisible(true);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Only handle game controls when actually playing
        if (!showingGameOver && !showingWin && gameStarted) {
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                rightPressed = true;
            }
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
        // Schedules code to run later on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Food Blaster");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            Main game = new Main(frame);

            // Start with menu
            JPanel menuPanel = game.createMenuPanel();
            frame.setContentPane(menuPanel);
            frame.setVisible(true);
        });
    }
}