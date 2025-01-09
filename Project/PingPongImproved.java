import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PingPongImproved extends JPanel implements Runnable, KeyListener {
    private static final int WIDTH = 800, HEIGHT = 600;
    private static final int ROCKET_WIDTH = 15, ROCKET_HEIGHT = 100, BALL_SIZE = 20;
    private int ROCKET_SPEED = 5, INITIAL_BALL_SPEED = 8;
    private static final Font LARGE_FONT = new Font("Verdana", Font.BOLD, 36);
    private static final Font MEDIUM_FONT = new Font("Verdana", Font.PLAIN, 24);
    private static final Font SMALL_FONT = new Font("Verdana", Font.PLAIN, 16);

    private Rectangle leftRocket, rightRocket, ball;
    private int leftScore = 0, rightScore = 0;
    private int ballSpeedX = INITIAL_BALL_SPEED, ballSpeedY = INITIAL_BALL_SPEED;
    private boolean isPaused = false, isSinglePlayer = false;

    private String player1Name = "Player 1", player2Name = "Player 2";
    private int maxPoints = 10;
    private boolean[] keys = new boolean[65535];
    private int countdownNumber = 0;
    private String  difficultyLevel; 

    public PingPongImproved() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);
        initializeGameObjects();
    }

    private void initializeGameObjects() {
        leftRocket = new Rectangle(20, (HEIGHT / 2) - (ROCKET_HEIGHT / 2), ROCKET_WIDTH, ROCKET_HEIGHT);
        rightRocket = new Rectangle(WIDTH - ROCKET_WIDTH - 20, (HEIGHT / 2) - (ROCKET_HEIGHT / 2), ROCKET_WIDTH, ROCKET_HEIGHT);
        ball = new Rectangle((WIDTH / 2) - (BALL_SIZE / 2), (HEIGHT / 2) - (BALL_SIZE / 2), BALL_SIZE, BALL_SIZE);
    }

    public void start() {
        // Select player mode (Single Player or Double Player)
        String[] modeOptions = {"Single Player", "Double Player"};
        String mode = (String) JOptionPane.showInputDialog(
            null,
            "Select Player Mode:",
            "Mode",
            JOptionPane.QUESTION_MESSAGE,
            null,
            modeOptions,
            "Single Player"
        );
        isSinglePlayer = mode.startsWith("Single");
    
        // Get player names
        player1Name = JOptionPane.showInputDialog("Enter Player 1 Name:");
        if (isSinglePlayer) {
            player2Name = "CPU";
        } else {
            player2Name = JOptionPane.showInputDialog("Enter Player 2 Name:");
        }
    
        // Select difficulty level
        String[] difficultyOptions = {"Easy", "Medium", "Hard"};
        difficultyLevel = (String) JOptionPane.showInputDialog(
            null,
            "Select Difficulty Level:",
            "Difficulty",
            JOptionPane.QUESTION_MESSAGE,
            null,
            difficultyOptions,
            "Medium"
        );
        setDifficulty();
    
        // Set maximum points to win
        String maxPointsInput;
        do {
            maxPointsInput = JOptionPane.showInputDialog(
                null,
                "Enter Maximum Points to Win:",
                "Game Setup",
                JOptionPane.QUESTION_MESSAGE
            );
        } while (!isValidNumber(maxPointsInput));
        maxPoints = Integer.parseInt(maxPointsInput);
    
        // Display instructions
        String message = "First to " + maxPoints + " points wins!";
        JOptionPane.showMessageDialog(this, message, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
    
        // Start game thread
        Thread gameThread = new Thread(this);
        gameThread.start();
        countdown(3);
    }
    
    public static int getCurrentDisplayRefreshRate() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        DisplayMode displayMode = defaultScreen.getDisplayMode();
        int refreshRate = displayMode.getRefreshRate();
        return refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN ? 0 : refreshRate;
    }
     
    private boolean isValidNumber(String input) {
        try {
            int number = Integer.parseInt(input);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    

    @Override
    public void run() {
        final int UPS = 60; // Updates per second (ticks per second)
        final double nsPerTick = 1_000_000_000.0 / UPS; // Nanoseconds per update
    
       // int refreshRate = getRefreshRate(); // Dynamically get refresh rate (assume 60+ supported)
        int targetFPS = getRefreshRate(); // Cap FPS at 60 for standard gameplay
        long targetFrameTime = 1_000_000_000L / targetFPS; // Nanoseconds per frame
        final int minSleepTime = 16; // Minimum sleep time in milliseconds
    
        long lastTime = System.nanoTime();
        double delta = 0;
    
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
    
            // Update game logic if enough time has passed (based on UPS)
            if (!isPaused && delta >= 1) {
                update(); // Update game logic
                delta--;
            }
    
            // Render the game state
            repaint();
            
            // Calculate elapsed time for this frame
            long frameEndTime = System.nanoTime();
            long frameDuration = frameEndTime - now;
            //System.out.println("UPS: " + nsPerTick + ", FPS: " + targetFPS);
            // Calculate sleep time to maintain target FPS
            long sleepTime = (targetFrameTime - frameDuration) / 1_000_000; // Convert to milliseconds
            if (sleepTime > 0) {
                try {
                    Thread.sleep(Math.max(sleepTime, minSleepTime)); // Ensure minimum sleep time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //Retrieve the monitor's refresh rate.
    private int getRefreshRate() {
        try {
            // Toolkit provides screen-related information
            return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDisplayMode()
                    .getRefreshRate();
        } catch (Exception e) {
            // Default refresh rate if not determinable
            return 60;
        }
    }
    

    
    
    private void setDifficulty() {
        switch (difficultyLevel) {
            case "Easy":
                ballSpeedX = 4;
                ballSpeedY = 4;
                ROCKET_SPEED = 5;
                break;
            case "Medium":
                ballSpeedX = 7;
                ballSpeedY = 7;
                ROCKET_SPEED = 5;
                break;
            case "Hard":
                ballSpeedX = 9;
                ballSpeedY = 9;
                ROCKET_SPEED = 6;
                break;
        }
    }

    private void update() {
        if (countdownNumber > 0) return;

        if (keys[KeyEvent.VK_W] && leftRocket.y > 0) leftRocket.y -= ROCKET_SPEED;
        if (keys[KeyEvent.VK_S] && leftRocket.y < HEIGHT - ROCKET_HEIGHT) leftRocket.y += ROCKET_SPEED;

        if (!isSinglePlayer) {
            if (keys[KeyEvent.VK_UP] && rightRocket.y > 0) rightRocket.y -= ROCKET_SPEED;
            if (keys[KeyEvent.VK_DOWN] && rightRocket.y < HEIGHT - ROCKET_HEIGHT) rightRocket.y += ROCKET_SPEED;
        } else {
            if (ball.y < rightRocket.y) rightRocket.y -= ROCKET_SPEED;
            if (ball.y > rightRocket.y + ROCKET_HEIGHT) rightRocket.y += ROCKET_SPEED;
        }

        ball.x += ballSpeedX;
        ball.y += ballSpeedY;

        if (ball.y <= 0 || ball.y >= HEIGHT - BALL_SIZE) ballSpeedY *= -1;

        if (ball.intersects(leftRocket) || ball.intersects(rightRocket)) ballSpeedX *= -1;

        if (ball.x < 0) {
            rightScore++;
            resetRound();
        } else if (ball.x > WIDTH) {
            leftScore++;
            resetRound();
        }

        if (leftScore == maxPoints || rightScore == maxPoints) {
            String winner = (leftScore == maxPoints) ? player1Name : player2Name;
            JOptionPane.showMessageDialog(this, winner + " Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    private void resetRound() {
        ball.x = (WIDTH / 2) - (BALL_SIZE / 2);
        ball.y = (HEIGHT / 2) - (BALL_SIZE / 2);
        leftRocket.y = (HEIGHT / 2) - (ROCKET_HEIGHT / 2);
        rightRocket.y = (HEIGHT / 2) - (ROCKET_HEIGHT / 2);
        ballSpeedX *= -1;
        countdown(3);
    }

    private void countdown(int seconds) {
        isPaused = true;
        countdownNumber = seconds;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (countdownNumber > 0) {
                    repaint();
                    countdownNumber--;
                } else {
                    isPaused = false;
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(50, 50, 100), 0, HEIGHT, new Color(20, 20, 60));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(new Color(200, 200, 200));
        g.fillRoundRect(leftRocket.x, leftRocket.y, leftRocket.width, leftRocket.height, 10, 10);
        g.fillRoundRect(rightRocket.x, rightRocket.y, rightRocket.width, rightRocket.height, 10, 10);

        g.setColor(new Color(255, 100, 100));
        g.fillOval(ball.x, ball.y, ball.width, ball.height);

        g.setFont(LARGE_FONT);
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics(LARGE_FONT);
        g.drawString(String.valueOf(leftScore), WIDTH / 4 - fm.stringWidth(String.valueOf(leftScore)) / 2, 50);
        g.drawString(String.valueOf(rightScore), 3 * WIDTH / 4 - fm.stringWidth(String.valueOf(rightScore)) / 2, 50);

        g.setFont(SMALL_FONT);
        g.drawString(player1Name, 20, 30);
        g.drawString(player2Name, WIDTH - 150, 30);

        g.drawString("Keys: W/S for Player 1, UP/DOWN for Player 2, SPACE to Pause", WIDTH / 4, HEIGHT - 10);

        if (countdownNumber > 0) {
            String countdownText = String.valueOf(countdownNumber);
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(LARGE_FONT);
            fm = g.getFontMetrics(LARGE_FONT);
            g.drawString(countdownText, (WIDTH - fm.stringWidth(countdownText)) / 2, HEIGHT / 2);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE && countdownNumber <= 0) {
            isPaused = !isPaused;
            if (!isPaused) countdown(3);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Pong Improved");
        PingPongImproved game = new PingPongImproved();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        game.start();
    }
}
