package app.warzone.map;

import app.warzone.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The Country class represents a country in the game map.
 * @author Burhanuddin
 */
public class Country {
    /**
     * The unique identifier for the country.
     */
    int d_countryId;


    /**
     * The name of the country.
     */
    String d_countryName;
    /**
     * The continent to which this country belongs.
     */
    Continent d_memberOfContinent;
    /**
     * The current army count in the country.
     */
    int d_currentArmyCount;
    /**
     * List of neighboring countries.
     */
    List<Country> d_neighbours;
    /**
     * The player who currently holds the country.
     */
    Player d_holder;
    /**
     * The country node is processed during bfs travel.
     */
    boolean d_isProcessed;

    public String getD_countryName() {
        return d_countryName;
    }
    /**
     * Constructs a Country object with the given parameters.
     *
     * @param p_id        The unique identifier for the country.
     * @param p_name      The name of the country.
     * @param p_continent The continent to which this country belongs.
     */
    public Country(int p_id, String p_name, Continent p_continent) {
        d_countryId = p_id;
        d_countryName = p_name;
        d_memberOfContinent = p_continent;
        d_neighbours = new ArrayList<>();
        d_currentArmyCount = 0;
        d_holder = null;
    }

    /**
     * Getter for country id
     *
     * @return Country ID
     */
    public int getCountryId() {
        return d_countryId;
    }

    /**
     * Getter for countries parent continent
     *
     * @return Continent object
     * @see app.warzone.map.Continent
     */
    public Continent getContinentality() {
        return d_memberOfContinent;
    }

    /**
     * Getter for current army count in the country
     *
     * @return Count of Army
     */
    public int getCurrentArmyCount() {
        return d_currentArmyCount;
    }

    /**
     * Getter for All Neighbouring countries
     *
     * @return List of all the countries that are the neighbours
     * @see app.warzone.map.Country
     */
    public List<Country> getNeighbouringCountries() {
        return d_neighbours;
    }

    /**
     * Getter for Player that holds the country
     *
     * @return Player object
     * @see app.warzone.player
     */
    public Player getCountryHolder() {
        return d_holder;
    }

    /**
     * Prints the status of the country.
     *
     * @param isMapPhase Indicates if it's the map phase or not.
     */
    public void printCountryStatus(boolean isMapPhase) {
        System.out.printf("\n**%s**\n", d_countryName);
        if (!isMapPhase) {
            System.out.printf("Armies present :- %d\n", d_currentArmyCount);
            System.out.printf("Current holder :- %s\n", d_holder == null ? "No holder" : d_holder.d_playerName);
        }
        for (Country neighbour : d_neighbours) {
            System.out.printf("%s\t", neighbour.d_countryName);
        }
        System.out.print("\n");
    }

    /**
     * Adds or removes a neighboring country.
     *
     * @param p_country  The neighboring country to add or remove.
     * @param p_isAdding True to add, false to remove.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean addRemoveNeighbour(Country p_country, boolean p_isAdding) {
        if (p_isAdding) {
            for (Country neighbour : d_neighbours) {
                if (neighbour.d_countryId == p_country.d_countryId) {
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


    /**
     * Assigns a player as the holder of the country with a specified army count.
     *
     * @param p_player    The player to assign as the country holder.
     * @param p_armyCount The army count to assign to the country.
     */
    public void assignHolderWithArmies(Player p_player, int p_armyCount) {
        d_holder = p_player;
        d_currentArmyCount = p_armyCount;
    }

}
