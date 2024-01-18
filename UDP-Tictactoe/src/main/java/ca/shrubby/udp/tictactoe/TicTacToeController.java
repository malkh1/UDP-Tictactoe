package ca.shrubby.udp.tictactoe;

import javax.swing.*;


/**
 * @author Mohammad Alkhaledi 101162465
 * implements controller for tictactoe game
 * GAME OVER IS NOT WORKING FIGURE OUT WHATS WRONG WITH IT AND WORK IS DONE
 */
public class TicTacToeController implements ViewListener{
    private TicTacToe model;
    private TicTacToeView view;
    private boolean turn;
    private boolean isClicked;
    public static boolean gameOver;

    /**
     * default constructor for controller
     * @param model model of tictactoe
     */
    public TicTacToeController(TicTacToe model){
        this.model = model;
        turn = false;
        isClicked = false;
        gameOver = false;
    }
    @Override
    public void handleButtonClick(int x, int y) {
        changeTurn();
        play(x, y);
        view.updateButtonPressed(x, y, model.getTurn() ? 'O' : 'X');
        view.changeTurnLabelText(turn);
        isClicked = true;
    }

    /**
     * makes a play and updates gui accordingly
     * @param x x coordinate of play
     * @param y y coordinate of play
     */
    public void play(int x, int y){
        model.play(x, y);

    }
    /**
     * checks final state of game.
     * displays message accordingly.
     */
    public void checkWin(){
        TicTacToe.Status status = model.getStatus();
        switch (status){
            case UNDECIDED->{}
            case O_WON-> {
                view.displayWinner('O');
                gameOver = true;
            }
            case X_WON -> {
                view.displayWinner('X');
                gameOver = true;

            }
            case TIE -> {
                view.displayTie();
                gameOver = true;
            }
            default-> JOptionPane.showMessageDialog(view, "error");
        }
    }

    /**
     * adds a view to the controller
     * @param view view to be added
     */
    public void setView(TicTacToeView view){
        this.view = view;
    }
    public void setModel(TicTacToe ticTacToe){
        model = ticTacToe;
    }

    /**
     * update the GUI, but do not re-enable any buttons
     */
    public void updateGame(){
        view.updateGUI(model.getGrid(), false);
        view.changeTurnLabelText(turn);
    }

    /**
     * update the GUI, and enable any empty squares(buttons)
     */
    public void enableGame(){
        view.updateGUI(model.getGrid(), true);
        view.changeTurnLabelText(turn);
    }

    /**
     * disable all buttons
     */
    public void disableGame(){
        view.disableGame();
    }

    /**
     * allow game to wait for user to enter a command
     */
    public void waitForClick(){
        while(!isClicked){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isClicked = false;
    }
    /**
     * changes turn
     */
    public void changeTurn(){
        turn = !turn;
    }
    public void setTurn(boolean turn){
        this.turn = turn;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
