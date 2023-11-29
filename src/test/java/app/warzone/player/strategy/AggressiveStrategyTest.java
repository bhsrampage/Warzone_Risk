package app.warzone.player.strategy;

import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.player.orders.Advance;
import app.warzone.player.orders.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the AggressiveStrategy class.
 */
class AggressiveStrategyTest {

    /**
     * Test the toDefend method for an aggressive player.
     */
    @Test
    void testToDefend() {
        // Create an aggressive player
        Player aggressivePlayer = new Player("AggressivePlayer", "aggressive");

        // Create countries for testing
        Country country1 = new Country(1, "Country1", null);
        Country country2 = new Country(2, "Country2", null);

        // Assign armies to countries
        country1.assignHolderWithArmies(aggressivePlayer, 4);
        country2.assignHolderWithArmies(aggressivePlayer, 2);

        // Create AggressiveStrategy
        AggressiveStrategy aggressiveStrategy = new AggressiveStrategy(aggressivePlayer);

        // Test toDefend method
        Country result = aggressiveStrategy.toDefend();
        assertNull(result, "toDefend() should return null for aggressive player");
    }

    /**
     * Test the toAttackFrom method for an aggressive player.
     */
    @Test
    void testToAttackFrom() {
        // Create an aggressive player
        Player aggressivePlayer = new Player("AggressivePlayer", "aggressive");

        // Create countries for testing
        Country country1 = new Country(1, "Country1", null);
        Country country2 = new Country(2, "Country2", null);

        // Assign armies to countries
        country1.assignHolderWithArmies(aggressivePlayer, 10);
        country2.assignHolderWithArmies(aggressivePlayer, 12); // Changed to 12 for testing

        // Add countries to the aggressive player's holding countries
        aggressivePlayer.addCountryToHolderList(country1, 10);
        aggressivePlayer.addCountryToHolderList(country2, 12);

        // Create AggressiveStrategy
        AggressiveStrategy aggressiveStrategy = new AggressiveStrategy(aggressivePlayer);

        // Test toAttackFrom method
        Country result = aggressiveStrategy.toAttackFrom();
        assertNotNull(result, "toAttackFrom() should return a non-null value");
        assertEquals(country2, result, "Aggressive player should attack from the country with the most armies");
    }
}
