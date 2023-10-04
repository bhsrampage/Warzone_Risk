package app.warzone.map;

import java.util.*;

/**
 * This class performs validation of map correctness, also after loading
 * and saving any new map the map will be validated automatically.
 *
 * @author Burhanuddin
 */


public class MapValidator {

    static String d_alertMsg = "";

    /**
     * d_isValid variable is for clearing the continents of the d_Map variable.
     */
    public static boolean d_isValidMap = false;

    /**
     * This method is used for validation of map.
     *
     * @param p_map refers to the map object for verification.
     */

    public static boolean validateMap(Map p_map) {
        if (p_map == null) {
            d_alertMsg = "The Map is not valid, it does not contain any content.";
            return false;
        } else {
            if (p_map.d_continents.isEmpty()) {
                d_alertMsg = "There should be atleast one Continent in the graph.";
                return false;
            } else {

                if (!validateCountryBelongsToOneContinent(p_map)) {
                    return false;
                }

                if (!validateContinents(p_map)) {
                    return false;
                }

                if (!isMapConnectedGraph(p_map)) {
                    d_alertMsg = "The map is not a connected graph i.e Continent is not a subgraph in the map. A map should be connected graph formed by continents.";
                    return false;
                } else {
                    System.out.println("The map is connected");
                    d_isValidMap = true;
                    return true;
                }

            }

        }

    }

    /**
     * This method is for validation the continents.
     * <p>
     * The map should be a graph of continents and if it is true then the continents must be subgraph of countries.
     * This method will validate whether the map is a graph of continents.
     *
     * @param p_map map refers to map object to validate the continents.
     */

    public static boolean validateContinents(Map p_map) {

        if (p_map.d_continents.isEmpty()) {
            d_alertMsg = "There are no continents present";
            return false;
        }
        for (Continent l_continent : p_map.d_continents) {
            if (l_continent == null) continue;
            if (l_continent.d_memberCountries.isEmpty()) {
                d_alertMsg = "There should be atleast one country in any continent.";
                return false;
            }

            for (Country l_country : l_continent.d_memberCountries) {
                if (!validateCountry(l_country)) return false;
            }

            if (!isContinentConnectedGraph(l_continent)) {
                d_alertMsg = "The Continent:- " + l_continent.getContinentName() + " is not connected by its neighbouring countries.A Continent must be connected graph formed by its neighbouring countries in the map.";
                return false;
            }
        }

        for (Continent l_continent : p_map.d_continents) {
            if (l_continent == null) continue;
            if (!checkSubGraphConnectivityForContinent(l_continent)) {
                d_alertMsg = "The Continent:-" + l_continent.d_continentName + " failed subgraph connectivity";
            }
        }

        return true;
    }


    /**
     * This method is used for validating that whether the continents are connected or not.
     *
     * @param p_continent is the continent to be validated.
     * @return will return true if the continent is connected in the map.
     */

    public static boolean isContinentConnectedGraph(Continent p_continent) {
        bfsTraversalCountry(p_continent.d_memberCountries.get(0));
        boolean l_returnValue = true;
        for (Country l_country : p_continent.d_memberCountries) {
            if (!l_country.d_isProcessed) {
                d_alertMsg = l_country.d_countryName + "country is not forming connected graph in the continent" + p_continent.d_continentName + ".";
                l_returnValue = false;
                break;
            }
        }

        for (Country l_country : p_continent.d_memberCountries) {
            l_country.d_isProcessed = false;
        }
        return l_returnValue;
    }

    /**
     * This method performs the traversal of the countries in BFS manner.
     */

    public static void bfsTraversalCountry(Country p_country) {
        if (p_country.d_isProcessed) {
            return;
        }

        p_country.d_isProcessed = true;

        for (Country l_country : p_country.d_neighbours) {
            if ((l_country.d_memberOfContinent == p_country.d_memberOfContinent) && !l_country.d_isProcessed)
                bfsTraversalCountry(l_country);
        }

    }

    /**
     * This method is used to validate that the country is connected or not.
     *
     * @param p_country refers to the country that is to be verified.
     */

    public static boolean validateCountry(Country p_country) {
        List<Country> l_adjCountryList = p_country.getNeighbouringCountries();

        if ((l_adjCountryList == null) || (l_adjCountryList.isEmpty())) {
            d_alertMsg = "Country: " + p_country.d_countryName + " must have atleast one adjacent country.";
            return false;
        } else {
            for (Country l_adjCountry : l_adjCountryList) {
                if (!l_adjCountry.getNeighbouringCountries().contains(p_country)) {
                    l_adjCountry.getNeighbouringCountries().add(p_country);
                }
            }
        }

        return true;
    }

    /**
     * This method is used for verifying that the subgraph of the continent is connected.
     *
     * @param p_continent refers to the continent
     * @return will return true or false accordingly
     */

