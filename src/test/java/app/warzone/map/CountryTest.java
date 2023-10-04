package app.warzone.map;

import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the Country class, which represents a
 * country on the game map in Warzone.
 */
public class CountryTest {

	private Country d_country;
	private Continent d_continent;
	private Player d_player;

	@BeforeEach
	void setUp() {
		d_continent = new Continent(1, "Test Continent", 5);
		d_player = new Player("Test Player");
		d_country = new Country(1, "Test Country", d_continent);
		d_country.assignHolderWithArmies(d_player, 5);

		Country l_neighbor1 = new Country(2, "Neighbor 1", d_continent);
		Country l_neighbor2 = new Country(3, "Neighbor 2", d_continent);

		d_country.addRemoveNeighbour(l_neighbor1, true);
		d_country.addRemoveNeighbour(l_neighbor2, true);
	}

	@Test
	void getCountryId() {
		assertEquals(1, d_country.getCountryId());
	}

	@Test
	void getContinentality() {
		assertEquals(d_continent, d_country.getContinentality());
	}

	@Test
	void getCurrentArmyCount() {
		assertEquals(5, d_country.getCurrentArmyCount());
	}

	@Test
	void getNeighbouringCountries() {
		List<Country> l_expectedNeighbors = new ArrayList<>();
		l_expectedNeighbors.add(new Country(2, "Neighbor 1", d_continent));
		l_expectedNeighbors.add(new Country(3, "Neighbor 2", d_continent));

		assertEquals(l_expectedNeighbors.size(), d_country.getNeighbouringCountries().size());
		for (int l_i = 0; l_i < 2; l_i++) {
			Country l_expected = l_expectedNeighbors.get(l_i);
			Country l_observed = l_expectedNeighbors.get(l_i);
			assertEquals(l_expected.d_countryId, l_observed.d_countryId);
			assertEquals(l_expected.d_countryName, l_observed.d_countryName);
			assertEquals(l_expected.d_memberOfContinent, l_observed.d_memberOfContinent);
		}
	}

	@Test
	void getCountryHolder() {
		assertEquals(d_player, d_country.getCountryHolder());
	}

	@Test
	void addRemoveNeighbour() {
		Country l_newNeighbor = new Country(4, "New Neighbor", d_continent);
		assertTrue(d_country.addRemoveNeighbour(l_newNeighbor, true));
		assertTrue(d_country.getNeighbouringCountries().contains(l_newNeighbor));

		assertTrue(d_country.addRemoveNeighbour(l_newNeighbor, false));
		assertFalse(d_country.getNeighbouringCountries().contains(l_newNeighbor));
	}

	@Test
	void assignHolderWithArmies() {
		Player l_newPlayer = new Player("New Player");
		int l_newArmyCount = 10;
		d_country.assignHolderWithArmies(l_newPlayer, l_newArmyCount);

		assertEquals(l_newPlayer, d_country.getCountryHolder());
		assertEquals(l_newArmyCount, d_country.getCurrentArmyCount());
	}
}
