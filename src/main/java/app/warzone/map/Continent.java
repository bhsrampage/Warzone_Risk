package app.warzone.map;

import app.warzone.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Continent class that represents the subgraph of an entire map Continents interconnect to for a map.
 */
public class Continent {
    int d_continentId;
    String d_continentName;
    int d_armyBonusCount;
    List<Country> d_memberCountries;
    Player d_holder;


    public int getContinentId() {
        return d_continentId;
    }

    public String getContinentName() {
        return d_continentName;
    }

    public int getArmyBonusCount() {
        return d_armyBonusCount;
    }

    public Player getHolder() {
        return d_holder;
    }

    public List<Country> getMemberCountries() {
        return d_memberCountries;
    }

    public Continent(int p_Id, String p_name, int p_bonusCount){
        d_continentId = p_Id;
        d_continentName = p_name;
        d_armyBonusCount = p_bonusCount;
        d_memberCountries = new ArrayList<Country>();
        d_holder = null;
    }

    public void printContinent(boolean p_isMapPhase){
        System.out.printf("\n\n***%s***\n",d_continentName);
        if(d_holder != null && !p_isMapPhase) System.out.printf("The holder of %s is %s\n",d_continentName,d_holder.d_playerName);
        for(Country members: d_memberCountries){
            members.printCountryStatus(p_isMapPhase);
        }
    }

    public boolean addRemoveMembers(Country p_country, boolean p_isAdding){
        if(p_isAdding){
            for(Country member: d_memberCountries){
                if(member.d_countryId == p_country.d_countryId){
                    System.err.println(p_country.d_countryName + " already present as member");
                    return false;
                }
            }
            d_memberCountries.add(p_country);
            return true;
        } else {
            for (Country neighbour : d_memberCountries) {
                if (neighbour.d_countryId == p_country.d_countryId) {
                    d_memberCountries.remove(neighbour);
                    return true;
                }
            }
            System.err.println(p_country.d_countryName + " does'nt exist as neighbour");
            return false;
        }
    }
    public void changeHolder(Player p_newHolder){
        d_holder = p_newHolder;
    }

}
