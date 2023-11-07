package app.warzone.player.orders;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdvanceTest {

    private Player d_player;
    private Country d_d_targetCountry;
    private Country targetCountry;
    private Advance d_advanceOrder;

    private Continent d_continent;

    @BeforeEach
    public void setup() {
        d_player = new Player("d_player 1");
        d_continent = new Continent(1,"Test",5);
        d_d_targetCountry = new Country(0,"Source Country",d_continent);
        targetCountry = new Country(0,"Target Country",d_continent);
        d_d_targetCountry.addRemoveNeighbour(targetCountry,true);
        targetCountry.addRemoveNeighbour(d_d_targetCountry,true);
        d_advanceOrder = new Advance(d_player, d_d_targetCountry, targetCountry, 5);
    }

    @Test
    public void testIsValidWithValidOrder() {
        d_player.addCountryToHolderList(d_d_targetCountry,10);

        assertTrue(d_advanceOrder.isValid());
    }

    @Test
    public void testIsValidWithInvalidOrder() {
        assertFalse(d_advanceOrder.isValid());
    }

    @Test
    public void testExecuteValidOrderSameOwner() {
        d_player.addCountryToHolderList(d_d_targetCountry,10);
        d_player.addCountryToHolderList(targetCountry,5);

        d_advanceOrder.execute();

        assertEquals(5, d_d_targetCountry.getCurrentArmyCount());
        assertEquals(10, targetCountry.getCurrentArmyCount());
        assertTrue(d_advanceOrder.d_isExecuted);
    }

    @Test
    public void testExecuteValidOrderDifferentOwners() {
        d_player.addCountryToHolderList(d_d_targetCountry,10);
        targetCountry.setD_currentArmyCount(5);
        Player d_otherplayer = new Player("d_player 2");
        targetCountry.assignHolderWithArmies(d_otherplayer,3);

        d_advanceOrder.execute();

        assertTrue(d_advanceOrder.d_isExecuted);
    }

    @Test
    public void testExecuteLosingPlayer() {
        d_player.addCountryToHolderList(d_d_targetCountry,10);
        targetCountry.setD_currentArmyCount(5);
        Player d_otherplayer = new Player("d_player 2");
        targetCountry.assignHolderWithArmies(d_otherplayer,3);

        d_advanceOrder.execute();

        assertTrue(d_advanceOrder.d_isExecuted);
        assertTrue(d_otherplayer.d_hasLost);
    }

    @Test
    public void testExecuteCapturingContinent() {
        d_player.addCountryToHolderList(d_d_targetCountry,10);
        targetCountry.setD_currentArmyCount(5);
        Player d_otherplayer = new Player("d_player 2");
        targetCountry.assignHolderWithArmies(d_otherplayer,3);
        d_advanceOrder.execute();
        assertTrue(d_advanceOrder.d_isExecuted);
        assertEquals(d_continent.getHolder(),d_player);
    }
    @Test
    public void testGenerateBooleanWithProbability() {
        assertTrue(Advance.generateBooleanWithProbability(1.0));
        assertFalse(Advance.generateBooleanWithProbability(0.0));
    }
}
