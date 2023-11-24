package app.warzone.player.strategy;

import app.warzone.player.Player;
import app.warzone.player.orders.Order;
import app.warzone.map.Country;
import app.warzone.player.orders.Advance;
import app.warzone.player.orders.Airlift;
import app.warzone.player.orders.Deploy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BenevolentStrategy extends PlayerStrategy{
    public BenevolentStrategy(Player p_player) {
        super(p_player);
    }

    @Override
    public Order createOrder() {
        int randomOrder = new Random().nextInt(3) + 1;
        switch (randomOrder) {
            case 1:
                return deployOnWeakestCountry();
            case 2:
                return advanceToWeakestCountry();
            case 3:
                if (d_targetPlayer.d_holdingCards.contains("Airlift")) {
                    return airliftToWeakestCountry();
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * Deploy armies on the weakest country owned by the player.
     */
    private Order deployOnWeakestCountry() {
        Country weakestCountry = getWeakestCountry();
        if (weakestCountry != null) {
            int d_deployArmyCount = new Random().nextInt(d_targetPlayer.d_currentArmyCount) + 1;
            return new Deploy(d_targetPlayer, d_deployArmyCount, weakestCountry);
        }
        return null;
    }

    /**
     * Airlift armies to the weakest country owned by the player.
     *
     * @return The airlift order.
     */
    private Order airliftToWeakestCountry() {
        Country l_weakestCountry = getWeakestCountry();
        if (l_weakestCountry != null) {
            Country l_strongNeighbor = getStrongNeighbor(l_weakestCountry);
            if (l_strongNeighbor != null) {
                int d_airliftArmyCount = new Random().nextInt(l_strongNeighbor.getCurrentArmyCount()) + 1;
                return new Airlift(d_targetPlayer, l_strongNeighbor, l_weakestCountry, d_airliftArmyCount);
            }
        }
        return null;
    }

    /**
     * Advance armies to reinforce the weakest country.
     */
    private Order advanceToWeakestCountry() {
        Country l_weakestCountry = getWeakestCountry();
        if (l_weakestCountry != null) {
            Country l_strongNeighbor = getStrongNeighbor(l_weakestCountry);
            if(l_strongNeighbor != null) {
                int d_advanceArmyCount = new Random().nextInt(l_strongNeighbor.getCurrentArmyCount()) + 1;
                return new Advance(d_targetPlayer, l_strongNeighbor, l_weakestCountry, d_advanceArmyCount);
            }
        }
        return null;
    }

    /**
     * Get the weakest country owned by the player.
     *
     * @return Weakest country or null if no countries owned
     */
    private Country getWeakestCountry() {
        List<Country> playerCountries = new ArrayList<>(d_targetPlayer.d_holdingCountries);
        if (!playerCountries.isEmpty()) {
            // Sort countries based on the number of armies in ascending order
            playerCountries.sort(Comparator.comparingInt(Country::getCurrentArmyCount));
            return playerCountries.get(0); // The first country is the weakest
        }
        return null;
    }

    /**
     * Get a strong neighbor of a given country.
     *
     * @param p_country The country to find a strong neighbor for
     * @return Strong neighbor or null if no strong neighbors
     */
    private Country getStrongNeighbor(Country p_country) {
        for (Country l_neighbor : p_country.getNeighbouringCountries()) {
            if (!l_neighbor.equals(p_country) && l_neighbor.getCurrentArmyCount() > 1) {
                return l_neighbor;
            }
        }
        return null;
    }

    public String getStrategyName() {
        return "benevolent";
    }
}