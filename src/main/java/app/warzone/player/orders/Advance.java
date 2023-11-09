package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;

import java.util.Random;

import static app.warzone.game.GameUtils.generateRandomNumber;

/**
 * The Advance class represents an order for a player to advance armies from one
 * country to another in the Warzone game.
 */
public class Advance extends Order {

    Player d_attackingPlayer;
    int d_armyCount;
    Country d_sourceCountry;
    Country d_targetCountry;
   public boolean d_isExecuted;

    /**
     * Constructor for the Advance order.
     *
     * @param p_player            The player issuing the order.
     * @param p_sourcecountry     The source country from which armies are advanced.
     * @param p_targetCountry     The target country to which armies are advanced.
     * @param p_deployArmyCount   The number of armies to advance.
     */
    public Advance(Player p_player, Country p_sourcecountry, Country p_targetCountry, int p_deployArmyCount) {
        d_attackingPlayer = p_player;
        d_armyCount = p_deployArmyCount;
        d_targetCountry = p_targetCountry;
        d_sourceCountry = p_sourcecountry;
        d_isExecuted = false;
    }

    /**
     * Checks if the Advance order is valid.
     *
     * @return true if the order is valid, false otherwise.
     */
    public boolean isValid() {
        // Check for validity conditions and provide error messages if conditions are not met.
        if (d_targetCountry == null || d_sourceCountry == null) {
            System.out.println("Either one of the countries doesn't exist.");
            return false;
        }
        if (!d_attackingPlayer.d_holdingCountries.contains(d_sourceCountry)) {
            System.out.println(d_attackingPlayer.d_playerName + " doesn't hold " + d_sourceCountry.getD_countryName());
            return false;
        }
        if (d_armyCount > d_sourceCountry.getCurrentArmyCount()) {
            System.out.println(d_attackingPlayer.d_playerName + " is trying to advance more armies than the current army count of " + d_sourceCountry.getD_countryName());
            return false;
        }
        if (!d_sourceCountry.getNeighbouringCountries().contains(d_targetCountry)) {
            System.out.println(d_targetCountry.getD_countryName() + " is not adjacent to " + d_sourceCountry.getD_countryName());
            return false;
        }
        if (d_targetCountry == d_sourceCountry) {
            System.out.println(d_targetCountry.getD_countryName() + " cannot be the same as " + d_sourceCountry.getD_countryName());
            return false;
        }
        return true;
    }

    /**
     * Print details of the Advance order.
     */
    public void printOrder() {
        System.out.println("Order Type: Advance\nPlayer: " + d_attackingPlayer.d_playerName +
                "\nSource Country: " + d_sourceCountry.getD_countryName()
                + "\nTarget Country: " + d_targetCountry.getD_countryName() + "\nNumber Of Armies: " + d_armyCount);
        d_attackingPlayer.d_gameUtil.updateLog("Advance\nPlayer: " + d_attackingPlayer.d_playerName +
                "\nSource Country: " + d_sourceCountry.getD_countryName()
                + "\nTarget Country: " + d_targetCountry.getD_countryName() + "\nNumber Of Armies: " + d_armyCount, "order");
    }

    /**
     * Execute the Advance order, including potential battles and transfers of armies between countries.
     */
    @Override
    public void execute() {
        printOrder();
        if (isValid()) {
            if (d_targetCountry.getCountryHolder() == d_sourceCountry.getCountryHolder()) {
                d_sourceCountry.setD_currentArmyCount(d_sourceCountry.getCurrentArmyCount() - d_armyCount);
                d_targetCountry.setD_currentArmyCount(d_targetCountry.getCurrentArmyCount() + d_armyCount);
                System.out.printf("\nMoved %d armies to %s from %s", d_armyCount, d_targetCountry.getD_countryName(), d_sourceCountry.getD_countryName());
                d_attackingPlayer.d_gameUtil.updateLog("\nMoved " + d_armyCount + " armies to " + d_targetCountry.getD_countryName() + " from " + d_sourceCountry.getD_countryName(), "order");
            } else {
                int l_defendingNum = d_targetCountry.getCurrentArmyCount();
                int l_attackingNum = d_armyCount;

                while (l_defendingNum > 0 && l_attackingNum > 0) {
                    if (generateBooleanWithProbability(0.6)) {
                        l_defendingNum--;
                    }
                    if (l_defendingNum == 0) break;
                    if (generateBooleanWithProbability(0.7)) {
                        l_attackingNum--;
                    }
                    if (l_attackingNum == 0) break;
                }

                if (l_defendingNum == 0) {
                    d_sourceCountry.setD_currentArmyCount(d_sourceCountry.getCurrentArmyCount() - d_armyCount);
                    d_attackingPlayer.addCountryToHolderList(d_targetCountry, l_attackingNum);
                    System.out.println(d_attackingPlayer.d_playerName + " has successfully advanced and captured " + d_targetCountry.getD_countryName());
                    d_attackingPlayer.d_gameUtil.updateLog(d_attackingPlayer.d_playerName + " has successfully advanced and captured " + d_targetCountry.getD_countryName(), "effect");
                    d_attackingPlayer.d_holdingCards.add(getCard());
                } else {
                    d_sourceCountry.setD_currentArmyCount(d_sourceCountry.getCurrentArmyCount() - d_armyCount);
                    d_targetCountry.setD_currentArmyCount(l_defendingNum);
                    System.out.println(d_attackingPlayer.d_playerName + " has lost the battle for " + d_targetCountry.getD_countryName());
                    d_attackingPlayer.d_gameUtil.updateLog(d_attackingPlayer.d_playerName + " has lost the battle for " + d_targetCountry.getD_countryName(), "effect");
                }
            }

            d_isExecuted = true;
        }
    }

    /**
     * Generate a boolean value with a given probability.
     *
     * @param p_probability The probability of generating a `true` value.
     * @return `true` with the specified probability, `false` otherwise.
     */
    public static boolean generateBooleanWithProbability(double p_probability) {
        Random l_random = new Random();
        double l_randomValue = l_random.nextDouble(); // Generates a random number between 0.0 (inclusive) and 1.0 (exclusive).

        return l_randomValue < p_probability;
    }

    /**
     * Get a random card from a list of possible cards.
     *
     * @return A randomly selected card.
     */
    public static String getCard() {
        String[] l_cards = {"bomb", "blockade", "airlift", "negotiate"};
        int l_index = generateRandomNumber(0, l_cards.length-1);
        return l_cards[l_index];
    }
}
