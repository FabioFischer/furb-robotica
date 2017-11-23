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

    private Mark turn;

    /**
     *
     */
    public Game() {
        this.board = new Board();
    }


    /**
     *
     * @param playerMark
     */
    public void newGame() {
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
            turn = p.getMark();
            System.out.println(this.toString());
        }
    }

    /**
     *
     * @return
     */
    public Game clone() {
        Game other = new Game();
        other.board = this.board;
        other.winner = this.winner;
        other.turn = this.turn;
        return other;
    }

    /**
     *
     * @return
     */
    public Mark getWinner() {
        return winner;
    }

    /**
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * @return
     */
    public Mark getTurn() {
        return turn;
    }

    /**
     *
     * @return
     */
    public boolean isOver() {
        return winner != null;
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
