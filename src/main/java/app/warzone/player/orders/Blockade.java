package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.game.GameUtils;

import java.util.ArrayList;

public class Blockade extends Order {

    /**
     * The player who is issuing the Blockade order.
     */
    Player d_player;

    /**
     * The country on which the Blockade order is being applied.
     */
    Country d_country;

    /**
     * Constructs a new Blockade order.
     *
     * @param p_player  The player issuing the order.
     * @param p_country The country on which the order is applied.
     */
    public Blockade(Player p_player, Country p_country) {
        d_player = p_player;

        d_country = p_country;
    }

    /**
     * Checks if the Blockade order is valid.
     *
     * @return true if the order is valid, false otherwise.
     */
    public boolean isValid() {

        if (d_player.d_holdingCards.contains("blockade")) {
            if (d_country.getCurrentArmyCount() > 0) {
                ArrayList<String> l_countriesOwnedList = new ArrayList<>();
                for (Country l_country : d_player.d_holdingCountries) {
                    l_countriesOwnedList.add(l_country.getD_countryName());
                }
                if (!l_countriesOwnedList.contains(d_country.getD_countryName())) {
                    System.out.println(d_player.d_playerName + " cannot use Blockade card since it is opponent's country");
                    return false;
                }
                return true;
            } else {
                System.out.println(d_country.getD_countryName() + " has no army. So you cannot use Blockade card there");
                return false;
            }
        } else {
            System.out.println("Player do not have a blockade card");
            return false;
        }

    }

    /**
     * Executes the Blockade order if it is valid. It triples the number of armies in the country and make it neutral.
     */
    @Override
    public void execute() {
        printOrder();
        if (isValid()) {
            int l_previousArmy = d_country.getCurrentArmyCount();
            System.out.println("Before Blockade Card number of army in " + d_country.getD_countryName() + " is : " + d_country.getCurrentArmyCount());
            GameUtils.updateLog("Before Blockade Card number of army in " + d_country.getD_countryName() + " is : " + d_country.getCurrentArmyCount(), "effect");
            d_country.setD_currentArmyCount((l_previousArmy * 3));
            d_country.assignHolderWithArmies(null,(l_previousArmy * 3));
            System.out.println(d_player.d_playerName + " applied Blockade Card successfully");
            GameUtils.updateLog(d_player.d_playerName + " applied Blockade Card successfully", "effect");
            System.out.println("After Blockade Card number of army in " + d_country.getD_countryName() + " is : " + d_country.getCurrentArmyCount());
            GameUtils.updateLog("After Blockade Card number of army in " + d_country.getD_countryName() + " is : " + d_country.getCurrentArmyCount(), "effect");
            d_player.d_holdingCards.remove("blockade");
            d_player.d_holdingCountries.remove(d_country);

        }
    }

    public void printOrder() {
        System.out.println("Order Type : Blockade \nPlayer : " + d_player.d_playerName + "\n Country to block : " + d_country.getD_countryName());
        GameUtils.updateLog("Blockade \nPlayer : " + d_player.d_playerName + "\n Country to block : " + d_country.getD_countryName(), "order");
    }

}