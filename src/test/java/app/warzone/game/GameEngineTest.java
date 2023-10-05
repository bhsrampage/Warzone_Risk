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
	public void testInitialize() {
		String userInput = "1\n2\n3\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);
		gameEngine.initialize();
		assertEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	@Test
	public void testInitializeInvalidChoice() {
		String userInput = "invalid\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);
		gameEngine.initialize();
		assertNotEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	@Test
	public void testInitializeQuit() {
		String userInput = "3\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);
		gameEngine.initialize();
		assertNotEquals(Phase.STARTUP, gameEngine.getD_currPhase());
	}

	@After
	public void tearDown() {
		System.setIn(originalSystemIn);
	}
}
