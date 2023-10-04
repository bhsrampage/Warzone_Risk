package app.warzone.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.warzone.Main.Phase;

public class GameEngineTest {

	private GameEngine gameEngine;
	private InputStream originalSystemIn;

	@Before
	public void setUp() {
		gameEngine = new GameEngine();
		originalSystemIn = System.in;
	}

	@Test
	public void testListenMapCommands() {
		String userInput = "editcontinent Asia 3 7\nshowmap\nexit\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		gameEngine.listenMapCommands();
	}

	@Test
	public void testListenStartupCommands() {
		// Redirect System.in for testing user input
		String userInput = "loadmap testmap.map\ngameplayer addPlayer TestPlayer\nassigncountries\nshowmap\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		gameEngine.listenStartupCommands();
	}

	@Test
	public void testInitialize() {
		// Redirect System.in for testing user input
		String userInput = "1\n2\n3\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		// Call the initialize method
		gameEngine.initialize();

		// Check if the d_currPhase field is set correctly
		assertEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	@Test
	public void testInitializeInvalidChoice() {
		// Redirect System.in for testing invalid user input
		String userInput = "invalid\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		// Call the initialize method
		gameEngine.initialize();

		// Check if the d_currPhase field is not set to STARTUP
		assertNotEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	@Test
	public void testInitializeQuit() {
		// Redirect System.in for testing user input to quit
		String userInput = "3\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		// Call the initialize method
		gameEngine.initialize();

		// Check if the d_currPhase field is not set to STARTUP
		assertNotEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	@After
	public void tearDown() {
		// Restore the original System.in
		System.setIn(originalSystemIn);
	}
}
