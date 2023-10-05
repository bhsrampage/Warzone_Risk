package app.warzone.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import app.warzone.map.Country;
import app.warzone.player.Player;

/**
 * Test class for the GameUtils class, which provides utility methods for game
 * management. This class contains test cases for the assignment of
 * reinforcement armies to players.
 */
public class GameUtilsTest {

	private GameUtils gameUtil;

	/**
	 * Setup method to initialize the GameUtils instance for testing.
	 */
	@Before
	public void setUp() {
		gameUtil = new GameUtils();
	}

	/**
	 * Test the assignment of reinforcement armies to players. This test case
	 * verifies that the correct number of reinforcement armies is assigned to each
	 * player based on the number of countries they hold.
	 */
	@Test
	public void testAssignReinforcementArmies() {

		Player l_player1 = new Player("Player1");
		Player l_player2 = new Player("Player2");
		gameUtil.d_playerList.add(l_player1);
		gameUtil.d_playerList.add(l_player2);

		// Add countries to Player1
		for (int i = 0; i < 12; i++) {
			Country country = new Country(i, "Country" + (i + 1), null);
			l_player1.addCountryToHolderList(country, 0);
		}

		// Add countries to Player2
		for (int i = 13; i < 15; i++) {
			Country country = new Country(i, "Country" + i, null);
			l_player2.addCountryToHolderList(country, 0);
		}

		// Assign reinforcement armies
		gameUtil.assignReinforcementArmies();

		// Verify the number of reinforcement armies assigned to each player
		assertEquals(4, l_player1.d_currentArmyCount);
		assertEquals(3, l_player2.d_currentArmyCount);
	}
}
