package core.ai;

import core.game.Game;
import core.model.Board;
import core.model.Move;
import core.model.Player;

/**
 * @author fabio.fischer
 *
 * TODO Determinate the best play to do on a Tic Tac Toe game by using the Minimax algorith.
 */
public class MiniMax  {

    /**
     *
     */
    private static double maxPly;

    /**
     * MiniMax cannot be instantiated.
     */
    private MiniMax() {}

    /**
     * Execute the algorithm.
     * @param player        the player that the AI will identify as
     * @param board         the Tic Tac Toe board to play on
     * @param maxPly        the maximum depth
     */
     public static void run(Player player, Game game, double maxPly) {
        if (maxPly < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }

        MiniMax.maxPly = maxPly;
        miniMax(player, game, 0);
    }

    private static int miniMax (Player player, Game game, int currentPly) {
        if (currentPly++ == maxPly || game.isOver()) {
            return score(player, game);
        }
        if (game.getTurn() == player.getMark()) {
            return getMax(player, game, currentPly);
        } else {
            return getMin(player, game, currentPly);
        }
    }

    /**
     * Play the move with the highest score.
     */
    private static int getMax (Player player, Game game, int currentPly) {
        double bestScore = Double.NEGATIVE_INFINITY;
        Move bestMove = null;

        for (Move move : game.getBoard().getAvailableMoves()) {
            Game other = game.clone();
            other.newMove(move.getX(), move.getY(), player);

            int score = miniMax(player, other, currentPly);

            if (score >= bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        if (bestMove != null)
            game.newMove(bestMove.getX(), bestMove.getY(), player);
        return (int)bestScore;
    }

    /**
     * Play the move with the lowest score.
     */
    private static int getMin (Player player, Game game, int currentPly) {
        double bestScore = Double.POSITIVE_INFINITY;
        Move bestMove = null;

        for (Move move : game.getBoard().getAvailableMoves()) {
            Game other = game.clone();
            other.newMove(move.getX(), move.getY(), player);

            int score = miniMax(player, other, currentPly);

            if (score <= bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        if (bestMove != null)
            game.newMove(bestMove.getX(), bestMove.getY(), player);
        return (int)bestScore;
    }

    /**
     * Get the score of the board.
     */
    private static int score (Player player, Game game) {
        if (game.isOver() && game.getWinner() == player.getMark()) {
            return 1;
        } else if (game.isOver() && game.getWinner() == ((player.getMark() == Game.Mark.X) ? Game.Mark.O : Game.Mark.X)) {
            return -1;
        } else {
            return 0;
        }
    }
}
