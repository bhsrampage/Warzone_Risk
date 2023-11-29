package app.warzone.game;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GameEngine class, which is responsible for game
 * management. This class contains test cases for the initialization of the game
 * and user input handling.
 */
public class GameEngineTest {

    private GameEngine gameEngine;
    private InputStream originalSystemIn;

    /**
     * Setup method to initialize the GameEngine and store the original System.in
     * stream.
     */
    @BeforeEach
    public void setUp() {
        gameEngine = new GameEngine();
        originalSystemIn = System.in;
    }

    /**
     * Test the initialization of the game when the user quits. To ensure that the
     * game phase is not set to STARTUP when the user quits.
     */
    @Test
    public void testInitializeQuit() {
        String userInput = "3\n";
        InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(userInputInputStream);
        gameEngine.initialize();
        assertEquals("End", gameEngine.gamePhase.getClass().getSimpleName());
    }

    /**
     * To restore the original System.in stream after testing.
     */
    @AfterEach
    public void tearDown() {
        System.setIn(originalSystemIn);
    }
}
