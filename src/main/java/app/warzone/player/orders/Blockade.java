package app.warzone.player.orders;

import app.warzone.map.Map;
import app.warzone.map.Country;
import app.warzone.player.Player;

import java.util.ArrayList;

public class Blockade extends Order {

    Player d_player;
    Country d_country;
    Map d_map;
    String d_countryName;

    public Blockade(Player p_player, String p_countryName) {
        this.d_player = p_player;

        d_country = new Country();
        d_country = d_map.getCountryByName(p_countryName);

        this.d_countryName = p_countryName;
    }

    public boolean valid() {

        if (d_player.d_holdingCards.contains("blockade")) {
            if (d_country.getCurrentArmyCount() > 0) {
                ArrayList<String> l_countriesOwnedList = new ArrayList<>();
                for (Country l_country : d_player.d_holdingCountries) {
                    l_countriesOwnedList.add(l_country.getD_countryName());
                }
                if (!l_countriesOwnedList.contains(d_countryName)) {
                    System.out.println(d_player.d_playerName + " cannot use Blockade card since it is opponent’s country");
                    //d_Log.notify(d_player.getD_PlayerName() + " can not use Blockade card on opponent’s country");
                    return false;
                }
                return true;
            } else {
                System.out.println(d_countryName + " has no army. So you cannot use Blockade card there");
                //d_Log.notify(d_countryName + " has a 0 army so you can not apply Blockade order there");
                return false;
            }


        } else {
            System.out.println("Player do not have a blockade card");
            //d_Log.notify("Player doesnot contain blockade card");
            return false;
        }

    }
    @Override
    public void execute() {

    }
}