package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AirliftTest {

    private Airlift airlift;
    private Player player;
    private Country sourceCountry;
    private Country targetCountry;

    @Before
    public void setUp() {
        // Initialize the player, source country, and target country for testing
        player = new Player("TestPlayer");
        sourceCountry = new Country(0, "Source Country", null);
        targetCountry = new Country(0, "Target Country", null);

        // Assign source and target countries to the player
        player.addCountryToHolderList(sourceCountry, 10);
        player.addCountryToHolderList(targetCountry, 10);

        // Create an Airlift order with 10 armies
        airlift = new Airlift(player, sourceCountry, targetCountry, 10);
    }

    @Test
    public void testIsValid() {
        assertTrue(airlift.isValid());
    }

    @Test
    public void testIsValidWithInvalidSourceCountry() {
        sourceCountry = null;
        airlift = new Airlift(player, sourceCountry, targetCountry, 5);
        assertFalse(airlift.isValid());
    }

    @Test
    public void testIsValidWithInvalidTargetCountry() {
        targetCountry = null;
        airlift = new Airlift(player, sourceCountry, targetCountry, 5);
        assertFalse(airlift.isValid());
    }

    @Test
    public void testIsValidWithInvalidArmyCount() {
        airlift = new Airlift(player, sourceCountry, targetCountry, 0);
        assertFalse(airlift.isValid());
    }

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