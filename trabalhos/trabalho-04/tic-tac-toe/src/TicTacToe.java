/**
 * @author fabio.fischer
 */
public class TicTacToe {

    /**
     *
     */
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

    /**
     *
     */
    public class Player {
        private Mark mark;

        public Player(Mark mark) {
            this.setMark(mark);
        }

        public Mark getMark() {
            return mark;
        }

        public void setMark(Mark mark) {
            this.mark = mark;
        }
    }

    /**
     *
     */
    private Mark[][] game;

    /**
     *
     */
    private Player player;

    /**
     *
     */
    private Mark mark;

    /**
     *
     */
    private Mark winner;

    /**
     *
     */
    public TicTacToe() {
        game = new Mark[3][3];
    }

    /**
     *
     * @param playerMark
     */
    public void newGame(Mark playerMark) {
        player = new Player(playerMark);
        winner = null;

        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                game[i][j] = Mark.EMPTY;
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @throws IllegalArgumentException
     */
    public void newTurn(int x, int y) {
        if (game[x][y] != Mark.EMPTY)
            throw new IllegalArgumentException("This position is already filled, brah");

        newMove(x, y, player.getMark());

        //TODO calcular IA
        //newMove(x, y, mark);



        System.out.println(this.toString());
    }

    /**
     * 
     * @param x
     * @param y
     * @param m
     */
    public void newMove(int x, int y, Mark m) {
        System.out.println(x + " - " + y);

        if (winner == null) {
            if (game[x][y] == Mark.EMPTY) {
                game[x][y] = m;
            }

            // Check column winner
            for(int i = 0; i < game.length; i++){
                if(game[x][i] != m)
                    break;
                if(i == game.length-1){
                    winner = m;
                }
            }

            // Check row winner
            for(int i = 0; i < game.length; i++){
                if(game[i][y] != m)
                    break;
                if(i == game.length-1){
                    winner = m;
                }
            }

            // Check diagonal winner
            if(x == y){
                for(int i = 0; i < game.length; i++){
                    if(game[i][i] != m)
                        break;
                    if(i == game.length-1){
                        winner = m;
                    }
                }
            }

            // Check anti diagonal winner
            if(x + y == game.length){
                for(int i = 0; i <= game.length; i++){
                    if(game[i][game.length-1] != m)
                        break;
                    if(i == game.length-1){
                        winner = m;
                    }
                }
            }

            // Check draw
            int blankCnt = 0;
            for (int i = 0; i < game.length; i++) {
                for (int j = 0; j < game[i].length; j++) {
                    if (game[i][j] == Mark.EMPTY)
                        blankCnt++;
                }
            }
            if (blankCnt == 0)
                winner = Mark.EMPTY;
        }
    }

    public Mark getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                stringBuilder.append(game[i][j].asChar() + "\t");
            }
            stringBuilder.append("\n");
        }
        if (winner != null) {
            stringBuilder.append("\nVencedor: ").append(winner);
        }

        return stringBuilder.toString();
    }
}
