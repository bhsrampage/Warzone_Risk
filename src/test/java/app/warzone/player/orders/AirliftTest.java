package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AirliftTest class to test the Airlift order implementation.
 */
public class AirliftTest {

    private Airlift airlift;
    private Player player;
    private Country sourceCountry;
    private Country targetCountry;

    @BeforeEach
    public void setUp() {
        // Initialize the player, source country, and target country for testing
        player = new Player("TestPlayer");
        player.d_holdingCards.add("airlift");
        sourceCountry = new Country(0, "Source Country", null);
        targetCountry = new Country(0, "Target Country", null);

        // Assign source and target countries to the player
        player.addCountryToHolderList(sourceCountry, 10);
        player.addCountryToHolderList(targetCountry, 10);

        // Create an Airlift order with 10 armies
        airlift = new Airlift(player, sourceCountry, targetCountry, 10);
    }

    /**
     * Test case to validate the Airlift order when it is valid.
     */
    @Test
    public void testIsValid() {
        assertTrue(airlift.isValid());
    }

    /**
     * Test case to validate the Airlift order when the source country is invalid.
     */
    @Test
    public void testIsValidWithInvalidSourceCountry() {
        sourceCountry = null;
        airlift = new Airlift(player, sourceCountry, targetCountry, 5);
        assertFalse(airlift.isValid());
    }

    /**
     * Test case to validate the Airlift order when the target country is invalid.
     */
    @Test
    public void testIsValidWithInvalidTargetCountry() {
        targetCountry = null;
        airlift = new Airlift(player, sourceCountry, targetCountry, 5);
        assertFalse(airlift.isValid());
    }

    /**
     * Test case to validate the Airlift order when the number of armies is invalid (zero).
     */
    @Test
    public void testIsValidWithInvalidArmyCount() {
        airlift = new Airlift(player, sourceCountry, targetCountry, 0);
        assertFalse(airlift.isValid());
    }

    /**
     * Test case to execute the Airlift order and validate its effects.
     */
    @Test
    public void testExecute() {
        airlift.execute();

        // Check if armies were moved from the source to the target country
        assertEquals(0, sourceCountry.getCurrentArmyCount());
        assertEquals(20, targetCountry.getCurrentArmyCount());

        // Check if the Airlift card was removed from the player's card list
        assertFalse(player.d_holdingCards.contains("airlift"));
    }
}
