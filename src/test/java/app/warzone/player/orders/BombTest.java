package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.game.phase.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BombTest {

    private Player d_player1, d_player2;
    private Country d_country1, d_country2, d_country3;
    private Bomb d_bombOrder;
    Phase d_gamePhase;

    @BeforeEach
    void setUp() {
        d_player1 = new Player("player_1");
        d_player2 = new Player("player_2");
        d_country1 = new Country(0,"Source Country",null);
        d_country2 = new Country(0,"Target1 Country",null);
        d_country3 = new Country(0,"Target2 Country",null);
        d_country1.addRemoveNeighbour(d_country2,true);
        d_country2.addRemoveNeighbour(d_country1,true);
        d_player1.d_holdingCards.add("bomb");
        d_player2.d_holdingCards.add("bomb");
    }

    @Test
    void testIsValidWithValidOrder() {
        d_player1.addCountryToHolderList(d_country1,10);
        d_player2.addCountryToHolderList(d_country2,6);
        d_bombOrder = new Bomb(d_player1, d_country2);
        assertTrue(d_bombOrder.isValid());
    }

    @Test
    void testIsValidWithInvalidOrder() {
        d_player1.addCountryToHolderList(d_country1,10);
        d_bombOrder = new Bomb(d_player1, d_country3);
        assertFalse(d_bombOrder.isValid());
    }

    @Test
    void testExecute() {
        d_bombOrder = new Bomb(d_player1, d_country2);
        d_player1.addCountryToHolderList(d_country1,10);
        d_player2.addCountryToHolderList(d_country2,6);
        d_bombOrder.execute();
        assertEquals(3, d_country2.getCurrentArmyCount());
        assertTrue(d_bombOrder.d_isExecuted);
    }
}