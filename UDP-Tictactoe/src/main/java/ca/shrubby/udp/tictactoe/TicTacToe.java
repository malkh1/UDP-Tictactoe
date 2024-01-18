package ca.shrubby.udp.tictactoe;

import java.io.Serializable;

public class TicTacToe implements Serializable {


    public static int SIZE = 3;
    public static boolean X = true;
    private int turnNumber;
    public enum Status {X_WON, O_WON, TIE, UNDECIDED}

    private final char[][] grid;
    private boolean turn;
    private Status status;


    public TicTacToe() {
        grid = new char[SIZE][SIZE];
        buildGame();

    }

    public char[][] getGrid() {
        return grid;
    }

    private void changeTurn() {
        turn = !turn;
    }

    public Status getStatus() {return status;}



    /**
     * prints text view of board. used for testing.
     * @return text view of board
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < SIZE; ++i){
            for(int j = 0; j < SIZE; ++j){
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * used to build game/reset game
     */
    public void buildGame(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = ' ';
            }
        }
        turn = X;
        status = Status.UNDECIDED;
        turnNumber = 0;

    }
    /**
     * gets turn of player
     * @return turn of player
     */
    public boolean getTurn() {return turn;}

    /**
     * places a piece on the tictactoe board
     * @param x x coordinate for board
     * @param y y coordinate for board
     * @return returns true if play is valid. false otherwise.
     */
    public boolean play(int x, int y) {
        if (grid[x][y] != ' ') return false;
        grid[x][y] = turn ? 'X' : 'O';
        updateStatus();
        changeTurn();
        return true;
    }

    /**
     * determines the status of the game; whether there is a win, loss, tie or if the game is still going
     */
    private void updateStatus() {
        boolean r1xw = grid[0][0] == 'X' && grid[0][1] == 'X' && grid[0][2] == 'X';
        boolean r2xw = grid[1][0] == 'X' && grid[1][1] == 'X' && grid[1][2] == 'X';
        boolean r3xw = grid[2][0] == 'X' && grid[2][1] == 'X' && grid[2][2] == 'X';
        boolean c1xw = grid[0][0] == 'X' && grid[1][0] == 'X' && grid[2][0] == 'X';
        boolean c2xw = grid[0][1] == 'X' && grid[1][1] == 'X' && grid[2][1] == 'X';
        boolean c3xw = grid[0][2] == 'X' && grid[1][2] == 'X' && grid[2][2] == 'X';
        boolean d1xw = grid[0][0] == 'X' && grid[1][1] == 'X' && grid[2][2] == 'X';
        boolean d2xw = grid[0][2] == 'X' && grid[1][1] == 'X' && grid[2][0] == 'X';

        boolean r1ow = grid[0][0] == 'O' && grid[0][1] == 'O' && grid[0][2] == 'O';
        boolean r2ow = grid[1][0] == 'O' && grid[1][1] == 'O' && grid[1][2] == 'O';
        boolean r3ow = grid[2][0] == 'O' && grid[2][1] == 'O' && grid[2][2] == 'O';
        boolean c1ow = grid[0][0] == 'O' && grid[1][0] == 'O' && grid[2][0] == 'O';
        boolean c2ow = grid[0][1] == 'O' && grid[1][1] == 'O' && grid[2][1] == 'O';
        boolean c3ow = grid[0][2] == 'O' && grid[1][2] == 'O' && grid[2][2] == 'O';
        boolean d1ow = grid[0][0] == 'O' && grid[1][1] == 'O' && grid[2][2] == 'O';
        boolean d2ow = grid[0][2] == 'O' && grid[1][1] == 'O' && grid[2][0] == 'O';

        if(r1xw) status = Status.X_WON;
        else if(r2xw)status = Status.X_WON;
        else if(r3xw)status = Status.X_WON;
        else if(c1xw)status = Status.X_WON;
        else if(c2xw)status = Status.X_WON;
        else if(c3xw)status = Status.X_WON;
        else if(d1xw)status = Status.X_WON;
        else if(d2xw)status = Status.X_WON;
        else if(r1ow)status = Status.O_WON;
        else if(r2ow)status = Status.O_WON;
        else if(r3ow)status = Status.O_WON;
        else if(c1ow)status = Status.O_WON;
        else if(c2ow)status = Status.O_WON;
        else if(c3ow)status = Status.O_WON;
        else if(d1ow)status = Status.O_WON;
        else if(d2ow)status = Status.O_WON;
        else if(turnNumber == 8)status = Status.TIE;
        else status = Status.UNDECIDED;

        ++turnNumber;
    }
}
