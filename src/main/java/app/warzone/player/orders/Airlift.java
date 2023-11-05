package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;

import java.util.Random;

public class Airlift extends Order {

    Player d_player;
    int d_armyCount;
    Country d_sourceCountry;
    Country d_targetCountry;
    boolean d_isExecuted;

    public Airlift(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armyCount) {
        d_player = p_player;
        d_sourceCountry = p_sourceCountry;
        d_targetCountry = p_targetCountry;
        d_armyCount = p_armyCount;
        d_isExecuted = false;
    }

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

    public void printOrder() {
        System.out.println("Order Type: Airlift\nPlayer: " + d_player.d_playerName +
                "\nSource Country: " + d_sourceCountry.getD_countryName() +
                "\nTarget Country: " + d_targetCountry.getD_countryName() +
                "\nNumber Of Armies: " + d_armyCount);
    }

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