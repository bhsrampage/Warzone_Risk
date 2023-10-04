package app.warzone.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.warzone.Main.Phase;

/**
 * This class contains unit tests for the GameEngine class, which is responsible
 * for managing the Warzone game's rules and flow of gameplay.
 */
public class GameEngineTest {

	private GameEngine gameEngine;
	private InputStream originalSystemIn;

	/**
	 * Set up the test environment by initializing the GameEngine and storing the
	 * original System.in stream.
	 */
	@Before
	public void setUp() {
		gameEngine = new GameEngine();
		originalSystemIn = System.in;
	}

	/**
	 * Test the 'listenMapCommands' method to simulate user input for map editing
	 * commands.
	 */
	@Test
	public void testListenMapCommands() {
		String userInput = "editcontinent Asia 3 7\nshowmap\nexit\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		gameEngine.listenMapCommands();
	}

	/**
	 * Test the 'listenStartupCommands' method to simulate user input for game
	 * startup commands.
	 */
	@Test
	public void testListenStartupCommands() {
		String userInput = "loadmap testmap.map\ngameplayer addPlayer TestPlayer\nassigncountries\nshowmap\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		gameEngine.listenStartupCommands();
	}

	/**
	 * Test the 'initialize' method to simulate user input for choosing game phases.
	 */
	@Test
	public void testInitialize() {
		String userInput = "1\n2\n3\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);
		gameEngine.initialize();
		assertEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	/**
	 * Test the 'initialize' method when an invalid choice is provided by the user.
	 */
	@Test
	public void testInitializeInvalidChoice() {
		String userInput = "invalid\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);
		gameEngine.initialize();
		assertNotEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	/**
	 * Test the 'initialize' method when the user chooses to quit.
	 */
	@Test
	public void testInitializeQuit() {
		String userInput = "3\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);
		gameEngine.initialize();
		assertNotEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	/**
	 * Clean up the test environment by restoring the original System.in.
	 */
	@After
	public void tearDown() {
		System.setIn(originalSystemIn);
	}
}
