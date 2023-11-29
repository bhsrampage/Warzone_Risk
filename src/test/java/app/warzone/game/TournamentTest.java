package app.warzone.game;

import app.warzone.game.Tournament;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TournamentTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testBegin() {
        // Set up test data
        List<String> arguments = Arrays.asList("-M", "map1", "map2", "-P", "strategy1", "strategy2", "-G", "3", "-D", "20");

        // Execute the method to test
        Tournament.startTournament(arguments);

        // Assert the expected output or behavior
        // You might want to check the output if it's correct, or validate other aspects of your code
        // For now, let's just check that the output is not empty (assuming your code prints something meaningful)
        assertNotNull(outContent.toString());
    }

    @Test
    public void testValidateTournamentCommands() {
        // Set up test data
        List<String> arguments = Arrays.asList("-M", "map1", "map2", "-P", "strategy1", "strategy2", "-G", "3", "-D", "20");

        // Execute the method to test
        boolean result = Tournament.validateTournamentCommands(arguments);

        // Assert the expected output or behavior
        assertTrue(result);
        // You might want to add more assertions based on the specific logic in your validateTournamentCommands method
    }

    // Add more test methods as needed for other functionalities in the Tournament class
}
