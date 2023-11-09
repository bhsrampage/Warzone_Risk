package app.warzone.game;

import app.warzone.map.Continent;
import app.warzone.map.Map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import app.warzone.map.Country;
import app.warzone.player.Player;

/**
 * Test class for the GameUtils class, which provides utility methods for game
 * management. This class contains test cases for the assignment of
 * reinforcement armies to players.
 */
public class GameUtilsTest {

    private GameUtils gameUtil;

    /**
     * Setup method to initialize the GameUtils instance for testing.
     */
    @BeforeEach
    public void setUp() {
        gameUtil = new GameUtils();
        GameUtils.d_currTargetMap = new Map("Test");
    }

    /**
     * Test the assignment of reinforcement armies to players. This test case
     * verifies that the correct number of reinforcement armies is assigned to each
     * player based on the number of countries they hold.
     */
    @Test
    public void testAssignReinforcementArmies() {

        Player l_player1 = new Player("Player1");
        Player l_player2 = new Player("Player2");
        Player l_player3 = new Player("Player3");
        Continent l_continent = new Continent(1, "Test", 5);
        GameUtils.d_currTargetMap.addContinent(l_continent);

        GameUtils.d_playerList.add(l_player1);
        GameUtils.d_playerList.add(l_player2);
        GameUtils.d_playerList.add(l_player3);


        // Add countries to Player1
        for (int i = 0; i < 12; i++) {
            Country country = new Country(i, "Country" + (i + 1), null);
            l_player1.addCountryToHolderList(country, 0);
        }

        // Add countries to Player2
        for (int i = 13; i < 15; i++) {
            Country country = new Country(i, "Country" + i, null);
            l_player2.addCountryToHolderList(country, 0);
        }

        for (int i = 15; i < 18; i++) {
            Country country = new Country(i, "Country" + i, l_continent);
            l_continent.addRemoveMembers(country, true);
            l_player3.addCountryToHolderList(country, 0);
        }
        l_continent.setD_holder(l_player3);
        // Assign reinforcement armies
        gameUtil.assignReinforcementArmies();

        // Verify the number of reinforcement armies assigned to each player
        assertEquals(4, l_player1.d_currentArmyCount);
        assertEquals(3, l_player2.d_currentArmyCount);
        assertEquals(5, l_player3.d_currentArmyCount);
    }
}
