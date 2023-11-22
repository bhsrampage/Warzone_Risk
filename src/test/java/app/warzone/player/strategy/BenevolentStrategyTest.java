package app.warzone.player.strategy;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.player.orders.Order;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;/**
 * Test class for the BenevolentStrategy implementation.
 */
public class BenevolentStrategyTest {

    private Player testPlayer;

    /**
     * Set up method executed before each test.
     * Initializes a test player with the BenevolentStrategy.
     */
    @Before
    public void setUp() {
        testPlayer = new Player("TestPlayer", "benevolent");
        testPlayer.d_strategy = new BenevolentStrategy(testPlayer);
    }

    /**
     * Test case for the createOrder method of BenevolentStrategy.
     * Verifies that the created order is an instance of Deploy and is valid.
     */
    @Test
    public void testBenevolentStrategyCreateOrder() {
        // Test the createOrder method of BenevolentStrategy
        testPlayer.d_holdingCountries.add(new Country(1, "TestCountry", new Continent(1, "TestContinent", 3)));
        Order order = testPlayer.d_strategy.createOrder();

        // The order should be an instance of Deploy
        assertTrue(order instanceof app.warzone.player.orders.Deploy);

        // Ensure that the order is valid
        assertTrue(order.isValid());
    }

    /**
     * Test case for the isValid method of the Advance order.
     * Verifies that creating an Advance order between two countries in the player's holding list is valid.
     */
    @Test
    public void testAdvanceOrderIsValid() {
        // Create two countries
        Continent continent = new Continent(1, "TestContinent", 3);
        Country sourceCountry = new Country(1, "SourceCountry", continent);
        Country targetCountry = new Country(2, "TargetCountry", continent);

        // Add the countries to the player's holding list
        testPlayer.d_holdingCountries.add(sourceCountry);
        testPlayer.d_holdingCountries.add(targetCountry);

        // Deploy armies to the source country
        int initialArmyCount = 5;
        sourceCountry.assignHolderWithArmies(testPlayer, initialArmyCount);

        // Attempt to create an Advance order
        app.warzone.player.orders.Order order = new app.warzone.player.orders.Advance(testPlayer, sourceCountry, targetCountry, 3);

        // The order should be valid
        assertTrue(order.isValid());
    }

    /**
     * Test case for the isValid method of the Advance order.
     * Verifies that creating an Advance order without adding the countries to the player's holding list is invalid.
     */
    @Test
    public void testAdvanceOrderIsInvalid() {
        // Create two countries
        Continent continent = new Continent(1, "TestContinent", 3);
        Country sourceCountry = new Country(1, "SourceCountry", continent);
        Country targetCountry = new Country(2, "TargetCountry", continent);

        // Attempt to create an Advance order without adding the countries to the player's holding list
        app.warzone.player.orders.Order order = new app.warzone.player.orders.Advance(testPlayer, sourceCountry, targetCountry, 3);

        // The order should be invalid
        assertFalse(order.isValid());
    }
}