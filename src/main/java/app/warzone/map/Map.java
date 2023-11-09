package app.warzone.map;

import java.util.ArrayList;
import java.util.List;

/**
 * The Map class represents a game map, which includes continents and countries.
 *
 * @author Burhanuddin
 */
public class Map {
    /**
     * The name of the map.
     */
    String d_mapName;

    /**
     * The list of continents in the map.
     */
    List<Continent> d_continents;

    /**
     * The list of countries in the map.
     */
    List<Country> d_countries;

    public List<Country> getD_countries() {
        return d_countries;
    }

    /**
     * Constructs a Map object with the given name.
     *
     * @param p_name The name of the map.
     */
    public Map(String p_name) {
        d_mapName = p_name;
        d_continents = new ArrayList<>();
        d_countries = new ArrayList<>();
    }

    /**
     * Gets a country by its unique identifier.
     *
     * @param p_id The unique identifier of the country.
     * @return The country with the specified identifier, or null if not found.
     */
    Country getCountryById(int p_id) {
        for (Country l_country : d_countries) {
            if (p_id == l_country.d_countryId) {
                return l_country;
            }
        }
        return null;
    }

    /**
     * Gets a country by its name.
     *
     * @param p_name The name of the country.
     * @return The country with the specified name, or null if not found.
     */
    public Country getCountryByName(String p_name) {
        for (Country l_country : d_countries) {
            if (p_name.equals(l_country.d_countryName)) {
                return l_country;
            }
        }
        return null;
    }

    /**
     * Gets a continent by its unique identifier.
     *
     * @param p_id The unique identifier of the continent.
     * @return The continent with the specified identifier, or null if not found.
     */
    Continent getContinentById(int p_id) {
        for (Continent l_continent : d_continents) {
            if (l_continent == null)
                continue;
            if (p_id == l_continent.d_continentId) {
                return l_continent;
            }
        }
        return null;
    }

    /**
     * Gets a continent by its name.
     *
     * @param p_name The name of the continent.
     * @return The continent with the specified name, or null if not found.
     */
    public Continent getContinentByName(String p_name) {
        for (Continent l_continent : d_continents) {
            if (l_continent == null)
                continue;
            if (p_name.equals(l_continent.d_continentName)) {
                return l_continent;
            }
        }
        return null;
    }

    public List<Continent> getD_continents() {
        return d_continents;
    }

    /**
     * Prints the map, including its continents and countries.
     *
     * @param isMapPhase Indicates if it's the map phase or not.
     */
    public void printMap(boolean isMapPhase) {
        for (Continent continent : d_continents) {
            if (continent == null)
                continue;
            continent.printContinent(isMapPhase);
        }
    }

    /**
     * Adds a continent to the map.
     *
     * @param p_newContinent The continent to add.
     */
    public void addContinent(Continent p_newContinent) {
        d_continents.add(p_newContinent);
    }

    /**
     * Removes a continent from the map by its name.
     *
     * @param p_name The name of the continent to remove.
     */
    public void removeContinentByName(String p_name) {
        Continent l_target = getContinentByName(p_name);
        if (l_target == null) {
            System.out.println("No continent found");
            return;
        }
        List<Country> l_memberCopy = new ArrayList<>(l_target.getMemberCountries());
        for (Country l_member : l_memberCopy) {
            l_target.addRemoveMembers(l_member, false);
            d_countries.remove(l_member);
            for (Country l_country : d_countries) {
                l_country.d_neighbours.remove(l_member);
            }
        }
        d_continents.set(d_continents.indexOf(l_target), null);
    }

    /**
     * Adds a new country to the map.
     *
     * @param p_newCountry The country object to be added to the map.
     */
    public void addCountry(Country p_newCountry) {
        d_countries.add(p_newCountry);
        p_newCountry.d_memberOfContinent.addRemoveMembers(p_newCountry, true);
    }

    /**
     * Removes a country from the map by its name.
     *
     * @param p_name The name of the country to remove.
     */
    public void removeCountryByName(String p_name) {
        Country l_targetCountry = getCountryByName(p_name);
        if (l_targetCountry == null) {
            System.err.printf("No country found '%s'", p_name);
            return;
        }
        l_targetCountry.d_memberOfContinent.addRemoveMembers(l_targetCountry, false);
        d_countries.remove(l_targetCountry);
        for (Country l_country : d_countries) {
            l_country.d_neighbours.remove(l_targetCountry);
        }
        System.out.println(p_name + " has been removed.");
    }

    /**
     * Adds or removes a neighbor for a country in the map.
     *
     * @param p_countryName          The name of the target country.
     * @param p_neighbourCountryName The name of the neighbor country.
     * @param isAdding               True to add a neighbor, false to remove.
     */
    public void addRemoveCountryNeighbourByName(String p_countryName, String p_neighbourCountryName, boolean isAdding) {
        Country l_targetCountry = getCountryByName(p_countryName);
        if (l_targetCountry == null) {
            System.err.printf("No country found '%s'", p_countryName);
            return;
        }
        Country l_neighbourToTarget = getCountryByName(p_neighbourCountryName);
        if (l_neighbourToTarget == null) {
            System.err.printf("No country found with id %s", p_neighbourCountryName);
            return;
        }
        l_targetCountry.addRemoveNeighbour(l_neighbourToTarget, isAdding);
        l_neighbourToTarget.addRemoveNeighbour(l_targetCountry, isAdding);
        System.out.println(isAdding ? "Addition" : "Removal" + " of neighbour executed");
    }

}