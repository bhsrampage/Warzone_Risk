package app.warzone.player.strategy;

import app.warzone.player.Player;
import app.warzone.player.orders.Deploy;
import app.warzone.player.orders.Order;
import app.warzone.map.Country;

/**
 * The BenevolentStrategy class represents a strategy for a benevolent player in the Warzone game.
 * Benevolent players focus on protecting their weak countries and avoid attacking enemy territories.
 */
public class BenevolentStrategy extends PlayerStrategy {

    /**
     * Constructor for the BenevolentStrategy class.
     *
     * @param p_player The player using this strategy.
     */
    public BenevolentStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * Create an order based on the benevolent strategy.
     * Benevolent players deploy or advance armies on their weakest countries.
     *
     * @return The order created based on the strategy.
     */
    @Override
    public Order createOrder() {
        // Check if there are weak countries owned by the player
        if (!d_targetPlayer.d_holdingCountries.isEmpty()) {
            // Find the weakest country (lowest army count)
            int minArmyCount = Integer.MAX_VALUE;
            Country weakestCountry = null;

            for (Country country : d_targetPlayer.d_holdingCountries) {
                if (country.getCurrentArmyCount() < minArmyCount) {
                    minArmyCount = country.getCurrentArmyCount();
                    weakestCountry = country;
                }
            }

            if (weakestCountry != null) {
                // Deploy armies to the weakest country
                int deployArmyCount = Math.max(1, d_targetPlayer.d_currentArmyCount / 2);
                return new Deploy(d_targetPlayer, deployArmyCount, weakestCountry);
            }
        }

        // If no weak countries found, return null
        return null;
    }

    /**
     * Get the name of the benevolent strategy.
     *
     * @return The name of the strategy.
     */
    @Override
    public String getStrategyName() {
        return "Benevolent";
    }
}
