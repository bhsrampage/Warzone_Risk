package app.warzone.player.orders;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Javadoc for the BlockadeTest class.
 */
public class BlockadeTest {
    /**
     * The player for testing.
     */
    private Player d_player;
    /**
     * The country for testing.
     */
    private Country d_country;

    /**
     * Sets up initial state for each test case.
     */
    @BeforeEach
    public void setUp() {
        d_player = new Player("Player1"); // You need to create a Player class if it doesn't exist
        d_country = new Country(1, "Country1", new Continent(1, "Continent1", 8));
        // You need to create a Country class if it doesn't exist
    }

    /**
     * Test case for the Valid method with a blockade card.
     */
    @Test
    public void testIsValidWithBlockadeCard() {
        d_player.d_holdingCards.add("blockade"); // Assuming there's a method to add a card to the player
        d_country.setD_currentArmyCount(2); // Set the army count for testing

        Blockade blockade = new Blockade(d_player, d_country);

        assertFalse(blockade.isValid());
    }

    /**
     * Test case for the Valid method without a blockade card.
     */
    @Test
    public void testIsValidWithoutBlockadeCard() {
        // No blockade card in the player's hand

        Blockade blockade = new Blockade(d_player, d_country);

        assertFalse(blockade.isValid());
    }

    /**
     * Test case for the Valid method with an opponent's country.
     */
    @Test
    public void testIsValidOpponentsCountry() {
        d_player.d_holdingCards.add("blockade");
        d_country.setD_currentArmyCount(2);
        d_player.d_holdingCountries.add(new Country(2, "Country2", new Continent(2, "Continent2", 10))); // Add a country not owned by the player

        Blockade blockade = new Blockade(d_player, d_country);

        assertFalse(blockade.isValid());
    }

    /**
     * Test case for the isValid method with no army in the country.
     */
    @Test
    public void testIsValidNoArmy() {
        d_player.d_holdingCards.add("blockade");
        d_country.setD_currentArmyCount(0); // No army in the country

        Blockade blockade = new Blockade(d_player, d_country);

        assertFalse(blockade.isValid());
    }
}
