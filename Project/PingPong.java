import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class PingPong extends JPanel implements Runnable, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int ROCKET_WIDTH = 15;
    private static final int ROCKET_HEIGHT = 100;
    private static final int ROCKET_SPEED = 5;
    private static final int BALL_SIZE = 20;
    private static final int BALL_START_X = (WIDTH / 2) - (BALL_SIZE / 2);
    private static final int BALL_START_Y = (HEIGHT / 2) - (BALL_SIZE / 2);
    private static int BALL_SPEED_X = 4;
    private static int BALL_SPEED_Y = 4;
    private static final double UPS = 60.0;
    private static final double FPS = 60.0;
    private static final boolean[] KEYS = new boolean[65535];
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color ROCKET_COLOR = new Color(220, 220, 220);
    private static final Color BALL_COLOR = new Color(255, 69, 58);
    private static final Font FONT = new Font("Arial", Font.BOLD, 36);
    private static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 18);
    private static final int SCORE_Y = 50;

    private Rectangle leftRocket;
    private Rectangle rightRocket;
    private Rectangle ball;

    private int leftScore = 0;
    private int rightScore = 0;
    private String player1Name;
    private String player2Name;
    private int maxPoints;

    public PingPong() {
        super(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);
        this.leftRocket = new Rectangle(0, (HEIGHT / 2) - (ROCKET_HEIGHT / 2), ROCKET_WIDTH, ROCKET_HEIGHT);
        this.rightRocket = new Rectangle(WIDTH - ROCKET_WIDTH, (HEIGHT / 2) - (ROCKET_HEIGHT / 2), ROCKET_WIDTH, ROCKET_HEIGHT);
        this.ball = new Rectangle(BALL_START_X, BALL_START_Y, BALL_SIZE, BALL_SIZE);
    }
    public static int getCurrentDisplayRefreshRate() {
        // Get the local graphics environment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // Get the default screen device (current display)
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        //return defaultScreen.getDisplayMode().getRefreshRate();
        // Get the display mode of the current display
        DisplayMode displayMode = defaultScreen.getDisplayMode();

        // Get the refresh rate
        int refreshRate = displayMode.getRefreshRate();

        if (refreshRate == displayMode.REFRESH_RATE_UNKNOWN) {
            return 0; 
        } else {
            return refreshRate; // Return refresh rate in Hz
        }
    }
    public void start() {
        player1Name = JOptionPane.showInputDialog("Enter Player 1 Name:");
        player2Name = JOptionPane.showInputDialog("Enter Player 2 Name:");
        String maxPointsInput;
        do {
            maxPointsInput = JOptionPane.showInputDialog(null, "Enter Maximum Points to Win:", "Game Setup", JOptionPane.QUESTION_MESSAGE);
        } while (!isValidNumber(maxPointsInput));

        this.maxPoints = Integer.parseInt(maxPointsInput);
        String message = "First to " + maxPoints + " points wins!";

        JOptionPane.showMessageDialog(this, message, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);

        Thread gameThread = new Thread(this);
        gameThread.start();
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
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / UPS;
        final double timeF = 1000000000 / FPS;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        while (true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                this.update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1) {
                this.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.println("UPS: " + ticks + ", FPS: " + frames);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
            //Or Lap CPU goes drrrrrrrrrrr(100% cpu usage)
            try {
                Thread.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // Move left rocket
        if (KEYS[KeyEvent.VK_W] && this.leftRocket.y > 0) {
            this.leftRocket.y -= ROCKET_SPEED;
        }
        if (KEYS[KeyEvent.VK_S] && this.leftRocket.y < HEIGHT - ROCKET_HEIGHT) {
            this.leftRocket.y += ROCKET_SPEED;
        }
        // Move right rocket
        if (KEYS[KeyEvent.VK_UP] && this.rightRocket.y > 0) {
            this.rightRocket.y -= ROCKET_SPEED;
        }
        if (KEYS[KeyEvent.VK_DOWN] && this.rightRocket.y < HEIGHT - ROCKET_HEIGHT) {
            this.rightRocket.y += ROCKET_SPEED;
        }

        this.ball.x += BALL_SPEED_X;
        this.ball.y += BALL_SPEED_Y;

        if (this.ball.y <= 0 || this.ball.y >= HEIGHT - BALL_SIZE) {
            BALL_SPEED_Y *= -1;
        }
        // Ball collision
        if (this.ball.intersects(this.leftRocket) || this.ball.intersects(this.rightRocket)) {
            BALL_SPEED_X *= -1;
        }
         // Ball goes out of bounds
        if (this.ball.x < 0) {
            this.rightScore++;
            resetBall();
        } else if (this.ball.x > WIDTH) {
            this.leftScore++;
            resetBall();
        }
        // Check for win condition
        if (this.leftScore >= this.maxPoints) {
            JOptionPane.showMessageDialog(this, player1Name + " wins! with " + this.maxPoints + " Points");
            System.exit(0);
        } else if (this.rightScore >= this.maxPoints) {
            JOptionPane.showMessageDialog(this, player2Name + " wins! with " + this.maxPoints + " Points");
            System.exit(0);
        }
    }

    private void resetBall() {
        this.ball.x = BALL_START_X;
        this.ball.y = BALL_START_Y;
        BALL_SPEED_X *= -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        // Draw paddles
        g2.setColor(ROCKET_COLOR);
        g2.fillRoundRect(this.leftRocket.x, this.leftRocket.y, ROCKET_WIDTH, ROCKET_HEIGHT, 10, 10);
        g2.fillRoundRect(this.rightRocket.x, this.rightRocket.y, ROCKET_WIDTH, ROCKET_HEIGHT, 10, 10);
        // Draw ball
        g2.setColor(BALL_COLOR);
        g2.fillOval(this.ball.x, this.ball.y, BALL_SIZE, BALL_SIZE);
        // Draw score
        g2.setFont(FONT);
        g2.setColor(Color.WHITE);
        String scoreText = player1Name + " " + this.leftScore + " : " + this.rightScore + " " + player2Name;
        FontMetrics fm = g2.getFontMetrics(FONT);
        g2.drawString(scoreText, (WIDTH - fm.stringWidth(scoreText)) / 2, SCORE_Y);

        g2.setFont(SMALL_FONT);
        String instructions = "W/S: Move Left Paddle | Up/Down: Move Right Paddle";
        g2.drawString(instructions, (WIDTH - g2.getFontMetrics(SMALL_FONT).stringWidth(instructions)) / 2, HEIGHT - 30);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        KEYS[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KEYS[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        PingPong pong = new PingPong();
        JFrame frame = new JFrame("PingPong Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(pong);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        pong.start();
    }
}
