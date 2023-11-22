package app.warzone.player.strategy;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.player.orders.Advance;
import app.warzone.player.orders.Deploy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the CheaterStrategy implementation.
 */
public class CheaterStrategyTest {

    private Player testPlayer;

    /**
     * Set up method executed before each test.
     * Initializes a test player with the CheaterStrategy.
     */
    @Before
    public void setUp() {
        testPlayer = new Player("TestPlayer", "cheater");
        testPlayer.d_strategy = new CheaterStrategy(testPlayer);
    }

    /**
     * Test case for the createOrder method of CheaterStrategy.
     * Verifies that the CheaterStrategy creates the expected Deploy and Advance orders.
     */
    @Test
    public void testCheaterStrategyCreateOrder() {
        // Create test countries and continents
        Continent continent1 = new Continent(1, "Continent1", 3);
        Country sourceCountry = new Country(1, "SourceCountry", continent1);
        Country enemyCountry1 = new Country(2, "EnemyCountry1", continent1);
        Country enemyCountry2 = new Country(3, "EnemyCountry2", continent1);
        testPlayer.d_holdingCountries.add(sourceCountry);

        // Set up neighboring relationships
        sourceCountry.addRemoveNeighbour(enemyCountry1, true);
        sourceCountry.addRemoveNeighbour(enemyCountry2, true);

        // Deploy armies on enemy countries and invoke CheaterStrategy
        enemyCountry1.assignHolderWithArmies(new Player("EnemyPlayer1", "aggressive"), 3);
        enemyCountry2.assignHolderWithArmies(new Player("EnemyPlayer2", "benevolent"), 2);
        testPlayer.d_strategy.createOrder();

        // Verify the expected changes
        // 1. Deploy order on the acquired enemy country
        assertTrue(testPlayer.d_strategy.createOrder() instanceof Deploy);
        // 2. Advance order to double armies on the bordering enemy countries
        assertTrue(testPlayer.d_strategy.createOrder() instanceof Advance);
        assertEquals(6, enemyCountry1.getCurrentArmyCount());
        assertEquals(4, enemyCountry2.getCurrentArmyCount());
    }

    /**
     * Test case for the getStrategyName method of CheaterStrategy.
     * Verifies that the correct strategy name is returned.
     */
    @Test
    public void testCheaterStrategyGetStrategyName() {
        assertEquals("cheater", testPlayer.d_strategy.getStrategyName());
    }
}

