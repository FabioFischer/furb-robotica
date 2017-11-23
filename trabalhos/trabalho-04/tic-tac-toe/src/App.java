
import core.ai.AIPlayer;
import core.ai.MiniMax;
import core.game.HumanPlayer;
import core.game.Game;
import core.model.Player;

/**
 * Created by fabio.fischer on 22/11/2017.
 */
public class App {

    Game t;
    Player p1;
    Player p2;

    public static void miniMax (Game t, Player p) {
        MiniMax.run(p, t, Double.POSITIVE_INFINITY);
    }

    public App() {
        t = new Game();
        p1 = new AIPlayer(Game.Mark.X);
        p2 = new HumanPlayer(Game.Mark.O);
    }

    public void play() {
        t.newGame();
        MiniMax.run(p1, t, Double.POSITIVE_INFINITY);
    }

    public static void main(String[] args) {
        App a = new App();
        a.play();
    }
}
