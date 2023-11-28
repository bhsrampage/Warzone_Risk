package app.warzone.player.strategy;
import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.player.orders.Advance;
import app.warzone.player.orders.Deploy;
import app.warzone.player.orders.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Aggressive Player Strategy Class.
 *
 * This strategy focuses on centralization of forces and then attack. It deploys on its strongest country, always
 * attacks with its strongest country, and then moves its armies to maximize aggregation of forces in one country.
 *
 */
public class AggressiveStrategy extends PlayerStrategy {
    int d_flag = 0;
    static int l_numOfArmies;

    /**
     * Instantiates a new Aggressive Player strategy.
     *
     * @param p_player Player Object
     */
    public AggressiveStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * Decide the country to attack.
     * Aggressive Player always attacks with its strongest country.
     *
     * @return target country
     */
    protected Country toAttack() {
        d_flag = 0;
        Country l_myMaxArmies = toAttackFrom();

        if (l_myMaxArmies == null || l_myMaxArmies.getNeighbouringCountries() == null) {
            return null;
        }

        for (Country l_country : l_myMaxArmies.getNeighbouringCountries()) {
            if (!l_country.getCountryHolder().d_playerName.equals(d_targetPlayer.d_playerName)) {
                return l_country;
            }
        }

        d_flag = 1;
        ArrayList<Country> l_toAttack = new ArrayList<>();

        for (Country l_country : d_targetPlayer.d_holdingCountries) {
            for (Country l_adjacentCountry : l_country.getNeighbouringCountries()) {
                if (l_adjacentCountry.getCountryHolder() != d_targetPlayer) {
                    l_toAttack.add(l_adjacentCountry);
                }
            }
        }

        List<Country> l_newList = l_toAttack.stream().distinct().collect(Collectors.toList());
        int l_random = new Random().nextInt(l_newList.size());
        return l_newList.get(l_random);
    }

    /**
     * Decide which country should be defended.
     *
     * @return current player country with the most number of armies
     */
    protected Country toDefend() {
        return null;
    }

    /**
     * Decide where to launch an attack from.
     *
     * @return Country to attack from
     */
    protected Country toAttackFrom() {
        // Add null check for toAttackFrom and ensure target player has holding countries
        if (d_targetPlayer.d_holdingCountries.isEmpty()) {

            return null;
        }
        Country l_myMaxArmies = d_targetPlayer.d_holdingCountries.get(new Random().nextInt(d_targetPlayer.d_holdingCountries.size()));
        List<Country> l_countryList = d_targetPlayer.d_holdingCountries;
        if (l_myMaxArmies != null) {
            for (Country l_tempCountry : l_countryList) {
                if (l_myMaxArmies.getCurrentArmyCount() < l_tempCountry.getCurrentArmyCount()) {
                    l_myMaxArmies = l_tempCountry;
                }
            }
        }
        return l_myMaxArmies;
    }

    /**
     * Decide where armies are moved to.
     *
     * @return null
     */
    protected Country toMoveFrom() {
        return null;
    }

    /**
     * Creates an order for Aggressive Player and calls advanced method for attack.
     *
     * @return null
     */
    @Override
    public Order createOrder() {
        Random l_rand = new Random();
        int l_rndOrder = l_rand.nextInt(4);

        Country l_toAttackFrom, l_toAttack;

        l_toAttackFrom = toAttackFrom();
        l_toAttack = toAttack();

        l_numOfArmies = l_toAttackFrom.getCurrentArmyCount() - 1;

        if (d_targetPlayer.d_currentArmyCount > 0) {
            d_targetPlayer.d_currentArmyCount = 0;
            return new Deploy(d_targetPlayer, d_targetPlayer.d_currentArmyCount, l_toAttackFrom);
        }

        if (d_flag == 1) {
            for (Country l_country : d_targetPlayer.d_holdingCountries) {
                if (l_country.getNeighbouringCountries().contains(l_toAttack)) {
                    return new Advance(d_targetPlayer, l_toAttackFrom, l_country, l_numOfArmies);
                }
            }
        }

        if (l_numOfArmies > 0 || l_toAttackFrom.getNeighbouringCountries().contains(l_toAttack)) {
            return new Advance(d_targetPlayer, l_toAttackFrom, l_toAttack, l_numOfArmies);
        }

        System.out.println("\nAggressive Player max army country : " + l_toAttackFrom + "  " + l_toAttackFrom.getCurrentArmyCount());
        System.out.println("\nAggressive Player to attack country : " + l_toAttack + "  " + l_toAttack.getCurrentArmyCount());

        return null;
    }


    @Override
    public String getStrategyName() {
        return "aggressive";
    }
}












