package app.warzone.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MapFileParserTest {

	private MapFileParser d_mapFileParser;

	@BeforeEach
	void setUp() {
		d_mapFileParser = new MapFileParser("Test Map");
	}

	@Test
	void parseMapFile() {
		String l_mapFileContent = "[continents]\n" + "North_America 5\n" + "Europe 7\n" + "[countries]\n" + "1 USA 1\n"
				+ "2 Canada 1\n" + "[borders]\n" + "1 2";

		// Simulating scanning from a file
		System.setIn(new ByteArrayInputStream(l_mapFileContent.getBytes()));
		Scanner l_scanner = new Scanner(System.in);

		Map l_parsedMap = d_mapFileParser.parseMapFile(l_scanner);

		assertEquals("Test Map", l_parsedMap.d_mapName);
		assertEquals(2, l_parsedMap.d_continents.size());
		assertEquals(2, l_parsedMap.d_countries.size());
	}
}
