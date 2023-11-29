package app.warzone.map.parser;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.map.Map;

import java.util.Scanner;

/**
 * The MapFileParser class parses a Warzone map file and constructs a map based
 * on its contents.
 *
 * @author Burhanuddin
 */
public class MapFileParser {
    /**
     * Variable to hold the parsed map from the map file.
     */
    Map d_parsedMap;

    /**
     * Constructs a MapFileParser for parsing a map file with the given name.
     *
     * @param name The name of the map.
     */
    public MapFileParser(String name) {
        d_parsedMap = new Map(name,"Domination");
    }

    /**
     * Helper for extracting lines from the given file text that fall between the
     * specified start and end markers for the sections.
     *
     * @param p_file  The file text to extract lines from.
     * @param p_start The start marker.
     * @param p_end   The end marker.
     * @return An array of extracted lines.
     */
    public static String[] lineExtractor(StringBuilder p_file, String p_start, String p_end) {
        return getStrings(p_file, p_start, p_end);
    }
    static String[] getStrings(StringBuilder p_file, String p_start, String p_end) {
        int l_sectionStart = p_file.indexOf(p_start);
        if (p_end != null) {
            int l_sectionEnd = p_file.indexOf(p_end);
            return p_file.substring(l_sectionStart, l_sectionEnd).replace(p_start, "\n").replace(p_end, "\n")
                    .split("\n");
        }

        return p_file.substring(l_sectionStart).replace(p_start, "\n").split("\n");
    }

    /**
     * Helper to parse the continents section of the map file and adds continents to
     * the parsed map.
     *
     * @param p_fileText The file text.
     */
    void parseContinents(StringBuilder p_fileText) {
        String[] l_continentLines = lineExtractor(p_fileText, "[continents]", "[countries]");
        int i = 1;
        for (String l_continentInfo : l_continentLines) {
            String[] l_infoTokens = l_continentInfo.split(" ");
            if (l_infoTokens.length < 2) {
                if (l_infoTokens[0].equals("$$")) {
                    d_parsedMap.addContinent(null);
                    i++;
                }
                continue;
            }
            d_parsedMap.addContinent(new Continent(i, l_infoTokens[0], Integer.parseInt(l_infoTokens[1])));
            i++;
        }
    }

    /**
     * Helper to parse the countries section of the map file and adds countries to
     * the parsed map.
     *
     * @param p_fileText The file text.
     */
    void parseCountries(StringBuilder p_fileText) {
        String[] l_countryLines = lineExtractor(p_fileText, "[countries]", "[borders]");
        for (String l_countryInfo : l_countryLines) {
            String[] l_infoTokens = l_countryInfo.split(" ");
            if (l_infoTokens.length < 3)
                continue;
            d_parsedMap.addCountry(new Country(Integer.parseInt(l_infoTokens[0]), l_infoTokens[1],
                    d_parsedMap.getContinentById(Integer.parseInt(l_infoTokens[2]))));
        }
    }

    /**
     * Helper to parse the connections section of the map file and establishes
     * connections between countries in the parsed map.
     *
     * @param p_fileText The file text.
     */
    void parseConnections(StringBuilder p_fileText) {
        String[] l_connectionLines = lineExtractor(p_fileText, "[borders]", null);
        for (String l_connectionInfo : l_connectionLines) {
            String[] l_infoTokens = l_connectionInfo.split(" ");
            if (l_infoTokens.length < 2)
                continue;
            Country l_targetCountry = d_parsedMap.getCountryById(Integer.parseInt(l_infoTokens[0]));
            for (int l_i = 1; l_i < l_infoTokens.length; l_i++) {
                l_targetCountry.addRemoveNeighbour(d_parsedMap.getCountryById(Integer.parseInt(l_infoTokens[l_i])),
                        true);
            }
        }
    }

    /**
     * Parses the entire map file and constructs a map based on its contents using
     * the helper functions above.
     *
     * @param p_fileScan The scanner for reading the map file.
     * @return The constructed map.
     */
    public Map parseMapFile(Scanner p_fileScan) {
        StringBuilder l_file = new StringBuilder();
        while (p_fileScan.hasNextLine()) {
            l_file.append("\n");
            l_file.append(p_fileScan.nextLine());
        }
        parseContinents(l_file);
        parseCountries(l_file);
        parseConnections(l_file);

        return d_parsedMap;
    }
}