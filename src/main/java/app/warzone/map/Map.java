package app.warzone.map;

import app.warzone.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents the playable map for the game it consists of the subgraph structure through continent class which in turn
 * implemented using Country class.
 */
public class Map {
    String d_mapName;
    List<Continent> d_continents;
    List<Country> d_countries;

    public Map(String p_name){
        d_mapName = p_name;
        d_continents = new ArrayList<Continent>();
        d_countries = new ArrayList<Country>();
    }

    Country getCountryById(int p_id){
        for(Country l_country : d_countries){
            if(p_id == l_country.d_countryId) {
                return l_country;
            }
        }
        return null;
    }

    public void printMap(Main.Phase p_currPhase){
        for (Continent continent : d_continents ){
            continent.printContinent(p_currPhase == Main.Phase.MAP_ACTIONS);
        }
    }
    public void addContinent(Continent p_newContinent){
        d_continents.add(p_newContinent);
    }

    public void removeContinent(int p_continentId){
        for(Continent l_continent : d_continents){
            if(p_continentId == l_continent.d_continentId) {
                //Cascading deletion of a continent to corresponding member countries
                for(Country l_member: l_continent.getMemberCountries()){
                    l_continent.addRemoveMembers(l_member,false);
                    d_countries.remove(l_member);
                }
                d_continents.remove(l_continent);
                break;
            }
        }
    }

    public void addCountry(Country p_newCountry){
        d_countries.add(p_newCountry);
        p_newCountry.d_memberOfContinent.addRemoveMembers(p_newCountry,true);
    }

    public void removeCountry(int p_countryId){
        Country l_country = getCountryById(p_countryId);
        if(l_country == null) {
            System.err.printf("No country found with id %d", p_countryId);
            return;
        }
        l_country.d_memberOfContinent.addRemoveMembers(l_country,false);
        d_countries.remove(l_country);
    }

    public void addRemoveCountryNeighbour(int p_countryId, int p_neighbourCountryId,boolean isAdding){
        Country l_targetCountry = getCountryById(p_countryId);
        if(l_targetCountry == null) {
            System.err.printf("No country found with id %d", p_countryId);
            return;
        }
        Country l_neighbourToTarget = getCountryById(p_neighbourCountryId);
        if(l_neighbourToTarget == null) {
            System.err.printf("No country found with id %d", p_neighbourCountryId);
            return;
        }
        l_targetCountry.addRemoveNeighbour(l_neighbourToTarget,isAdding);
        l_neighbourToTarget.addRemoveNeighbour(l_targetCountry,isAdding);
        System.out.println(isAdding ? "Addition" : "Removal" + " of neighbour executed");
    }

}
