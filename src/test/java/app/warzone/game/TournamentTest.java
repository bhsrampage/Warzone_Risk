package app.warzone.game;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest {

    /**
     * Tests the validateTournamentCommands method with valid input parameters.
     */
    @Test
    void validateTournamentCommands_Valid() {
        Tournament tournament = new Tournament();
        List<String> arguments = Arrays.asList("-M", "map1.txt", "-P", "strategy1", "strategy2", "-G", "3", "-D", "20");

        boolean result = tournament.validateTournamentCommands(arguments);

        assertTrue(result);
    }

    /**
     * Tests the validateTournamentCommands method with invalid input parameters.
     */
    @Test
    void validateTournamentCommands_Invalid() {
        Tournament tournament = new Tournament();
        List<String> arguments = Arrays.asList("-M", "map1.txt", "-P", "strategy1", "-G", "0", "-D", "60");

        boolean result = tournament.validateTournamentCommands(arguments);

        assertFalse(result);
    }

    /**
     * Tests the automaticGame method with an invalid map, expecting an exception to be thrown.
     */
    @Test
    void automaticGame_InvalidMap() {
        Tournament tournament = new Tournament();
        tournament.d_maximumTurns = 15;
        tournament.d_ge = new GameEngine();

        assertThrows(Exception.class, () -> tournament.automaticGame("invalidMap.txt"));
    }
}

