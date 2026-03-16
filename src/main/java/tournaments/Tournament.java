package tournaments;

import games.Game;
import robots.Robot;
import java.util.*;

public abstract class Tournament {

    protected List<Robot> players;
    protected Game game;
    protected boolean finished = false;

    public Tournament(List<Robot> players, Game game) {
        this.players = players;
        this.game = game;
    }

    public Robot[] main() {
        run();
        List<Robot> sorted = new ArrayList<>(players);
        sorted.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        return sorted.toArray(new Robot[0]);
    }

    public void run() {
        if (finished) return;

        List<Robot[]> matches = getBracket();

        for (Robot[] pair : matches) {
            game.run(pair[0], pair[1]);
        }

        finished = true;
    }

    public boolean checkEnd() {
        return finished;
    }

    public abstract List<Robot[]> getBracket();
}