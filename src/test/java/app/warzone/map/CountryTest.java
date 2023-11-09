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

/**
 * Test class for the Country class, which represents a country in a game. This
 * class contains test cases for various methods of the Country class.
 */
public class CountryTest {

    private Country d_country;
    private Continent d_continent;
    private Player d_player;

    /**
     * Setup method to initialize the Continent, Player, and Country instances for
     * testing.
     */
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

    /**
     * Test case for getting the country ID. This test checks if the getCountryId
     * method returns the correct country ID.
     */
    @Test
    void getCountryId() {
        assertEquals(1, d_country.getCountryId());
    }

    /**
     * Test case for getting the continentality of the country. This test verifies
     * that the getContinentality method returns the correct continent for the
     * country.
     */
    @Test
    void getContinentality() {
        assertEquals(d_continent, d_country.getContinentality());
    }

    /**
     * Test case for getting the current army count of the country. This test
     * ensures that the getCurrentArmyCount method returns the correct current army
     * count.
     */
    @Test
    void getCurrentArmyCount() {
        assertEquals(5, d_country.getCurrentArmyCount());
    }

    /**
     * Test case for getting neighboring countries of the country. This test checks
     * if the getNeighbouringCountries method returns the expected list of
     * neighboring countries.
     */
    @Test
    void getNeighbouringCountries() {
        List<Country> l_expectedNeighbors = new ArrayList<>();
        l_expectedNeighbors.add(new Country(2, "Neighbor 1", d_continent));
        l_expectedNeighbors.add(new Country(3, "Neighbor 2", d_continent));

        assertEquals(l_expectedNeighbors.size(), d_country.getNeighbouringCountries().size());
        for (int l_i = 0; l_i < 2; l_i++) {
            Country l_expected = l_expectedNeighbors.get(l_i);
            Country l_observed = d_country.getNeighbouringCountries().get(l_i);
            assertEquals(l_expected.getCountryId(), l_observed.getCountryId());
            assertEquals(l_expected.getD_countryName(), l_observed.getD_countryName());
            assertEquals(l_expected.getContinentality(), l_observed.getContinentality());
        }
    }

    /**
     * Test case for getting the holder of the country. This test checks if the
     * getCountryHolder method returns the correct player who holds the country.
     */
    @Test
    void getCountryHolder() {
        assertEquals(d_player, d_country.getCountryHolder());
    }

    /**
     * Test case for adding and removing neighboring countries from the country.
     * This test verifies that the addRemoveNeighbour method correctly adds and
     * removes neighboring countries.
     */
    @Test
    void addRemoveNeighbour() {
        Country l_newNeighbor = new Country(4, "New Neighbor", d_continent);
        assertTrue(d_country.addRemoveNeighbour(l_newNeighbor, true));
        assertTrue(d_country.getNeighbouringCountries().contains(l_newNeighbor));

        assertTrue(d_country.addRemoveNeighbour(l_newNeighbor, false));
        assertFalse(d_country.getNeighbouringCountries().contains(l_newNeighbor));
    }

    /**
     * Test case for assigning the holder of the country with armies. This test
     * checks if the assignHolderWithArmies method correctly sets the holder and
     * army count of the country.
     */
    @Test
    void assignHolderWithArmies() {
        Player l_newPlayer = new Player("New Player");
        int l_newArmyCount = 10;
        d_country.assignHolderWithArmies(l_newPlayer, l_newArmyCount);

        assertEquals(l_newPlayer, d_country.getCountryHolder());
        assertEquals(l_newArmyCount, d_country.getCurrentArmyCount());
    }
}
