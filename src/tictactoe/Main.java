package tictactoe;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//TicTacToe is FUN !
//sorry for the late submission sir, I didn't realize I didn't submit yet. Hoping for your consideration, I understand that I will have demerits due to my tardiness
public class Main extends Frame {
    private Button[][] buttons;
    private char currentPlayer;
    private char[][] board;
    private Button newGameButton;

    public Main() {
        currentPlayer = 'X';
        board = new char[3][3];
        initializeBoard();
        initializeGUI();
    }

    // Initialize the game board with empty values
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Initialize the GUI components
    private void initializeGUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 450); // Adjusted size to accommodate the New Game button
        setLayout(new BorderLayout());

        // Panel for the game grid
        Panel gridPanel = new Panel();
        gridPanel.setLayout(new GridLayout(3, 3));
        buttons = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                gridPanel.add(buttons[i][j]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // New Game button
        newGameButton = new Button("New Game");
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 20));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });
        add(newGameButton, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Listener for the grid buttons
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board[row][col] == '-') {
                board[row][col] = currentPlayer;
                buttons[row][col].setLabel(String.valueOf(currentPlayer));
                buttons[row][col].setEnabled(false);

                if (checkWin()) {
                    showMessage("Player " + currentPlayer + " wins!");
                    resetBoard();
                } else if (isBoardFull()) {
                    showMessage("It's a draw!");
                    resetBoard();
                } else {
                    switchPlayer();
                }
            }
        }
    }

    // Switch the current player
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    // Check if the board is full
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if there is a win
    private boolean checkWin() {
        return (checkRows() || checkColumns() || checkDiagonals());
    }

    // Check rows for a win
    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2])) {
                return true;
            }
        }
        return false;
    }

    // Check columns for a win
    private boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i])) {
                return true;
            }
        }
        return false;
    }

    // Check diagonals for a win
    private boolean checkDiagonals() {
        return (checkRowCol(board[0][0], board[1][1], board[2][2]) || checkRowCol(board[0][2], board[1][1], board[2][0]));
    }

    // Check if all values are the same in a row or column or diagonal
    private boolean checkRowCol(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }

    // Reset the game board for a new game
    private void resetBoard() {
        currentPlayer = 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
                buttons[i][j].setLabel("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    // Display a message dialog
    private void showMessage(String message) {
        new Dialog(this, "Game Over", message);
    }

    // Main method to start the application
    public static void main(String[] args) {
        new Main();
    }
}

// Dialog class for displaying messages
class Dialog extends java.awt.Dialog {
    public Dialog(Frame parent, String title, String message) {
        super(parent, title, true);
        setLayout(new BorderLayout());
        add(new java.awt.Label(message), BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(ok, BorderLayout.SOUTH);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
