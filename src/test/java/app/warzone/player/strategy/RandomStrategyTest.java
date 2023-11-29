package app.warzone.player.strategy;

import app.warzone.map.Country;
import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RandomStrategyTest {
    private Player d_player1, d_player2;
    private Country d_country1, d_country2, d_country3;
    private RandomStrategy randomStrategy;

    @BeforeEach
    void setUp() {
        d_player1 = new Player("player_1", "random");
        d_player2 = new Player("player_2", "random");

        d_country1 = new Country(0, "Country1", null);
        d_country2 = new Country(0, "Country2", null);
        d_country3 = new Country(0, "Country3", null);

        d_country1.addRemoveNeighbour(d_country2, true);
        //d_country1.addRemoveNeighbour(d_country3, true);
        d_country2.addRemoveNeighbour(d_country1, true);
        //d_country3.addRemoveNeighbour(d_country1, true);

        d_player1.d_currentArmyCount=10;
        d_player1.d_holdingCards.add("bomb");
        d_player1.d_holdingCards.add("airlift");
    }

    /**
     * This method is to test strategy of random player
     */
    @Test
    public void randomOrderTest() {
        randomStrategy = new RandomStrategy(d_player1);
        d_player1.addCountryToHolderList(d_country1, 10);
        d_player1.addCountryToHolderList(d_country3, 10);
        d_player2.addCountryToHolderList(d_country2, 6);
        // Order order = d_player1.d_strategy.createOrder();
        randomStrategy.createOrder();
        assertTrue(randomStrategy.d_isExecuted);
    }



}