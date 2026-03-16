import games.Game;
import games.PrisonersDilemmaGame;
import listeners.MoveListener;
import listeners.ScoreListener;
import robots.CopyBot;
import robots.DefectBot;
import robots.RandomBot;
import robots.Robot;
import tournaments.RoundRobinTournament;
import tournaments.Tournament;
import java.util.Arrays;
import java.util.List;
// import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Robot r1 = new DefectBot("Viole");
        Robot r2 = new CopyBot("Khun");
        Robot r3 = new RandomBot("Rak");
        Robot r4 = new DefectBot("Joe");
        Robot r5 = new CopyBot("Kiryu");
        Robot r6 = new RandomBot("John Nioh");
        Robot r7 = new CopyBot("Commander Shepard");
        Robot r8 = new RandomBot("Gyro Zeppeli");

        List<Robot> robots = Arrays.asList(r1, r2, r3, r4, r5, r6, r7, r8);

        Game game = new PrisonersDilemmaGame(5);

        game.addMoveListener(new MoveListener());
        game.addScoreListener(new ScoreListener());

        Tournament tournament = new RoundRobinTournament(robots, game);

        tournament.run();
    }
}
