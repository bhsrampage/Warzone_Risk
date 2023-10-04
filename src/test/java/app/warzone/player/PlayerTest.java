package app.warzone.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import app.warzone.map.Country;
import app.warzone.player.orders.Deploy;
import app.warzone.player.orders.Order;

public class PlayerTest {

	private Player d_player;
	private Country d_country;

	@Before
	public void setUp() {
		d_player = new Player("TestPlayer");
		d_country = new Country(0, "TestCountry", null);
	}

	@Test
	public void testConstructor() {
		assertEquals("TestPlayer", d_player.d_playerName);
		assertEquals(0, d_player.d_holdingCountries.size());
		assertEquals(3, d_player.d_currentArmyCount);
		assertEquals(0, d_player.getD_givenOrders().size());
	}

	@Test
	public void testAddCountryToHolderList() {
		d_player.addCountryToHolderList(d_country, 5);
		assertEquals(1, d_player.d_holdingCountries.size());
		assertEquals(d_country, d_player.d_holdingCountries.get(0));
		assertEquals(5, d_country.getCurrentArmyCount());
		assertEquals(d_player, d_country.getCountryHolder());
	}

	@Test
	public void testNextOrder() {
		assertNull(d_player.next_order());

		d_player.getD_givenOrders().add(new Deploy(d_player, 3, d_country));
		Order nextOrder = d_player.next_order();

		assertNotNull(nextOrder);
		assertTrue(nextOrder instanceof Deploy);
		assertEquals(0, d_player.getD_givenOrders().size());
	}
}
