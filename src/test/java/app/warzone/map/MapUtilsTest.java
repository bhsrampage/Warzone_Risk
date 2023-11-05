package app.warzone.map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the MapUtils class, which provides utility methods for editing
 * maps. This class contains test cases for various map editing operations using
 * MapUtils.
 */
public class MapUtilsTest {

	private static MapUtils d_mapUtils;

	/**
	 * Initialization method to set up the MapUtils instance before running tests.
	 */
	@BeforeAll
	static void setUp() {
		// Initialize MapUtils or perform any necessary setup before each test
		d_mapUtils = new MapUtils();
	}

	/**
	 * Method to edit an existing map before running each test.
	 */
	@BeforeEach
	void editExistingMap() {
		String[] l_args = { "canada" };
		d_mapUtils.editMap(Arrays.asList(l_args));
		assertTrue(d_mapUtils.validateMap());
	}

	/**
	 * Test case for editing a country to add it to the map. This test checks if the
	 * editCountry method correctly adds countries to the map.
	 */
	@Test
	void editCountryShouldAddCountryToMap() {
		List<String> l_arguments = new ArrayList<>();
		l_arguments.add("-add");
		l_arguments.add("Testcountry1");
		l_arguments.add("Northwestern_Territories");
		l_arguments.add("-add");
		l_arguments.add("Testcountry2");
		l_arguments.add("Nunavut");

		d_mapUtils.editCountry(l_arguments);

		assertNotNull(d_mapUtils.d_currTargetMap.getCountryByName("Testcountry1"));
		assertNotNull(d_mapUtils.d_currTargetMap.getCountryByName("Testcountry2"));
	}

	/**
	 * Test case for editing a country to remove it from the map. This test checks
	 * if the editCountry method correctly removes countries from the map.
	 */
	@Test
	void editCountryShouldRemoveCountryFromMap() {
		List<String> l_arguments = new ArrayList<>();
		l_arguments.add("-remove");
		l_arguments.add("New_Brunswick");
		l_arguments.add("-remove");
		l_arguments.add("Quebec-South");

		d_mapUtils.editCountry(l_arguments);

		assertNull(d_mapUtils.d_currTargetMap.getCountryByName("Testcountry1"));
		assertNull(d_mapUtils.d_currTargetMap.getCountryByName("Testcountry2"));
	}

	// Additional test methods for editContinent, editNeighbour, createNewMapFile,
	// etc.

}
