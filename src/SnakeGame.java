import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    // Kích thước game
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / UNIT_SIZE;
    private static final int DELAY = 100;

    // Tọa độ rắn và thức ăn
    private ArrayList<Point> snake;
    private Point food;
    private char direction = 'R'; // R: phải, L: trái, U: lên, D: xuống
    private boolean running = false;
    private Timer timer;
    private Random random;
    private int score = 0;

    public SnakeGame() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        startGame();
    }

    public void startGame() {
        snake = new ArrayList<>();
        // Tạo rắn ban đầu ở giữa màn hình
        for (int i = 0; i < 3; i++) {
            snake.add(new Point(WIDTH / 2 - i * UNIT_SIZE, HEIGHT / 2));
        }

        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        score = 0;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Vẽ lưới (tùy chọn)
            g.setColor(Color.DARK_GRAY);
            for (int i = 0; i < HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, WIDTH, i * UNIT_SIZE);
            }

            // Vẽ thức ăn
            g.setColor(Color.RED);
            g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

            // Vẽ rắn
            for (int i = 0; i < snake.size(); i++) {
                if (i == 0) {
                    // Đầu rắn
                    g.setColor(Color.GREEN);
                } else {
                    // Thân rắn
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(snake.get(i).x, snake.get(i).y, UNIT_SIZE, UNIT_SIZE);
            }

            // Vẽ điểm số
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Điểm: " + score, (WIDTH - metrics.stringWidth("Điểm: " + score)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newFood() {
        int x = random.nextInt((int)(WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        int y = random.nextInt((int)(HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        food = new Point(x, y);

        // Đảm bảo thức ăn không xuất hiện trên thân rắn
        for (Point body : snake) {
            if (food.equals(body)) {
                newFood();
                return;
            }
        }
    }

    public void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case 'U':
                newHead.y -= UNIT_SIZE;
                break;
            case 'D':
                newHead.y += UNIT_SIZE;
                break;
            case 'L':
                newHead.x -= UNIT_SIZE;
                break;
            case 'R':
                newHead.x += UNIT_SIZE;
                break;
        }

        snake.add(0, newHead);

        // Kiểm tra nếu rắn ăn được thức ăn
        if (newHead.equals(food)) {
            score++;
            newFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    public void checkFood() {
        // Đã được xử lý trong move()
    }

    public void checkCollisions() {
        Point head = snake.get(0);

        // Kiểm tra va chạm với tường
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            running = false;
        }

        // Kiểm tra va chạm với thân rắn
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                running = false;
            }
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // Hiển thị điểm số cuối
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Điểm: " + score, (WIDTH - metrics1.stringWidth("Điểm: " + score)) / 2,
                g.getFont().getSize());

        // Hiển thị Game Over
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (WIDTH - metrics2.stringWidth("GAME OVER")) / 2,
                HEIGHT / 2);

        // Hướng dẫn chơi lại
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Nhấn SPACE để chơi lại",
                (WIDTH - metrics3.stringWidth("Nhấn SPACE để chơi lại")) / 2,
                HEIGHT / 2 + 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!running) {
                    startGame();
                    repaint();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Rắn Săn Mồi");
        SnakeGame game = new SnakeGame();

        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
