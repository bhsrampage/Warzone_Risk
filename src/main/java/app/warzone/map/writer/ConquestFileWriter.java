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
 * The ConquestFileWriter class is responsible for writing a Warzone map to a Conquest format file.
 * It includes methods to save the map, which includes information about continents and territories.
 */
public class ConquestFileWriter {
    /** The target map to be written. */
    Map d_targetMap;

    /**
     * Constructor for the ConquestFileWriter class.
     *
     * @param p_map The map to be written.
     */
    public ConquestFileWriter(Map p_map){
        d_targetMap = p_map;
    }

    /**
     * Saves the map to a Conquest format file.
     * It includes information about continents and territories.
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
            lines.add("[Continents]");
            for (Continent l_continent : d_targetMap.getD_continents()) {
                lines.add("\n");
                lines.add(l_continent == null ? "$$"
                        : String.format("%s=%d", l_continent.getContinentName(), l_continent.getArmyBonusCount()));
            }

            lines.add("\n");

            // Add territories section.
            lines.add("[Territories]");
            for (Country l_country : d_targetMap.getD_countries()) {
                lines.add("\n");
                StringBuilder l_line = new StringBuilder(String.format("%s,%d,%d,%s", l_country.getD_countryName(), 0, 0, l_country.getContinentality().getContinentName()));
                for (Country l_l_country : l_country.getNeighbouringCountries())
                    l_line.append(",").append(l_l_country.getD_countryName());
                lines.add(l_line.toString());
            }

            lines.add("\n");

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
