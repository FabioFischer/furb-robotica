package core.model;

import core.game.Game;

/**
 * @author fabio.fischer
 */
public abstract class Player {
    private Game.Mark mark;

    public Player(Game.Mark mark) {
        this.setMark(mark);
    }

    public Game.Mark getMark() {
        return mark;
    }

    public void setMark(Game.Mark mark) {
        this.mark = mark;
    }
}
