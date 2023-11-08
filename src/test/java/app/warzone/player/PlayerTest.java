package app.warzone.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import app.warzone.map.Country;
import app.warzone.player.orders.Deploy;
import app.warzone.player.orders.Order;

/**
 * Test class for the Player class, which represents a player in a game. This
 * class contains test cases for the constructor and various player-related
 * methods.
 */
public class PlayerTest {

	private Player d_player;
	private Country d_country;

	/**
	 * Setup method to initialize the Player and Country instances for testing.
	 */
	@BeforeEach
	public void setUp() {
		d_player = new Player("TestPlayer");
		d_country = new Country(0, "TestCountry", null);
	}

	/**
	 * Test case for the constructor of the Player class. This test verifies that
	 * the player is initialized with the correct attributes.
	 */
	@Test
	public void testConstructor() {
		assertEquals("TestPlayer", d_player.d_playerName);
		assertEquals(0, d_player.d_holdingCountries.size());
		assertEquals(0, d_player.d_currentArmyCount);
		assertEquals(0, d_player.getD_givenOrders().size());
	}

	/**
	 * Test case for the addCountryToHolderList method of the Player class. This
	 * test checks if a country can be added to the player's list of holding
	 * countries, and if the country's attributes are updated accordingly.
	 */
	@Test
	public void testAddCountryToHolderList() {
		d_player.addCountryToHolderList(d_country, 5);

		assertEquals(1, d_player.d_holdingCountries.size());
		assertEquals(d_country, d_player.d_holdingCountries.get(0));
		assertEquals(5, d_country.getCurrentArmyCount());
		assertEquals(d_player, d_country.getCountryHolder());
	}

	/**
	 * Test case for the next_order method of the Player class. This test verifies
	 * that the next order is correctly retrieved from the player's list of given
	 * orders, and that the list is updated accordingly.
	 */
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
