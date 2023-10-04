package app.warzone.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.warzone.Main.Phase;
//import static org.mockito.Mockito.*;

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
		// Redirect System.in for testing user input
		String userInput = "editcontinent Asia 3 7\nshowmap\nexit\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		gameEngine.listenMapCommands();

		// Check if the MapUtils methods were called as expected
		// You need to use a mocking framework like Mockito to properly test this
		// behavior
	}

	@Test
	public void testListenStartupCommands() {
		// Redirect System.in for testing user input
		String userInput = "loadmap testmap.map\ngameplayer addPlayer TestPlayer\nassigncountries\nshowmap\n";
		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(userInputInputStream);

		gameEngine.listenStartupCommands();

		// Check if the GameUtils methods were called as expected
		// You need to use a mocking framework like Mockito to properly test this
		// behavior
	}

//	@Test
//	public void testListenGameplayCommands() {
//		// Create a mock GameUtils instance
//		GameUtils mockGameUtils = mock(GameUtils.class);
//		Player player = new Player("TestPlayer");
//		player.addCountryToHolderList(new Country(s, "TestCountry", null), 5);
//		mockGameUtils.d_playerList.add(player);
//
//		// Redirect System.in for testing user input
//		String userInput = "deploy TestCountry 3\nexit\n";
//		InputStream userInputInputStream = new ByteArrayInputStream(userInput.getBytes());
//		System.setIn(userInputInputStream);
//
//		// Call the private method listenGameplayCommands using reflection
//		try {
//			java.lang.reflect.Method method = GameEngine.class.getDeclaredMethod("listenGameplayCommands",
//					GameUtils.class);
//			method.setAccessible(true);
//			method.invoke(gameEngine, mockGameUtils);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// Check if the Player's issue_order method was called as expected
//		// You need to use a mocking framework like Mockito to properly test this
//		// behavior
//	}

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
