import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI_V2 extends JFrame implements ActionListener {

    private JButton[] buttons = new JButton[9];
    private String turn = "X";
    private JLabel statusLabel;

    public TicTacToeGUI_V2() {
        setTitle("Tic Tac Toe");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("Comic Sans MS", Font.BOLD, 60);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(font);
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setForeground(new Color(50, 50, 50));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        // Status Label
        statusLabel = new JLabel("X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        statusLabel.setForeground(new Color(60, 60, 60));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(statusLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (!clickedButton.getText().equals("")) {
            return;
        }

        clickedButton.setText(turn);
        clickedButton.setForeground(turn.equals("X") ? new Color(30, 144, 255) : new Color(220, 20, 60)); // Blue for X, Red for O

        int[] winningLine = checkWinner();

        if (winningLine != null) {
            highlightWinner(winningLine);
            statusLabel.setText(turn + " Wins!");
            JOptionPane.showMessageDialog(this, "Congratulations! " + turn + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else if (isDraw()) {
            statusLabel.setText("It's a Draw!");
            JOptionPane.showMessageDialog(this, "It's a Draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else {
            turn = (turn.equals("X")) ? "O" : "X";
            statusLabel.setText(turn + "'s Turn");
        }
    }

    private int[] checkWinner() {
        int[][] winConditions = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
        };

        for (int[] condition : winConditions) {
            if (buttons[condition[0]].getText().equals(turn) &&
                buttons[condition[1]].getText().equals(turn) &&
                buttons[condition[2]].getText().equals(turn)) {
                return condition;
            }
        }
        return null;
    }

    private boolean isDraw() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void highlightWinner(int[] winCondition) {
        for (int index : winCondition) {
            buttons[index].setBackground(new Color(144, 238, 144)); // Light green for winning buttons
        }
    }

    private void resetGame() {
        Timer timer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                for (JButton button : buttons) {
                    button.setText("");
                    button.setBackground(Color.WHITE);
                }
                turn = "X";
                statusLabel.setText("X's Turn");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        new TicTacToeGUI_V2();
    }
}
