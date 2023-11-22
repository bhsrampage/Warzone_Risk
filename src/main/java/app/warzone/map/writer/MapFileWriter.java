package app.warzone.map.writer;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.map.Map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The MapFileWriter class is responsible for writing a Warzone map to a custom map file format.
 * It includes methods to save the map, which consists of information about continents, countries, and borders.
 */
public class MapFileWriter {
    /** The target map to be written. */
    private final Map d_targetMap;

    /**
     * Constructor for the MapFileWriter class.
     *
     * @param p_map The map to be written.
     */
    public MapFileWriter(Map p_map) {
        d_targetMap = p_map;
    }

    /**
     * Saves the map to a custom map file format.
     * It includes information about continents, countries, and borders.
     */
    public void saveMap() {
        try {
            // Create a file object for the map file.
            File myObj = new File("src/main/resources/maps/" + d_targetMap.getD_mapName() + ".map");

            // Create FileWriter and BufferedWriter for writing to the file.
            FileWriter fw = new FileWriter(myObj);
            BufferedWriter out = new BufferedWriter(fw);

            // List to store lines of the map file.
            List<String> lines = new ArrayList<>();

            // Add continents section.
            lines.add("[continents]");
            for (Continent l_continent : d_targetMap.getD_continents()) {
                lines.add("\n");
                lines.add(l_continent == null ? "$$"
                        : String.format("%s %d", l_continent.getContinentName(), l_continent.getArmyBonusCount()));
            }

            lines.add("\n");

            // Add countries section.
            lines.add("[countries]");
            for (Country l_country : d_targetMap.getD_countries()) {
                lines.add("\n");
                lines.add(String.format("%d %s %d", l_country.getCountryId(), l_country.getD_countryName(),
                        l_country.getContinentality().getContinentId()));
            }

            lines.add("\n");

            // Add borders section.
            lines.add("[borders]");
            for (Country l_country : d_targetMap.getD_countries()) {
                StringBuilder borderInfo = new StringBuilder(String.valueOf(l_country.getCountryId()));
                lines.add("\n");
                for (Country l_neighbour : l_country.getNeighbouringCountries()) {
                    borderInfo.append(" ").append(l_neighbour.getCountryId());
                }
                lines.add(borderInfo.toString());
            }

            // Write each line to the file.
            for (String s : lines)
                out.write(s);

            // Flush and close the BufferedWriter.
            out.flush();
            out.close();
            System.out.println("Map was saved successfully :)\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
