import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TetrisGame extends JPanel implements ActionListener, KeyListener {
    // Kích thước game
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private static final int CELL_SIZE = 30;
    private static final int BOARD_PIXEL_WIDTH = BOARD_WIDTH * CELL_SIZE;
    private static final int BOARD_PIXEL_HEIGHT = BOARD_HEIGHT * CELL_SIZE;
    private static final int SIDEBAR_WIDTH = 200;
    private static final int WINDOW_WIDTH = BOARD_PIXEL_WIDTH + SIDEBAR_WIDTH + 40;
    private static final int WINDOW_HEIGHT = BOARD_PIXEL_HEIGHT + 80;
    
    private static final int DROP_DELAY = 500; // Thời gian rơi tự động (ms)
    
    // Màu sắc cho các khối
    private static final Color[] COLORS = {
        new Color(0, 0, 0),           // 0 - Màu nền
        new Color(0, 255, 255),       // 1 - I (Cyan)
        new Color(255, 255, 0),       // 2 - O (Yellow)
        new Color(128, 0, 128),       // 3 - T (Purple)
        new Color(0, 255, 0),         // 4 - S (Green)
        new Color(255, 0, 0),         // 5 - Z (Red)
        new Color(0, 0, 255),         // 6 - J (Blue)
        new Color(255, 165, 0)        // 7 - L (Orange)
    };
    
    // Các hình dạng Tetris (I, O, T, S, Z, J, L)
    private static final int[][][] SHAPES = {
        // I
        {{1, 1, 1, 1}},
        // O
        {{2, 2}, {2, 2}},
        // T
        {{0, 3, 0}, {3, 3, 3}},
        // S
        {{0, 4, 4}, {4, 4, 0}},
        // Z
        {{5, 5, 0}, {0, 5, 5}},
        // J
        {{6, 0, 0}, {6, 6, 6}},
        // L
        {{0, 0, 7}, {7, 7, 7}}
    };
    
    private int[][] board;
    private int[][] currentPiece;
    private int currentPieceType;
    private int currentX, currentY;
    private int nextPieceType;
    private Random random;
    private Timer timer;
    private boolean gameOver = false;
    private boolean paused = false;
    private int score = 0;
    private int level = 1;
    private int linesCleared = 0;
    private boolean isDropping = false;
    
    public TetrisGame() {
        random = new Random();
        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(new Color(20, 20, 30));
        this.setFocusable(true);
        this.addKeyListener(this);
        
        spawnNewPiece();
        nextPieceType = random.nextInt(SHAPES.length);
        
        timer = new Timer(DROP_DELAY, this);
        timer.start();
    }
    
    private void spawnNewPiece() {
        currentPieceType = nextPieceType;
        currentPiece = copyShape(SHAPES[currentPieceType]);
        currentX = BOARD_WIDTH / 2 - currentPiece[0].length / 2;
        currentY = 0;
        nextPieceType = random.nextInt(SHAPES.length);
        
        // Kiểm tra game over
        if (collision(currentPiece, currentX, currentY)) {
            gameOver = true;
            timer.stop();
        }
    }
    
    private int[][] copyShape(int[][] shape) {
        int[][] newShape = new int[shape.length][];
        for (int i = 0; i < shape.length; i++) {
            newShape[i] = shape[i].clone();
        }
        return newShape;
    }
    
    private boolean collision(int[][] piece, int x, int y) {
        for (int row = 0; row < piece.length; row++) {
            for (int col = 0; col < piece[row].length; col++) {
                if (piece[row][col] != 0) {
                    int newX = x + col;
                    int newY = y + row;
                    
                    if (newX < 0 || newX >= BOARD_WIDTH || 
                        newY >= BOARD_HEIGHT ||
                        (newY >= 0 && board[newY][newX] != 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void placePiece() {
        for (int row = 0; row < currentPiece.length; row++) {
            for (int col = 0; col < currentPiece[row].length; col++) {
                if (currentPiece[row][col] != 0) {
                    int y = currentY + row;
                    int x = currentX + col;
                    if (y >= 0) {
                        board[y][x] = currentPiece[row][col];
                    }
                }
            }
        }
        clearLines();
        spawnNewPiece();
        isDropping = false;
    }
    
    private void clearLines() {
        int linesToClear = 0;
        for (int row = BOARD_HEIGHT - 1; row >= 0; row--) {
            boolean isFull = true;
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] == 0) {
                    isFull = false;
                    break;
                }
            }
            
            if (isFull) {
                linesToClear++;
                // Xóa dòng và di chuyển các dòng trên xuống
                for (int r = row; r > 0; r--) {
                    System.arraycopy(board[r - 1], 0, board[r], 0, BOARD_WIDTH);
                }
                // Xóa dòng đầu tiên
                for (int col = 0; col < BOARD_WIDTH; col++) {
                    board[0][col] = 0;
                }
                row++; // Kiểm tra lại dòng này
            }
        }
        
        if (linesToClear > 0) {
            linesCleared += linesToClear;
            // Tính điểm
            int[] points = {0, 100, 300, 500, 800};
            score += points[Math.min(linesToClear, 4)] * level;
            level = linesCleared / 10 + 1;
            // Tăng tốc độ
            timer.setDelay(Math.max(50, DROP_DELAY - (level - 1) * 50));
        }
    }
    
    private void rotatePiece() {
        int[][] rotated = new int[currentPiece[0].length][currentPiece.length];
        for (int row = 0; row < currentPiece.length; row++) {
            for (int col = 0; col < currentPiece[row].length; col++) {
                rotated[col][currentPiece.length - 1 - row] = currentPiece[row][col];
            }
        }
        
        if (!collision(rotated, currentX, currentY)) {
            currentPiece = rotated;
        }
    }
    
    private void movePiece(int dx, int dy) {
        if (!collision(currentPiece, currentX + dx, currentY + dy)) {
            currentX += dx;
            currentY += dy;
        } else if (dy > 0) {
            placePiece();
        }
    }
    
    private void dropPiece() {
        isDropping = true;
        while (!collision(currentPiece, currentX, currentY + 1)) {
            currentY++;
        }
        placePiece();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Vẽ nền
        g2d.setColor(new Color(20, 20, 30));
        g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // Vẽ board
        int boardX = 20;
        int boardY = 20;
        
        // Vẽ viền board
        g2d.setColor(new Color(100, 100, 150));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(boardX - 2, boardY - 2, BOARD_PIXEL_WIDTH + 4, BOARD_PIXEL_HEIGHT + 4);
        
        // Vẽ nền board
        g2d.setColor(new Color(30, 30, 40));
        g2d.fillRect(boardX, boardY, BOARD_PIXEL_WIDTH, BOARD_PIXEL_HEIGHT);
        
        // Vẽ các ô đã đặt
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] != 0) {
                    drawCell(g2d, boardX + col * CELL_SIZE, boardY + row * CELL_SIZE, 
                            COLORS[board[row][col]], true);
                }
            }
        }
        
        // Vẽ khối hiện tại
        if (!gameOver) {
            for (int row = 0; row < currentPiece.length; row++) {
                for (int col = 0; col < currentPiece[row].length; col++) {
                    if (currentPiece[row][col] != 0) {
                        int x = boardX + (currentX + col) * CELL_SIZE;
                        int y = boardY + (currentY + row) * CELL_SIZE;
                        if (y >= boardY) {
                            drawCell(g2d, x, y, COLORS[currentPiece[row][col]], true);
                        }
                    }
                }
            }
        }
        
        // Vẽ sidebar
        int sidebarX = boardX + BOARD_PIXEL_WIDTH + 20;
        int sidebarY = boardY;
        
        // Vẽ thông tin
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("TETRIS", sidebarX, sidebarY + 25);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("Điểm: " + score, sidebarX, sidebarY + 60);
        g2d.drawString("Cấp độ: " + level, sidebarX, sidebarY + 85);
        g2d.drawString("Dòng: " + linesCleared, sidebarX, sidebarY + 110);
        
        // Vẽ khối tiếp theo
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Tiếp theo:", sidebarX, sidebarY + 150);
        
        int[][] nextPiece = SHAPES[nextPieceType];
        int nextPieceX = sidebarX;
        int nextPieceY = sidebarY + 170;
        
        for (int row = 0; row < nextPiece.length; row++) {
            for (int col = 0; col < nextPiece[row].length; col++) {
                if (nextPiece[row][col] != 0) {
                    drawCell(g2d, nextPieceX + col * CELL_SIZE, 
                            nextPieceY + row * CELL_SIZE, 
                            COLORS[nextPiece[row][col]], true);
                }
            }
        }
        
        // Vẽ hướng dẫn
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        int instructionsY = sidebarY + 280;
        g2d.drawString("Điều khiển:", sidebarX, instructionsY);
        g2d.drawString("← → : Di chuyển", sidebarX, instructionsY + 20);
        g2d.drawString("↑ : Xoay", sidebarX, instructionsY + 40);
        g2d.drawString("↓ : Rơi nhanh", sidebarX, instructionsY + 60);
        g2d.drawString("SPACE : Thả xuống", sidebarX, instructionsY + 80);
        g2d.drawString("P : Tạm dừng", sidebarX, instructionsY + 100);
        
        // Vẽ game over
        if (gameOver) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            FontMetrics fm = g2d.getFontMetrics();
            String gameOverText = "GAME OVER";
            g2d.drawString(gameOverText, 
                    (WINDOW_WIDTH - fm.stringWidth(gameOverText)) / 2,
                    WINDOW_HEIGHT / 2 - 20);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            String restartText = "Nhấn R để chơi lại";
            g2d.drawString(restartText,
                    (WINDOW_WIDTH - g2d.getFontMetrics().stringWidth(restartText)) / 2,
                    WINDOW_HEIGHT / 2 + 20);
        }
        
        // Vẽ paused
        if (paused && !gameOver) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            
            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            FontMetrics fm = g2d.getFontMetrics();
            String pausedText = "TẠM DỪNG";
            g2d.drawString(pausedText,
                    (WINDOW_WIDTH - fm.stringWidth(pausedText)) / 2,
                    WINDOW_HEIGHT / 2);
        }
    }
    
    private void drawCell(Graphics2D g2d, int x, int y, Color color, boolean withBorder) {
        // Vẽ ô với hiệu ứng 3D
        g2d.setColor(color);
        g2d.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
        
        if (withBorder) {
            // Vẽ viền sáng
            g2d.setColor(color.brighter());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x + 1, y + 1, x + CELL_SIZE - 2, y + 1);
            g2d.drawLine(x + 1, y + 1, x + 1, y + CELL_SIZE - 2);
            
            // Vẽ viền tối
            g2d.setColor(color.darker());
            g2d.drawLine(x + CELL_SIZE - 2, y + 1, x + CELL_SIZE - 2, y + CELL_SIZE - 2);
            g2d.drawLine(x + 1, y + CELL_SIZE - 2, x + CELL_SIZE - 2, y + CELL_SIZE - 2);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !paused && !isDropping) {
            movePiece(0, 1);
            repaint();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                restart();
            }
            return;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
            repaint();
            return;
        }
        
        if (paused) {
            return;
        }
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                movePiece(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                movePiece(1, 0);
                break;
            case KeyEvent.VK_DOWN:
                movePiece(0, 1);
                break;
            case KeyEvent.VK_UP:
                rotatePiece();
                break;
            case KeyEvent.VK_SPACE:
                dropPiece();
                break;
        }
        repaint();
    }
    
    private void restart() {
        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        score = 0;
        level = 1;
        linesCleared = 0;
        gameOver = false;
        paused = false;
        isDropping = false;
        timer.setDelay(DROP_DELAY);
        spawnNewPiece();
        nextPieceType = random.nextInt(SHAPES.length);
        timer.start();
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Game - Đẹp Mắt");
        TetrisGame game = new TetrisGame();
        
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

