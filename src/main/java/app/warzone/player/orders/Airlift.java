package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;

/**
 * Airlift is an order that allows a player to transfer armies from one of their
 * countries to another country they own.
 */
public class Airlift extends Order {

    Player d_player;
    int d_armyCount;
    Country d_sourceCountry;
    Country d_targetCountry;
    boolean d_isExecuted;

    /**
     * Constructor for the Airlift order.
     *
     * @param p_player       The player issuing the order.
     * @param p_sourceCountry The source country from which armies are airlifted.
     * @param p_targetCountry The target country where armies are airlifted to.
     * @param p_armyCount     The number of armies to airlift.
     */
    public Airlift(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armyCount) {
        d_player = p_player;
        d_sourceCountry = p_sourceCountry;
        d_targetCountry = p_targetCountry;
        d_armyCount = p_armyCount;
        d_isExecuted = false;
    }

    /**
     * Validates the Airlift order for correctness.
     *
     * @return True if the order is valid, false otherwise.
     */
    public boolean isValid() {
        if (d_sourceCountry == null || d_targetCountry == null) {
            System.out.println("Either the source or target country doesn't exist.");
            return false;
        }
        if (!d_player.d_holdingCountries.contains(d_sourceCountry)) {
            System.out.println(d_player.d_playerName + " doesn't hold " + d_sourceCountry.getD_countryName());
            return false;
        }
        if (d_armyCount <= 0) {
            System.out.println("Number of armies to airlift must be greater than 0.");
            return false;
        }
        return true;
    }

    /**
     * Prints the details of the Airlift order.
     */
    public void printOrder() {
        System.out.println("Order Type: Airlift\nPlayer: " + d_player.d_playerName +
                "\nSource Country: " + d_sourceCountry.getD_countryName() +
                "\nTarget Country: " + d_targetCountry.getD_countryName() +
                "\nNumber Of Armies: " + d_armyCount);
    }

    /**
     * Executes the Airlift order if it's valid, transferring armies from the source
     * country to the target country.
     */
    @Override
    public void execute() {
        if (isValid()) {
            // Remove armies from the source country
            int sourceArmyCount = d_sourceCountry.getCurrentArmyCount();
            sourceArmyCount -= d_armyCount;
            d_sourceCountry.setD_currentArmyCount(sourceArmyCount);

            // Add armies to the target country
            int targetArmyCount = d_targetCountry.getCurrentArmyCount();
            targetArmyCount += d_armyCount;
            d_targetCountry.setD_currentArmyCount(targetArmyCount);

            System.out.println("Before Airlift Card, the number of armies in " + d_targetCountry.getD_countryName() + " is: " + (targetArmyCount - d_armyCount));

            System.out.println(d_player.d_playerName + " applied Airlift Card successfully");

            System.out.println("After Airlift Card, the number of armies in " + d_targetCountry.getD_countryName() + " is: " + targetArmyCount);

            // Remove the Airlift card from the player's card list
            d_player.d_holdingCards.remove("airlift");

            // Print the order details
            printOrder();
        }
    }
}
