import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Sudoku {
    private static final int SIZE = 9;
    private static JTextField[][] cells = new JTextField[SIZE][SIZE];
    private static int[][] solution = new int[SIZE][SIZE];

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sudoku");
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        gridPanel.setBackground(Color.BLACK);
        buttonPanel.setBackground(Color.BLACK);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 20));
                cells[row][col].setBackground(Color.DARK_GRAY);
                cells[row][col].setForeground(Color.CYAN);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[row][col].setDocument(new JTextFieldLimit(1));
                gridPanel.add(cells[row][col]);
            }
        }

        generatePuzzle();

        JButton validateButton = new JButton("Validate");
        validateButton.setBackground(Color.GREEN);
        validateButton.setForeground(Color.WHITE);
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateGrid();
            }
        });
        buttonPanel.add(validateButton);

        JButton hintButton = new JButton("Hint");
        hintButton.setBackground(Color.ORANGE);
        hintButton.setForeground(Color.WHITE);
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provideHint();
            }
        });
        buttonPanel.add(hintButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPuzzle();
            }
        });
        buttonPanel.add(resetButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);
    }

    private static void generatePuzzle() {
        int[][] puzzle = generateNewPuzzle();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                solution[row][col] = puzzle[row][col];
                if (puzzle[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(puzzle[row][col]));
                    cells[row][col].setEditable(false);
                } else {
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);
                }
            }
        }
    }

    private static int[][] generateNewPuzzle() {
        int[][] puzzle = new int[SIZE][SIZE];
        solveSudoku(puzzle, 0, 0);
        removeNumbers(puzzle);
        return puzzle;
    }

    private static boolean solveSudoku(int[][] board, int row, int col) {
        if (row == SIZE - 1 && col == SIZE) return true;
        if (col == SIZE) {
            row++;
            col = 0;
        }
        if (board[row][col] != 0) return solveSudoku(board, row, col + 1);

        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board, row, col + 1)) return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    private static boolean isSafe(int[][] board, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num) return false;
        }
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) return false;
            }
        }
        return true;
    }

    private static void removeNumbers(int[][] board) {
        Random rand = new Random();
        int count = 20;
        while (count != 0) {
            int cellId = rand.nextInt(SIZE * SIZE);
            int i = cellId / SIZE;
            int j = cellId % SIZE;
            if (board[i][j] != 0) {
                board[i][j] = 0;
                count--;
            }
        }
    }

    private static void validateGrid() {
        boolean allFilled = true;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                if (text.isEmpty()) {
                    allFilled = false;
                    JOptionPane.showMessageDialog(null, "There are empty cells.");
                    return;
                }
                int num = Integer.parseInt(text);
                if (num < 1 || num > 9 || !isValid(num, row, col)) {
                    JOptionPane.showMessageDialog(null, "Invalid number at (" + (row + 1) + ", " + (col + 1) + ")");
                    return;
                }
            }
        }
        if (allFilled) {
            JOptionPane.showMessageDialog(null, "Congratulations! You have completed the Sudoku puzzle.");
        }
    }

    private static boolean isValid(int num, int row, int col) {
        for (int i = 0; i < SIZE; i++) {
            if (cells[row][i].getText().equals(String.valueOf(num)) && i != col) return false;
            if (cells[i][col].getText().equals(String.valueOf(num)) && i != row) return false;
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (cells[i][j].getText().equals(String.valueOf(num)) && (i != row || j != col)) return false;
            }
        }
        return true;
    }

    private static void provideHint() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col].getText().isEmpty()) {
                    if (solution[row][col] != 0) {
                        cells[row][col].setText(String.valueOf(solution[row][col]));
                    }
                    return;
                }
            }
        }
    }

    private static void resetPuzzle() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].setText("");
                }
            }
        }
    }
}

class JTextFieldLimit extends javax.swing.text.PlainDocument {
    private int limit;

    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit && str.matches("[1-9]")) {
            super.insertString(offset, str, attr);
        }
    }
}