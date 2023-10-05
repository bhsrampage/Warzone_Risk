package app.warzone.player.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.player.Player;

public class DeployTest {

	private Player d_deployingPlayer;
	private Country d_targetCountry;
	private Deploy d_deployOrder;
	private Continent d_continent;

	@BeforeEach
	void setUp() {
		d_deployingPlayer = new Player("Player1");
		d_targetCountry = new Country(1, "Country 1", d_continent);
		d_deployOrder = new Deploy(d_deployingPlayer, 5, d_targetCountry);
	}

	@Test
	void testConstructor() {
		assertEquals(d_deployingPlayer, d_deployOrder.d_deployingPlayer);
		assertEquals(5, d_deployOrder.d_armyCount);
		assertEquals(d_targetCountry, d_deployOrder.d_targetCountry);
		assertFalse(d_deployOrder.d_isExecuted);
	}

	@Test
	void testExecute() {
		d_targetCountry.assignHolderWithArmies(d_deployingPlayer, 3);
		d_deployOrder.execute();
		assertTrue(d_deployOrder.d_isExecuted);
		assertEquals(8, d_targetCountry.getCurrentArmyCount());
	}

	@Test
	void testExecuteWithZeroArmies() {
		d_deployOrder = new Deploy(d_deployingPlayer, 0, d_targetCountry);
		d_targetCountry.assignHolderWithArmies(d_deployingPlayer, 3);
		d_deployOrder.execute();
		assertTrue(d_deployOrder.d_isExecuted);
		assertEquals(3, d_targetCountry.getCurrentArmyCount());
	}

	@Test
	void testExecuteWithMoreArmies() {
		d_deployOrder = new Deploy(d_deployingPlayer, 0, d_targetCountry);
		d_targetCountry.assignHolderWithArmies(d_deployingPlayer, 5);
		d_deployOrder.execute();
		assertFalse(d_deployOrder.d_isExecuted);
	}

}
