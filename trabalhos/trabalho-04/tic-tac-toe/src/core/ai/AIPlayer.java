package core.ai;

import core.game.Game;
import core.model.Player;

/**
 * @author fabio.fischer
 */
public class AIPlayer extends Player{

    /**
     *
     * @param mark
     */
    public AIPlayer(Game.Mark mark) {
        super(mark);
    }
//
//    /**
//     *
//     * @param game
//     */
//    public static void getNextMove(Game game, Player player) {
//        MiniMax.run(player, game, Double.POSITIVE_INFINITY);
//    }
}
