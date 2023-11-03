package app.warzone.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.warzone.player.Player;

/**
 * Test class for the Continent class, which represents a continent in a game.
 * This class contains test cases for various methods of the Continent class.
 */
public class ContinentTest {

	private Continent d_continent;
	private Country d_country1;
	private Country d_country2;
	private Player d_player;

	/**
	 * Setup method to initialize the Continent, Country, and Player instances for
	 * testing.
	 */
	@BeforeEach
	void setUp() {
		d_player = new Player("Test Player");
		d_continent = new Continent(1, "Test Continent", 5);
		d_country1 = new Country(1, "Country 1", d_continent);
		d_country2 = new Country(2, "Country 2", d_continent);
		d_continent.addRemoveMembers(d_country1, true);
	}

	/**
	 * Test case for getting the continent ID. This test checks if the
	 * getContinentId method returns the correct continent ID.
	 */
	@Test
	void getContinentId() {
		assertEquals(1, d_continent.getContinentId());
	}

	/**
	 * Test case for getting the continent name. This test verifies that the
	 * getContinentName method returns the correct continent name.
	 */
	@Test
	void getContinentName() {
		assertEquals("Test Continent", d_continent.getContinentName());
	}

	/**
	 * Test case for getting the army bonus count. This test ensures that the
	 * getArmyBonusCount method returns the correct army bonus count.
	 */
	@Test
	void getArmyBonusCount() {
		assertEquals(5, d_continent.getArmyBonusCount());
	}

	/**
	 * Test case for getting the continent holder. This test checks if the getHolder
	 * method returns null when no player holds the continent.
	 */
	@Test
	void getHolder() {
		assertNull(d_continent.getHolder());
	}

	/**
	 * Test case for getting the member countries of the continent. This test
	 * verifies that the getMemberCountries method returns the expected list of
	 * member countries.
	 */
	@Test
	void getMemberCountries() {
		List<Country> l_expectedCountries = new ArrayList<>();
		l_expectedCountries.add(d_country1);
		assertEquals(l_expectedCountries, d_continent.getMemberCountries());
	}

	/**
	 * Test case for adding and removing member countries from the continent. This
	 * test checks if the addRemoveMembers method correctly adds and removes
	 * countries from the continent.
	 */
	@Test
	void addRemoveMembers() {
		assertTrue(d_continent.addRemoveMembers(d_country2, true));
		assertTrue(d_continent.getMemberCountries().contains(d_country2));

		assertTrue(d_continent.addRemoveMembers(d_country1, false));
		assertFalse(d_continent.getMemberCountries().contains(d_country1));
	}

	/**
	 * Test case for changing the holder of the continent. This test verifies that
	 * the changeHolder method correctly sets the player as the holder of the
	 * continent.
	 */
	@Test
	void changeHolder() {
		d_continent.changeHolder(d_player);
		assertEquals(d_player, d_continent.getHolder());
	}
}
