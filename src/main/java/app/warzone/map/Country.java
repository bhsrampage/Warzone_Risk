package app.warzone.map;

import app.warzone.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Country class that acts as a single node for the subgraph of Continent
 */
public class Country {
    int d_countryId;
    String d_countryName;
    Continent d_memberOfContinent;
    int d_currentArmyCount;
    List<Country> d_neighbours;
    Player d_holder;

    public Player getCountryHolder() {
        return d_holder;
    }
    public Country(int p_id, String p_name, Continent p_continent) {
        d_countryId = p_id;
        d_countryName = p_name;
        d_memberOfContinent = p_continent;
        d_neighbours = new ArrayList<Country>();
        d_currentArmyCount = 0;
        d_holder = null;
    }

    public int getCountryId() {
        return d_countryId;
    }

    public Continent getContinentality() {
        return d_memberOfContinent;
    }

    public int getCurrentArmyCount() {
        return d_currentArmyCount;
    }

    public List<Country> getNeighbouringCountries() {
        return d_neighbours;
    }

    public void printCountryStatus(boolean isMapPhase) {
        System.out.printf("\n**%s**\n",d_countryName);
        //System.out.printf("\nPart of :- *%s*\n",d_memberOfContinent.d_continentName);
        if(!isMapPhase) {
        System.out.printf("Armies present :- %d\n",d_currentArmyCount);
        System.out.printf("Current holder :- %s\n", d_holder == null ? "No holder" : d_holder.d_playerName);
        }
        for(Country neighbour : d_neighbours){
            System.out.printf("%s\t",neighbour.d_countryName);
        }
    }

    public boolean addRemoveNeighbour(Country p_country, boolean p_isAdding){
        if(p_isAdding){
            for(Country neighbour: d_neighbours){
                if(neighbour.d_countryId == p_country.d_countryId){
                    System.err.println(p_country.d_countryName + " already present as neighbour");
                    return false;
                }
            }
            d_neighbours.add(p_country);
            return true;
        } else {
            for (Country neighbour : d_neighbours) {
                if (neighbour.d_countryId == p_country.d_countryId) {
                    d_neighbours.remove(neighbour);
                    return true;
                }
            }
            System.err.println(p_country.d_countryName + " does'nt exist as neighbour");
            return false;
        }
    }

    public void assignHolderWithArmies(Player p_player,int p_armyCount){
        d_holder = p_player;
        d_currentArmyCount = p_armyCount;
    }

}
