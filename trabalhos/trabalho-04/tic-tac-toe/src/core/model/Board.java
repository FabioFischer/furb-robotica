package core.model;

import core.game.Game;

/**
 * @author fabio.fischer
 */
public class Board {

    /**
     *
     */
    private static final int BOARD_LENGTH = 3;

    /**
     *
     */
    private Game.Mark[][] board;

    /**
     *
     */
    public Board() {
        board = new Game.Mark[BOARD_LENGTH][BOARD_LENGTH];
    }

    /**
     *
     * @param playerMark
     */
    public void clearBoard() {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                board[i][j] = Game.Mark.EMPTY;
            }
        }
    }

    /**
     *
     * @param move
     * @param mark
     */
    public void newMove(Move move, Game.Mark mark) {
            if (board[move.getX()][move.getY()] != Game.Mark.EMPTY)
                throw new IllegalArgumentException("This position is already filled, brah");
            board[move.getX()][move.getY()] = mark;
    }

    /**
     *
     * @param move
     * @param player
     * @return
     */
    private Game.Mark getColumnWinner(Move move, Game.Mark player) {
        for(int i = 0; i < BOARD_LENGTH; i++){
            if(board[move.getX()][i] != player)
                break;
            if(i == BOARD_LENGTH-1){
                return player;
            }
        }
        return null;
    }

    /**
     *
     * @param move
     * @param player
     * @return
     */
    private Game.Mark getRowWinner(Move move, Game.Mark player) {
        for(int i = 0; i < BOARD_LENGTH; i++){
            if(board[i][move.getY()] != player)
                break;
            if(i == BOARD_LENGTH-1){
                return player;
            }
        }
        return null;
    }

    /**
     *
     * @param move
     * @param player
     * @return
     */
    private Game.Mark getLeftDiagonalWinner(Move move, Game.Mark player) {
        if(move.getX() == move.getY()){
            for(int i = 0; i < BOARD_LENGTH; i++){
                if(board[i][i] != player)
                    break;
                if(i == BOARD_LENGTH-1){
                    return player;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param move
     * @param player
     * @return
     */
    private Game.Mark getRightDiagonalWinner(Move move, Game.Mark player) {
        if(move.getX() + move.getY() == BOARD_LENGTH){
            for(int i = 0; i <= BOARD_LENGTH; i++){
                if(board[i][BOARD_LENGTH-1] != player)
                    break;
                if(i == BOARD_LENGTH-1){
                    return player;
                }
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    private Game.Mark getDrawGame() {
        int blankCnt = 0;
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (board[i][j] == Game.Mark.EMPTY)
                    blankCnt++;
            }
        }
        if (blankCnt == 0)
            return Game.Mark.EMPTY;
        return null;
    }

    /**
     *
     * @param move
     * @param player
     * @return
     */
    public Game.Mark getWinner(Move move, Game.Mark player) {
        Game.Mark mark = getColumnWinner(move, player);

        if (mark == null)
            mark = getRowWinner(move, player);
        if (mark == null)
            mark = getLeftDiagonalWinner(move, player);
        if (mark == null)
            mark = getRightDiagonalWinner(move, player);
        if (mark == null)
            mark = getDrawGame();

        return mark;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                stringBuilder.append("[" + board[i][j].asChar() + "]\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
