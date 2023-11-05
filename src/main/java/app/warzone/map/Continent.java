package app.warzone.map;

import app.warzone.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The Continent class represents a continent in the game map.
 * 
 * @author Burhanuddin
 */
public class Continent {
	/**
	 * The unique identifier for the continent.
	 */
	int d_continentId;

	/**
	 * The name of the continent.
	 */
	String d_continentName;

	/**
	 * The army bonus count for holding the entire continent.
	 */
	int d_armyBonusCount;

	/**
	 * List of countries that are part of this continent.
	 */
	List<Country> d_memberCountries;

	/**
	 * The player who currently holds the entire continent.
	 */
	Player d_holder;

	/**
	 * Flag indicating if continent has been visited/processed
	 */
	boolean d_isProcessed;

	/**
	 * Gets the unique identifier of the continent.
	 *
	 * @return The unique identifier of the continent.
	 */
	public int getContinentId() {
		return d_continentId;
	}

	/**
	 * Gets the name of the continent.
	 *
	 * @return The name of the continent.
	 */
	public String getContinentName() {
		return d_continentName;
	}

	/**
	 * Gets the army bonus count for holding the entire continent.
	 *
	 * @return The army bonus count for holding the entire continent.
	 */
	public int getArmyBonusCount() {
		return d_armyBonusCount;
	}

	/**
	 * Gets the player who currently holds the entire continent.
	 *
	 * @return The player who currently holds the entire continent, or null if no
	 *         holder.
	 */
	public Player getHolder() {
		return d_holder;
	}

	/**
	 * Gets the list of countries that are part of this continent.
	 *
	 * @return List of countries that are part of this continent.
	 * @see app.warzone.map.Country
	 */
	public List<Country> getMemberCountries() {
		return d_memberCountries;
	}

	/**
	 * Constructs a Continent object with the given parameters.
	 *
	 * @param p_Id         The unique identifier for the continent.
	 * @param p_name       The name of the continent.
	 * @param p_bonusCount The army bonus count for holding the entire continent.
	 */
	public Continent(int p_Id, String p_name, int p_bonusCount) {
		d_continentId = p_Id;
		d_continentName = p_name;
		d_armyBonusCount = p_bonusCount;
		d_memberCountries = new ArrayList<Country>();
		d_holder = null;
	}

	/**
	 * Prints the continent's status, including its member countries and holder.
	 *
	 * @param p_isMapPhase Indicates if it's the map phase or not.
	 */
	public void printContinent(boolean p_isMapPhase) {
		System.out.printf("\n\n***%s***\n", d_continentName);
		if (d_holder != null && !p_isMapPhase)
			System.out.printf("The holder of %s is %s\n", d_continentName, d_holder.d_playerName);
		for (Country members : d_memberCountries) {
			members.printCountryStatus(p_isMapPhase);
		}
		System.out.print("\n");
	}

	/**
	 * Adds or removes a country as a member of this continent.
	 *
	 * @param p_country  The country to add or remove.
	 * @param p_isAdding True to add, false to remove.
	 * @return True if the operation was successful, false otherwise.
	 */
	public boolean addRemoveMembers(Country p_country, boolean p_isAdding) {
		if (p_isAdding) {
			for (Country member : d_memberCountries) {
				if (member.d_countryId == p_country.d_countryId) {
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

	/**
	 * Changes the holder of the entire continent.
	 *
	 * @param p_newHolder The player to assign as the continent holder.
	 */
	public void changeHolder(Player p_newHolder) {
		d_holder = p_newHolder;
	}

}
