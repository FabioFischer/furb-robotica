/**
 * Created by fabio.fischer on 22/11/2017.
 */
public class App {
    public static void main(String[] args) {
        TicTacToe t = new TicTacToe();

        t.newGame(TicTacToe.Mark.X);

        try {
            t.newTurn(0, 2);
            t.newTurn(1, 2);
            t.newTurn(2, 2);
            t.newTurn(2, 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}
