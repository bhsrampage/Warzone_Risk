package app.warzone.player;

import java.util.ArrayList;
import java.util.List;
import app.warzone.map.*;

public class Player {
    public String d_playerName;
    public List<Country> d_holdingCountries;

    public Player(String p_name){
        d_playerName = p_name;
        d_holdingCountries = new ArrayList<>();
    }

    public void addCountryToHolderList(Country p_country,int p_armyCount){

        p_country.assignHolderWithArmies(this,p_armyCount);
        d_holdingCountries.add(p_country);

    }
}

