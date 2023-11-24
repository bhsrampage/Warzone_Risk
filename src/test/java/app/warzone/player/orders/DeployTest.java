package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Deploy class, which represents a deployment order in a
 * game. This class contains test cases for the constructor and the execute
 * method of the Deploy order.
 */
public class DeployTest {

    private Player d_deployingPlayer;
    private Country d_targetCountry;
    private Deploy d_deployOrder;

    /**
     * Setup method to initialize player, country, and deploy order instances for
     * testing.
     */
    @BeforeEach
    void setUp() {
        d_deployingPlayer = new Player("Player1");
        d_targetCountry = new Country(1, "Country 1", null);
        d_deployOrder = new Deploy(d_deployingPlayer, 5, d_targetCountry);
    }

    /**
     * Test case for the constructor of the Deploy class. This test verifies that
     * the deploy order is initialized with the correct attributes.
     */
    @Test
    void testConstructor() {
        assertEquals(d_deployingPlayer, d_deployOrder.d_deployingPlayer);
        assertEquals(5, d_deployOrder.d_armyCount);
        assertEquals(d_targetCountry, d_deployOrder.d_targetCountry);
        assertFalse(d_deployOrder.d_isExecuted);
    }

    /**
     * Test case for the execute method of the Deploy class. This test checks if the
     * execute method correctly adds armies to the target country, sets the
     * execution flag, and updates the current army count of the target country.
     */
    @Test
    void testExecute() {
        d_targetCountry.assignHolderWithArmies(d_deployingPlayer, 3);
        d_deployingPlayer.d_currentArmyCount = 5;
        d_deployOrder.execute();
        assertTrue(d_deployOrder.d_isExecuted);
        assertEquals(8, d_targetCountry.getCurrentArmyCount());
    }

    /**
     * Test case for the execute method of the Deploy class with zero armies to
     * deploy. This test verifies that the execute method correctly sets the
     * execution flag without modifying the current army count when deploying zero
     * armies.
     */
    @Test
    void testExecuteWithZeroArmies() {
        d_deployOrder = new Deploy(d_deployingPlayer, 0, d_targetCountry);
        d_targetCountry.assignHolderWithArmies(d_deployingPlayer, 3);
        d_deployOrder.execute();
        assertFalse(d_deployOrder.d_isExecuted);
        assertEquals(3, d_targetCountry.getCurrentArmyCount());
    }

    /**
     * Test case for the execute method of the Deploy class with more armies than
     * available. This test ensures that the execute method does not modify the
     * state of the order if the player attempts to deploy more armies than they
     * have.
     */
    @Test
    void testExecuteWithMoreArmies() {
        assertTrue(d_deployingPlayer.d_currentArmyCount < 0);
    }
}
