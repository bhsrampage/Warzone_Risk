package app.warzone.player.strategy;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.map.Map;
import app.warzone.map.MapUtils;
import app.warzone.player.Player;
import app.warzone.player.orders.Advance;
import app.warzone.player.orders.Airlift;
import app.warzone.player.orders.Deploy;
import app.warzone.player.orders.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Javadoc for the BenevolentStrategyTest class.
 *
 * This class contains JUnit test cases for the BenevolentStrategy class.
 * It ensures that the strategies implemented in the BenevolentStrategy class
 * are functioning as expected.
 */
public class BenevolentStrategyTest {

    private BenevolentStrategy d_benevolentStrategy;

    /**
     * Set up the test environment before each test case.
     * It initializes the BenevolentStrategy object with a player and performs
     * any necessary setup for testing.
     *
     * @throws Exception if there is an issue during setup.
     */
    @Before
    public void setUp() throws Exception {
        // Create a MapUtils instance and set up a player for testing
        MapUtils l_Map = new MapUtils();
        String[] l_args = {"canada"};
        Player l_player = new Player("player_1", "benevolent");

        // Initialize the BenevolentStrategy object
        d_benevolentStrategy = new BenevolentStrategy(l_player);
    }

    /**
     * Test the getStrategyName method to ensure it returns the correct strategy name.
     * It checks if the strategy name retrieved from BenevolentStrategy matches the expected value.
     */
    @Test
    public void testGetStrategyName() {
        assertEquals("benevolent", d_benevolentStrategy.getStrategyName());
    }
}
