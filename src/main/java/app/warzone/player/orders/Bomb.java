package app.warzone.player.orders;

import java.util.ArrayList;
import app.warzone.map.Country;
import app.warzone.player.Player;

/**
 * The Bomb class represents an order for a player to bomb an adjacent country in the Warzone game.
 */
public class Bomb extends Order {
    Country d_country;
    Player d_player;
    public boolean d_isExecuted;

    /**
     * constructor for Bomb class
     *
     * @param p_player    The player issuing the order.
     * @param p_country   The target country which has to be bombed.
     */

    public Bomb(Player p_player, Country p_country) {
        d_country = p_country;
        d_player = p_player;
        d_isExecuted = false;
    }

    /**
     * Checks whether the Bomb order can be executed or not.
     *
     * @return true if the order is valid, false otherwise.
     */
    public boolean isValid() {
        if (d_player.d_holdingCards.contains("bomb")) {
            if (d_country.getCurrentArmyCount() > 0) {
                ArrayList<String> l_countriesList = new ArrayList<>();
                ArrayList<String> l_adjacentList = new ArrayList<>();
                for (Country l_country : d_player.d_holdingCountries) {
                    l_countriesList.add(l_country.getD_countryName());
                }

                for (Country l_country : d_player.d_holdingCountries) {
                    for (Country l_adjCountry : l_country.getNeighbouringCountries()) {
                        l_adjacentList.add(l_adjCountry.getD_countryName());
                    }
                }
                if (!l_adjacentList.contains(d_country.getD_countryName())) {
                    System.out.println(d_country.getD_countryName() + " is not adjacent with any of " + d_player.d_playerName + "'s territories");
                    return false;
                }

                if (l_countriesList.contains(d_country.getD_countryName())) {
                    System.out.println(d_player.d_playerName + " cannot bomb their own territory");
                    return false;
                }

                return true;
            } else {
                System.out.println(d_country.getD_countryName() + " has 0 armies so it cannot be bombed");
                return false;
            }


        } else {
            System.out.println("Player needs to hold the bomb card in order to execute this order");
            return false;
        }
    }

    /**
     * Print details of the Bomb order.
     */
    public void printOrder() {
        System.out.println("Order Type : Bomb \nPlayer : " + d_player.d_playerName + " Target Country : " + d_country.getD_countryName() + " \nSuccessfully Executed\n");
        d_player.d_gameUtil.updateLog("Bomb \nPlayer : " + d_player.d_playerName + " Target Country : " + d_country.getD_countryName() + " \nSuccessfully Executed\n", "order");
   }

    /**
     * Execute the Bomb order and reduce the opponent's army count in that country.
     */
    public void execute() {
        printOrder();
        if (isValid()) {
            int l_initialArmy = d_country.getCurrentArmyCount();
            System.out.println(d_country.getD_countryName() + "'s army count before deploying the bomb : " + l_initialArmy);
            d_player.d_gameUtil.updateLog(d_country.getD_countryName() + "'s army count before deploying the bomb : " + l_initialArmy, "effect");
            d_country.setD_currentArmyCount(l_initialArmy/2);
            System.out.println(d_player.d_playerName + " applied Bomb Card successfully");
            d_player.d_gameUtil.updateLog(d_player.d_playerName + " applied Bomb Card successfully", "effect");
            System.out.println(d_country.getD_countryName() + "'s army count after deploying the bomb : " + d_country.getCurrentArmyCount());
            d_player.d_gameUtil.updateLog(d_country.getD_countryName() + "'s army count after deploying the bomb : " + d_country.getCurrentArmyCount(), "effect");
            d_player.d_holdingCards.remove("bomb");
            d_isExecuted = true;
        }
    }
}
