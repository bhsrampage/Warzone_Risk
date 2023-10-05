package app.warzone.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import app.warzone.map.Country;
import app.warzone.player.Player;

public class GameUtilsTest {

	private GameUtils gameUtil;

	@Before
	public void setUp() {
		gameUtil = new GameUtils();
	}

	@Test
	public void testAssignReinforcementArmies() {

		Player l_player1 = new Player("Player1");
		Player l_player2 = new Player("Player2");
		gameUtil.d_playerList.add(l_player1);
		gameUtil.d_playerList.add(l_player2);

		for (int i = 0; i < 12; i++) {
			Country country = new Country(i, "Country" + (i + 1), null);
			l_player1.addCountryToHolderList(country, 0);
		}

		for (int i = 13; i < 15; i++) {
			Country country = new Country(i, "Country" + i, null);
			l_player2.addCountryToHolderList(country, 0);
		}
		gameUtil.assignReinforcementArmies();

		assertEquals(4, l_player1.d_currentArmyCount);
		assertEquals(3, l_player2.d_currentArmyCount);
	}
}
