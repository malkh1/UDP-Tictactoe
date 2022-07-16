package source;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mohammad Alkhaledi 101162465
 * implements view for tictactoe game
 */
public class TicTacToeView extends JFrame{
    private final JButton[][] buttons;
    private final JPanel buttonPanel;
    private final ViewListener viewListener;
    private final JLabel turnLabel;
    /**
     * default constructor for tictactoe view
     */
    public TicTacToeView(ViewListener viewListener){
        super("UDP Tictactoe");
        this.viewListener = viewListener;
        turnLabel = new JLabel("Sample text");
        buttonPanel = new JPanel(new GridLayout(3,3));
        buttons = new JButton[3][3];
        initializeGUI();
    }

    /**
     * initialize gui at the start of the game
     */
    private void initializeGUI() {
        var bodyPanel = new JPanel(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(640,480));
        createButtons();
        bodyPanel.add(turnLabel, BorderLayout.NORTH);
        bodyPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bodyPanel);
        pack();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * updates the game board for view by changing text of JButton via coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @param player player to be displayed
     */
    public void updateButtonPressed(int x, int y, char player){
        buttons[x][y].setText("\t\t"+ player);
        buttons[x][y].setEnabled(false);
        turnLabel.setText(player + "'S TURN");
    }


    /**
     * creates buttons and adds action listeners.
     * button press tells controller button was pressed.
     * controller replies with information to update the view.
     */
    private void createButtons(){
        for(int i = 0; i < buttons.length; ++i){
            for(int j = 0; j < buttons.length; ++j){
                buttons[i][j] = new JButton("\t\t");
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addActionListener(e -> viewListener.handleButtonClick(finalI, finalJ));
                buttonPanel.add(buttons[i][j]);

            }
        }
    }

    /**
     * disables the game
     */
    public void disableGame(){
        for (JButton[] buttons1 : buttons){
            for (JButton button : buttons1) {
                button.setEnabled(false);
            }
        }
    }

    /**
     * enables the game board with only remaining spaces
     * @param board the board that will be used to enable the game buttons
     */
    public void updateGUI(char[][] board, boolean enable){

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == ' ' && enable){
                    buttons[i][j].setEnabled(true);
                }
                else if (board[i][j] == 'X'){
                    buttons[i][j].setText("X");
                }
                else if(board[i][j] == 'O'){
                    buttons[i][j].setText("O");
                }
            }
        }
    }


    /**
     * updates the turn label to reflect current player's turn
     * X = TRUE
     * O = FALSE
     * @param player player who is currently playing
     */
    public void changeTurnLabelText(boolean player){
        turnLabel.setText((player ? "X" : "O") + "'S TURN");
    }

    /**
     * displays tie game via JOptionPane
     */
    public void displayTie(){
        JOptionPane.showMessageDialog(this,"TIE!!");
    }
    /**
     * displays winner of game via JOptionPane
     * @param player winner of game
     */
    public void displayWinner(char player){
        JOptionPane.showMessageDialog(this, "Player " + player
                + " won!");

    }


}
