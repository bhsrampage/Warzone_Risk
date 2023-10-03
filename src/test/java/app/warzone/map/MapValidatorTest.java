package app.warzone.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapValidatorTest {

    private Map d_map;
    private Continent d_continent1;
    private Continent d_continent2;
    private Country d_country1;
    private Country d_country2;
    private Country d_country3;

    @BeforeEach
    void setUp() {
        // Initialize a sample d_map and continents for testing
        d_map = new Map("Sample");
        d_continent1 = new Continent(1, "Continent 1", 5);
        d_continent2 = new Continent(2, "Continent 2", 3);
        d_map.addContinent(d_continent1);
        d_map.addContinent(d_continent2);

        // Initialize some countries
        d_country1 = new Country(1, "Country 1", d_continent1);
        d_country2 = new Country(2, "Country 2", d_continent1);
        d_country3 = new Country(3, "Country 3", d_continent2);
        d_map.addCountry(d_country1);
        d_map.addCountry(d_country2);
        d_map.addCountry(d_country3);

        // Set neighboring countries
        d_country1.addRemoveNeighbour(d_country2, true);
        d_country2.addRemoveNeighbour(d_country1, true);
        d_country1.addRemoveNeighbour(d_country3, true);
        d_country3.addRemoveNeighbour(d_country1, true);
    }

    @Test
    void validateMap_ValidMap() {
        assertTrue(MapValidator.validateMap(d_map));
    }

    @Test
    void validateMap_NullMap() {
        assertFalse(MapValidator.validateMap(null));
    }

    @Test
    void validateMap_EmptyContinents() {
        d_map.d_continents = new ArrayList<>();
        assertFalse(MapValidator.validateMap(d_map));
    }

    @Test
    void validateMap_InvalidMap_NotConnectedGraph() {
        // Make the d_map not a connected graph
        d_country3.addRemoveNeighbour(d_country1,false);
        d_country1.addRemoveNeighbour(d_country3,false);

        assertFalse(MapValidator.validateMap(d_map));
    }

    @Test
    void validateContinents_ValidContinents() {
        assertTrue(MapValidator.validateContinents(d_map));
    }

    @Test
    void validateContinents_EmptyContinent() {
        // Remove countries from d_continent1 to make it empty
        d_continent1.d_memberCountries = new ArrayList<>();
        assertFalse(MapValidator.validateContinents(d_map));
    }

    @Test
    void isContinentConnectedGraph_ValidContinent() {
        assertTrue(MapValidator.isContinentConnectedGraph(d_continent1));
    }

    @Test
    void isContinentConnectedGraph_InvalidContinent() {
        // Remove the connection between d_country1 and d_country2
        d_country1.addRemoveNeighbour(d_country2, false);

        assertFalse(MapValidator.isContinentConnectedGraph(d_continent1));
    }

    @Test
    void validateCountry_ValidCountry() {
        assertTrue(MapValidator.validateCountry(d_country1));
    }

    @Test
    void validateCountry_EmptyAdjacentCountries() {
        // Remove adjacent countries to make it invalid
        d_country1.d_neighbours = new ArrayList<>();
        assertFalse(MapValidator.validateCountry(d_country1));
    }
}
