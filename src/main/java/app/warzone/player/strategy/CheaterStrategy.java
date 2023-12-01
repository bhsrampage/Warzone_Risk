/**
 * The CheaterStrategy class represents a strategy where the player cheats to gain advantages in the Warzone game.
 * This strategy involves taking over neighboring countries, acquiring continents, and doubling armies on borders with enemy countries.
 *
 * @author Burhanuddin
 */
package app.warzone.player.strategy;

import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.player.orders.Order;

import java.util.ArrayList;

public class CheaterStrategy extends PlayerStrategy {

    /**
     * Constructs a new CheaterStrategy for the specified player.
     *
     * @param p_player The player associated with this strategy.
     */
    public CheaterStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * Checks if the player owns the entire continent of the target country and updates the continent's holder if necessary.
     *
     * @param d_targetCountry The target country to check for continent ownership.
     */
    public void checkIfOwnsContinent(Country d_targetCountry) {
        if (d_targetCountry.getContinentality().getHolder() == d_targetPlayer) return;
        for (Country l_country : d_targetCountry.getContinentality().getMemberCountries()) {
            if (l_country.getCountryHolder() != d_targetPlayer) break;
        }
        d_targetCountry.getContinentality().setD_holder(d_targetPlayer);
    }

    /**
     * Creates a cheating order for the player, involving taking over neighboring countries and doubling armies on borders with enemies.
     *
     * @return The cheating order created for the player.
     */
    @Override
    public Order createOrder() {
        ArrayList<Country> l_tempHolding = new ArrayList<>(d_targetPlayer.d_holdingCountries);
        // Acquire all neighboring countries
        for (Country l_c : l_tempHolding) {
            for (Country l_l_c : l_c.getNeighbouringCountries()) {
                if (l_l_c.getCountryHolder().equals(d_targetPlayer)) continue;
                l_l_c.getCountryHolder().d_holdingCountries.remove(l_l_c);
                l_l_c.assignHolderWithArmies(d_targetPlayer, l_l_c.getCurrentArmyCount());
                d_targetPlayer.d_holdingCountries.add(l_l_c);
                checkIfOwnsContinent(l_l_c);
            }
        }

        // Double armies on countries bordering enemy countries
        for (Country l_c : d_targetPlayer.d_holdingCountries) {
            for (Country l_l_c : l_c.getNeighbouringCountries()) {
                if (l_l_c.getCountryHolder().equals(d_targetPlayer)) continue;
                l_l_c.setD_currentArmyCount(l_l_c.getCurrentArmyCount() * 2);
            }
        }
        return null;
    }

    /**
     * Gets the name of the cheater strategy.
     *
     * @return The name of the strategy ("cheater").
     */
    public String getStrategyName() {
        return "cheater";
    }
}