    public static boolean checkSubGraphConnectivityForContinent(Continent p_continent) {

        bfsTraversalSubGraphConnectivityForContinent(p_continent.d_memberCountries.get(0));
        boolean l_returnValue = true;
        for (Country l_country : p_continent.d_memberCountries) {
            if (!l_country.d_isProcessed) {
                d_alertMsg = l_country.d_countryName + "country is not forming connected graph in the continent" + p_continent.d_continentName + ".";
                l_returnValue = false;
                break;
            }
        }

        for (Country l_country : p_continent.d_memberCountries) {
            l_country.d_isProcessed = false;
        }
        return l_returnValue;
    }

    /**
     * This is the BFS traversal method for the verification of subgraph connectivity in the continent
     *
     * @param p_country refers to the country
     */
    public static void bfsTraversalSubGraphConnectivityForContinent(Country p_country) {
        ArrayList<Country> l_adjCountryListBelongToSameContinent = new ArrayList<>();

        for (Country l_country : p_country.d_neighbours) {

            if (l_country.d_memberOfContinent.equals(p_country.d_memberOfContinent)) {
                l_adjCountryListBelongToSameContinent.add(l_country);
            }
        }

        if (p_country.d_isProcessed) {
            return;
        }
        p_country.d_isProcessed = true;

        for (Country l_country : l_adjCountryListBelongToSameContinent) {
            if ((l_country.d_memberOfContinent == p_country.d_memberOfContinent) && !l_country.d_isProcessed)
                bfsTraversalSubGraphConnectivityForContinent(l_country);
        }

    }


    /**
     * This method is used to validate that whether the continents in the map form a graph or not.
     *
     * @param p_map refers to the map object
     * @return returns true if the map is connected graph.
     */

    public static boolean isMapConnectedGraph(Map p_map) {

        if (p_map.d_continents.size() < 2) {
            return false;
        }

        bfsTraversalContinent(p_map.d_continents.get(0), p_map);

        boolean l_returnValue = true;
        for (Continent l_continent : p_map.d_continents) {
            if (l_continent == null) continue;
            if (!l_continent.d_isProcessed) {
                l_returnValue = false;
                break;
            }
        }

        for (Continent l_continent : p_map.d_continents) {
            if(l_continent == null) continue;
            l_continent.d_isProcessed = false;
        }
        return l_returnValue;
    }

    /**
     * This method performs the traversal of continents in BFS manner.
     *
     * @param p_continent refers to the continent to be traversed.
     * @param p_map       refers to the map object
     */

    public static void bfsTraversalContinent(Continent p_continent, Map p_map) {
        if (p_continent == null) bfsTraversalContinent(p_map.d_continents.get(1), p_map);
        assert p_continent != null;
        if (p_continent.d_isProcessed) {
            return;
        }

        p_continent.d_isProcessed = true;

        for (Continent l_continent : getAdjacentContinents(p_continent, p_map)) {
            if (!l_continent.d_isProcessed)
                bfsTraversalContinent(l_continent, p_map);
        }

    }

    /**
     * This method is used to return the adjacent continents as a list for any particular continent.
     *
     * @param p_continent refers to the continent whose adjacent continents are to be found.
     * @param p_map       refers to the map object.
     * @return will return the list of adjacent continents of the current continent.
     */

    public static List<Continent> getAdjacentContinents(Continent p_continent, Map p_map) {
        List<Continent> l_adjacentContinents = new ArrayList<>();
        HashSet<Country> l_adjCountryMainSet = new HashSet<>();

        for (Country l_country : p_continent.d_memberCountries) {
            l_adjCountryMainSet.addAll(l_country.d_neighbours);
        }

        for (Continent l_remainingContinent : p_map.d_continents) {
            if (l_remainingContinent == null) continue;
            if (!p_continent.equals(l_remainingContinent)) {
                if (!Collections.disjoint(l_adjCountryMainSet, l_remainingContinent.d_memberCountries)) {
                    l_adjacentContinents.add(l_remainingContinent);

                }
            }
        }
        return l_adjacentContinents;
    }

    /**
     * This method is used to check whether the country belongs to only one continent or not.
     *
     * @param p_map refers to map object.
     * @return true if country belongs to one continent false otherwise.
     */
    public static boolean validateCountryBelongsToOneContinent(Map p_map) {

        ArrayList<Country> l_countryList = new ArrayList<>();

        for (Continent l_continent : p_map.d_continents) {
            if (l_continent == null) continue;
            for (Country l_country : l_continent.d_memberCountries) {
                if (!l_countryList.contains(l_country)) {
                    l_countryList.add(l_country);
                } else {
                    d_alertMsg = "Country:" + l_country.d_countryName + " must belong to only one continent.";
                    return false;
                }
            }
        }
        return true;
    }


}