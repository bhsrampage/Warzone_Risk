package app.warzone.map;

import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContinentTest {

	private Continent d_continent;
	private Country d_country1;
	private Country d_country2;
	private Player d_player;

	@BeforeEach
	void setUp() {
		d_player = new Player("Test Player");
		d_continent = new Continent(1, "Test Continent", 5);
		d_country1 = new Country(1, "Country 1", d_continent);
		d_country2 = new Country(2, "Country 2", d_continent);
		d_continent.addRemoveMembers(d_country1, true);
	}

	@Test
	void getContinentId() {
		assertEquals(1, d_continent.getContinentId());
	}

	@Test
	void getContinentName() {
		assertEquals("Test Continent", d_continent.getContinentName());
	}

	@Test
	void getArmyBonusCount() {
		assertEquals(5, d_continent.getArmyBonusCount());
	}

	@Test
	void getHolder() {
		assertNull(d_continent.getHolder());
	}

	@Test
	void getMemberCountries() {
		List<Country> l_expectedCountries = new ArrayList<>();
		l_expectedCountries.add(d_country1);
		assertEquals(l_expectedCountries, d_continent.getMemberCountries());
	}

	@Test
	void addRemoveMembers() {
		assertTrue(d_continent.addRemoveMembers(d_country2, true));
		assertTrue(d_continent.getMemberCountries().contains(d_country2));

		assertTrue(d_continent.addRemoveMembers(d_country1, false));
		assertFalse(d_continent.getMemberCountries().contains(d_country1));
	}

	@Test
	void changeHolder() {
		d_continent.changeHolder(d_player);
		assertEquals(d_player, d_continent.getHolder());
	}
}
