package app.warzone.map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapUtilsTest {

	private static MapUtils d_mapUtils;

	@BeforeAll
	static void setUp() {
		// Initialize MapUtils or any necessary setup before each test
		d_mapUtils = new MapUtils();
	}

	@BeforeEach
	void editExistingMap() {
		String[] l_args = { "canada" };
		d_mapUtils.editMap(Arrays.asList(l_args));
		assertTrue(d_mapUtils.validateMap());
	}

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

	@Test
	void editContinentShouldAddContinentToMap() {
		// Create a sample list of l_arguments for adding a continent
		List<String> l_arguments = new ArrayList<>();
		l_arguments.add("-add");
		l_arguments.add("ContinentName");
		l_arguments.add("5");
		l_arguments.add("-add");
		l_arguments.add("ContinentName2");
		l_arguments.add("7");

		d_mapUtils.editContinent(l_arguments);

		assertNotNull(d_mapUtils.d_currTargetMap.getContinentByName("ContinentName"));
		assertNotNull(d_mapUtils.d_currTargetMap.getContinentByName("ContinentName2"));
	}

	@Test
	void editContinentShouldRemoveContinentFromMap() {
		// Create a sample list of l_arguments for adding a continent
		List<String> l_arguments = new ArrayList<>();
		l_arguments.add("-remove");
		l_arguments.add("Northwestern_Territories");
		l_arguments.add("-remove");
		l_arguments.add("Nunavut");

		d_mapUtils.editContinent(l_arguments);

		assertNull(d_mapUtils.d_currTargetMap.getContinentByName("Northwestern_Territories"));
		assertNull(d_mapUtils.d_currTargetMap.getContinentByName("Nunavut"));
	}

	@Test
	void editNeighbourShouldAddNeighboursToCountry() {
		List<String> l_arguments = new ArrayList<>();
		l_arguments.add("-add");
		l_arguments.add("New_Brunswick");
		l_arguments.add("N&L-Newfoundland");
		l_arguments.add("-add");
		l_arguments.add("N&L-Labrador");
		l_arguments.add("Ontario-South");

		d_mapUtils.editNeighbour(l_arguments);

		assertTrue(d_mapUtils.d_currTargetMap.getCountryByName("New_Brunswick").d_neighbours
				.contains(d_mapUtils.d_currTargetMap.getCountryByName("N&L-Newfoundland")));
		assertTrue(d_mapUtils.d_currTargetMap.getCountryByName("N&L-Newfoundland").d_neighbours
				.contains(d_mapUtils.d_currTargetMap.getCountryByName("New_Brunswick")));
		assertTrue(d_mapUtils.d_currTargetMap.getCountryByName("N&L-Labrador").d_neighbours
				.contains(d_mapUtils.d_currTargetMap.getCountryByName("Ontario-South")));
		assertTrue(d_mapUtils.d_currTargetMap.getCountryByName("Ontario-South").d_neighbours
				.contains(d_mapUtils.d_currTargetMap.getCountryByName("N&L-Labrador")));

	}

	@Test
	void editNeighbourShouldRemoveNeighboursFromCountry() {
		List<String> l_arguments = new ArrayList<>();
		l_arguments.add("-remove");
		l_arguments.add("New_Brunswick");
		l_arguments.add("Quebec-South");
		l_arguments.add("-remove");
		l_arguments.add("N&L-Labrador");
		l_arguments.add("N&L-Newfoundland");

		d_mapUtils.editNeighbour(l_arguments);

		assertFalse(d_mapUtils.d_currTargetMap.getCountryByName("New_Brunswick").d_neighbours
				.contains(d_mapUtils.d_currTargetMap.getCountryByName("Quebec-South")));
		assertFalse(d_mapUtils.d_currTargetMap.getCountryByName("Quebec-South").d_neighbours
				.contains(d_mapUtils.d_currTargetMap.getCountryByName("New_Brunswick")));
		assertFalse(d_mapUtils.d_currTargetMap.getCountryByName("N&L-Labrador").d_neighbours
				.contains(d_mapUtils.d_currTargetMap.getCountryByName("N&L-Newfoundland")));
		assertFalse(d_mapUtils.d_currTargetMap.getCountryByName("N&L-Newfoundland").d_neighbours
				.contains(d_mapUtils.d_currTargetMap.getCountryByName("N&L-Labrador")));

	}

	@Test
	void createNewMapFile() {
		String[] l_args = { "Test" };
		d_mapUtils.editMap(Arrays.asList(l_args));

		File l_file = new File("src/main/resources/maps/Test.map");
		assertTrue(l_file.exists());
		assertTrue(l_file.delete());
	}

}
