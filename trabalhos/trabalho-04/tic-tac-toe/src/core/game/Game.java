package core.game;

import core.model.Board;
import core.model.Move;
import core.model.Player;

/**
 * @author fabio.fischer
 */
public class Game {

    public enum Mark {
        O('O'), X('X'), EMPTY('-');

        public char asChar() {
            return asChar;
        }

        private final char asChar;

        private Mark(char asChar) {
            this.asChar = asChar;
        }
    }

    private Board board;

    private Mark winner;

    /**
     *
     */
    public Game() {
        this.board = new Board();
    }

    /**
     *
     * @param player1
     * @param player2
     */
    public Game(Player player1, Player player2) {
        this.board = new Board();
        newGame(player1, player2);
    }

    /**
     *
     * @param playerMark
     */
    public void newGame(Player player1, Player player2) {
        board.clearBoard();
    }

    /**
     *
     * @param x
     * @param y
     * @param m
     */
    public void newMove(int x, int y, Player p) {
        if (getWinner() == null) {
            Move move = new Move(x, y);
            board.newMove(move, p.getMark());
            winner = board.getWinner(move, p.getMark());

            System.out.println(this.toString());
        }
    }

    public Mark getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(board.toString());
        if (winner != null) {
            stringBuilder.append("\nVencedor: ").append(winner);
        }

        return stringBuilder.toString();
    }
}
