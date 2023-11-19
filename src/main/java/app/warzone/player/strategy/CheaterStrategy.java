package app.warzone.player.strategy;

import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.player.orders.Order;

public class CheaterStrategy extends PlayerStrategy {
    public CheaterStrategy(Player p_player) {
        super(p_player);
    }

    public void checkIfOwnsContinent(Country d_targetCountry) {
        if (d_targetCountry.getContinentality().getHolder() == d_targetPlayer) return;
        for (Country l_country : d_targetCountry.getContinentality().getMemberCountries()) {
            if (l_country.getCountryHolder() != d_targetPlayer) break;
        }
        d_targetCountry.getContinentality().setD_holder(d_targetPlayer);
    }

    @Override
    public Order createOrder() {

        //Acquire all neighbouring countries
        for (Country l_c : d_targetPlayer.d_holdingCountries){
            for (Country l_l_c : l_c.getNeighbouringCountries()){
                if (l_l_c.getCountryHolder().equals(d_targetPlayer)) continue;
                l_l_c.getCountryHolder().d_holdingCountries.remove(l_l_c);
                l_l_c.assignHolderWithArmies(d_targetPlayer,l_l_c.getCurrentArmyCount());
                d_targetPlayer.d_holdingCountries.add(l_l_c);
                checkIfOwnsContinent(l_l_c);
            }
        }

    //Double armies on countries bordering enemy countries
        for (Country l_c : d_targetPlayer.d_holdingCountries){
            for (Country l_l_c : l_c.getNeighbouringCountries()){
                if (l_l_c.getCountryHolder().equals(d_targetPlayer)) continue;
                l_l_c.setD_currentArmyCount(l_l_c.getCurrentArmyCount()*2);
            }
        }
        return null;
    }
}
