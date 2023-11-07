package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * AirliftTest class to test the Airlift order implementation.
 */
public class AirliftTest {

    private Airlift d_airlift;
    private Player d_player;
    private Country d_sourceCountry;
    private Country d_targetCountry;

    @Before
    public void setUp() {
        // Initialize the d_player, source country, and target country for testing
        d_player = new Player("TestPlayer");
        d_player.d_holdingCards.add("d_airlift");
        d_sourceCountry = new Country(0, "Source Country", null);
        d_targetCountry = new Country(0, "Target Country", null);

        // Assign source and target countries to the d_player
        d_player.addCountryToHolderList(d_sourceCountry, 10);
        d_player.addCountryToHolderList(d_targetCountry, 10);

        // Create an Airlift order with 10 armies
        d_airlift = new Airlift(d_player, d_sourceCountry, d_targetCountry, 10);
    }

    /**
     * Test case to validate the Airlift order when it is valid.
     */
    @Test
    public void testIsValid() {
        assertTrue(d_airlift.isValid());
    }

    /**
     * Test case to validate the Airlift order when the source country is invalid.
     */
    @Test
    public void testIsValidWithInvalidSourceCountry() {
        d_sourceCountry = null;
        d_airlift = new Airlift(d_player, d_sourceCountry, d_targetCountry, 5);
        assertFalse(d_airlift.isValid());
    }

    /**
     * Test case to validate the Airlift order when the target country is invalid.
     */
    @Test
    public void testIsValidWithInvalidTargetCountry() {
        d_targetCountry = null;
        d_airlift = new Airlift(d_player, d_sourceCountry, d_targetCountry, 5);
        assertFalse(d_airlift.isValid());
    }

    /**
     * Test case to validate the Airlift order when the number of armies is invalid (zero).
     */
    @Test
    public void testIsValidWithInvalidArmyCount() {
        d_airlift = new Airlift(d_player, d_sourceCountry, d_targetCountry, 0);
        assertFalse(d_airlift.isValid());
    }

    /**
     * Test case to execute the Airlift order and validate its effects.
     */
    @Test
    public void testExecute() {
        d_airlift.execute();

        // Check if armies were moved from the source to the target country
        assertEquals(0, d_sourceCountry.getCurrentArmyCount());
        assertEquals(20, d_targetCountry.getCurrentArmyCount());

        // Check if the Airlift card was removed from the d_player's card list
        assertFalse(d_player.d_holdingCards.contains("d_airlift"));
    }
}
