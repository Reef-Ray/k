import games.PrisonersDilemmaGame;
import robots.*;
import tournaments.RoundRobinTournament;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AllTests {

    static class StaticRobot extends Robot {
        private final String move;

        public StaticRobot(String name, String move) {
            super(name);
            this.move = move;
        }

        @Override
        public String getAction(String enemyName) {
            return move;
        }
    }

    @Test
    void pdg_cooperateCooperate_yields3and3() {
        PrisonersDilemmaGame game = new PrisonersDilemmaGame(1);
        StaticRobot r1 = new StaticRobot("A","Cooperate");
        StaticRobot r2 = new StaticRobot("B","Cooperate");

        game.run(r1, r2);

        assertEquals(1, r1.getHistory().size());
        assertEquals(1, r2.getHistory().size());
        assertEquals(3, r1.getScore());
        assertEquals(3, r2.getScore());
    }

    @Test
    void pdg_defectCooperate_yields5and0() {
        PrisonersDilemmaGame game = new PrisonersDilemmaGame(1);
        StaticRobot r1 = new StaticRobot("A","Defect");
        StaticRobot r2 = new StaticRobot("B","Cooperate");

        game.run(r1, r2);

        assertEquals(5, r1.getScore());
        assertEquals(0, r2.getScore());
    }

    @Test
    void pdg_invalidMoves_handledCorrectly() {
        PrisonersDilemmaGame game = new PrisonersDilemmaGame(1);
        StaticRobot r1 = new StaticRobot("A","X");
        StaticRobot r2 = new StaticRobot("B","X");

        game.run(r1, r2);

        assertEquals(0, r1.getScore());
        assertEquals(0, r2.getScore());
    }

    @Test
    void copyBot_defaultsToCooperate_whenNoHistory() {
        CopyBot c = new CopyBot("C");
        String action = c.getAction("X");
        assertEquals("Cooperate", action);
    }

    @Test
    void copyBot_copiesMostRecentEnemyMove() {
        CopyBot c = new CopyBot("C");
        c.addHistory(new History("X","C","Defect","Cooperate", new int[]{5,0}));
        assertEquals("Defect", c.getAction("X"));
    }

    @Test
    void defectBot_alwaysDefect() {
        DefectBot d = new DefectBot("D");
        assertEquals("Defect", d.getAction("any"));
    }

    @Test
    void randomBot_returnsAllowedMove() {
        RandomBot r = new RandomBot("R");
        String a = r.getAction("x");
        assertTrue(a.equals("Cooperate") || a.equals("Defect"));
    }

    @Test
    void getScore_sumsHistoryProperly() {
        CopyBot c = new CopyBot("C");
        c.addHistory(new History("C","A","Cooperate","Cooperate", new int[]{3,3}));
        c.addHistory(new History("C","B","Defect","Cooperate", new int[]{5,0}));
        assertEquals(8, c.getScore());
    }

    @Test
    void roundRobin_bracketSizeMatchesCombinationCount() {
        List<Robot> players = Arrays.asList(
                new DefectBot("A"),
                new CopyBot("B"),
                new RandomBot("C"),
                new DefectBot("D")
        );

        RoundRobinTournament t = new RoundRobinTournament(players, new PrisonersDilemmaGame(1));
        List<Robot[]> bracket = t.getBracket();

        assertEquals(6, bracket.size());
    }

    @Test
    void tournament_run_marksFinished_and_returnsSortedResults() {
        List<Robot> players = Arrays.asList(
                new DefectBot("A"),
                new DefectBot("B")
        );

        RoundRobinTournament t = new RoundRobinTournament(players, new PrisonersDilemmaGame(1));
        assertFalse(t.checkEnd());
        t.run();
        assertTrue(t.checkEnd());

        Robot[] results = t.main();
        assertEquals(2, results.length);
    }
}
