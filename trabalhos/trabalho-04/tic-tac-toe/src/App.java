import core.model.Player;
import core.game.Game;

/**
 * Created by fabio.fischer on 22/11/2017.
 */
public class App {
    public static void main(String[] args) {
        Game t = new Game();

        Player p1 = new Player(Game.Mark.X);
        Player p2 = new Player(Game.Mark.O);

        t.newGame(p1, p2);

        try {
            t.newMove(0, 1, p1);
            t.newMove(1, 1, p2);
            t.newMove(0, 0, p1);
            t.newMove(2, 2, p2);
            t.newMove(0, 2, p1);
            t.newMove(1, 2, p2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}
