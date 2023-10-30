package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdvanceTest {

    private Player player;
    private Country sourceCountry;
    private Country targetCountry;
    private Advance advanceOrder;

    @BeforeEach
    public void setup() {
        player = new Player("Player 1");
        sourceCountry = new Country(0,"Source Country",null);
        targetCountry = new Country(0,"Target Country",null);
        sourceCountry.addRemoveNeighbour(targetCountry,true);
        targetCountry.addRemoveNeighbour(sourceCountry,true);
        advanceOrder = new Advance(player, sourceCountry, targetCountry, 5);
    }

    @Test
    public void testIsValidWithValidOrder() {
        player.addCountryToHolderList(sourceCountry,10);

        assertTrue(advanceOrder.isValid());
    }

    @Test
    public void testIsValidWithInvalidOrder() {
        assertFalse(advanceOrder.isValid());
    }

    @Test
    public void testExecuteValidOrderSameOwner() {
        player.addCountryToHolderList(sourceCountry,10);
        player.addCountryToHolderList(targetCountry,5);

        advanceOrder.execute();

        assertEquals(5, sourceCountry.getCurrentArmyCount());
        assertEquals(10, targetCountry.getCurrentArmyCount());
        assertTrue(advanceOrder.d_isExecuted);
    }

    @Test
    public void testExecuteValidOrderDifferentOwners() {
        player.addCountryToHolderList(sourceCountry,10);
        targetCountry.setD_currentArmyCount(5);
        Player otherPlayer = new Player("Player 2");
        targetCountry.assignHolderWithArmies(otherPlayer,3);

        advanceOrder.execute();

        assertTrue(advanceOrder.d_isExecuted);
    }

    @Test
    public void testGenerateBooleanWithProbability() {
        assertTrue(Advance.generateBooleanWithProbability(1.0));
        assertFalse(Advance.generateBooleanWithProbability(0.0));
    }
}
