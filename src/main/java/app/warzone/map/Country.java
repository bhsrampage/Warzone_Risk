package app.warzone.map;

import app.warzone.player.Player;

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

    public Country(int p_id, String p_name, Continent p_continent, List<Country> p_neighbours) {
        d_countryId = p_id;
        d_countryName = p_name;
        d_memberOfContinent = p_continent;
        d_neighbours = p_neighbours;
        d_currentArmyCount = 0;
    }

    public Continent getD_memberOfContinent() {
        return d_memberOfContinent;
    }
}
