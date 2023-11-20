package app.warzone.map;

import app.warzone.map.parser.ConquestFileParser;
import app.warzone.map.parser.MapFileAdapter;
import app.warzone.map.parser.MapFileParser;
import app.warzone.map.writer.ConquestFileWriter;
import app.warzone.map.writer.MapFileWriter;
import app.warzone.map.writer.MapWriterAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The MapUtils class provides utility functions to edit and manage the game
 * map.
 *
 * @author Burhanuddin
 */
public class MapUtils {

    /**
     * The target map being operated on.
     */
    Map d_currTargetMap;

    /**
     * Flag to indicate whether map is loaded
     */
    boolean d_isMapLoaded;

    /**
     * Helper method to print default map not loaded message
     *
     * @return true if map is loaded and prints message and returns false otherwise
     */
    boolean checkLoadStatus() {
        if (d_isMapLoaded)
            return true;
        System.out.println("Load the map using editmap <name> command to start editing");
        return false;
    }

    /**
     * Edits countries in the map based on the provided arguments.
     *
     * @param p_arguments The arguments specifying the operations to be performed on
     *                    countries.
     */
    public void editCountry(List<String> p_arguments) {
        if (!checkLoadStatus())
            return;
        int l_i;
        List<String> l_modifiable = new ArrayList<>(p_arguments);
        while (true) {
            l_i = l_modifiable.indexOf("-add");
            if (l_i == -1)
                break;
            if (l_modifiable.size() < l_i + 3) {
                System.out.println("Incorrect arguments for add try again..");
                break;
            }
            d_currTargetMap.addCountry(
                    new Country(
                            d_currTargetMap.d_countries.isEmpty() ? 1 : (d_currTargetMap.d_countries.get(d_currTargetMap.d_countries.size() - 1).d_countryId + 1),
                            l_modifiable.get(l_i + 1),
                            d_currTargetMap.getContinentByName(l_modifiable.get(l_i + 2))
                    )
            );
            l_modifiable.remove(l_i);
        }

        while (true) {
            l_i = l_modifiable.indexOf("-remove");
            if (l_i == -1)
                break;
            if (l_modifiable.size() < l_i + 2) {
                System.out.println("Incorrect arguments try again..");
                break;
            }
            d_currTargetMap.removeCountryByName(l_modifiable.get(l_i + 1));
            l_modifiable.remove(l_i);
        }

        System.out.println("editcountry command was executed\n");
    }

    /**
     * Edits continents in the map based on the provided arguments.
     *
     * @param p_arguments The arguments specifying the operations to be performed on
     *                    continents.
     */
    public void editContinent(List<String> p_arguments) {
        if (!checkLoadStatus())
            return;
        int l_i;
        List<String> l_modifiable = new ArrayList<>(p_arguments);
        while (true) {
            l_i = l_modifiable.indexOf("-add");
            if (l_i == -1)
                break;
            if (l_modifiable.size() < l_i + 3) {
                System.out.println("Incorrect arguments for add try again..");
                break;
            }
            d_currTargetMap.addContinent(new Continent(d_currTargetMap.d_continents.size() + 1, l_modifiable.get(l_i + 1),
                    Integer.parseInt(l_modifiable.get(l_i + 2))));
            l_modifiable.remove(l_i);
        }

        while (true) {
            l_i = l_modifiable.indexOf("-remove");
            if (l_i == -1)
                break;
            if (l_modifiable.size() < l_i + 2) {
                System.out.println("Incorrect arguments for remove try again..");
                break;
            }
            d_currTargetMap.removeContinentByName(l_modifiable.get(l_i + 1));
            l_modifiable.remove(l_i);
        }
        System.out.println("editcontinent command was executed\n");
    }

    /**
     * Edits neighboring countries of a country in the map based on the provided
     * arguments.
     *
     * @param p_arguments The arguments specifying the operations to be performed on
     *                    neighboring countries.
     */
    public void editNeighbour(List<String> p_arguments) {
        if (!checkLoadStatus())
            return;
        int l_i;
        List<String> l_modifiable = new ArrayList<>(p_arguments);
        while (true) {
            l_i = l_modifiable.indexOf("-add");
            if (l_i == -1)
                break;
            if (l_modifiable.size() < l_i + 3) {
                System.out.println("Incorrect arguments for add try again..");
                break;
            }
            d_currTargetMap.addRemoveCountryNeighbourByName(l_modifiable.get(l_i + 1), l_modifiable.get(l_i + 2), true);
            l_modifiable.remove(l_i);
        }

        while (true) {
            l_i = l_modifiable.indexOf("-remove");
            if (l_i == -1)
                break;
            if (l_modifiable.size() < l_i + 3) {
                System.out.println("Incorrect arguments for remove try again..");
                break;
            }
            d_currTargetMap.addRemoveCountryNeighbourByName(l_modifiable.get(l_i + 1), l_modifiable.get(l_i + 2),
                    false);
            l_modifiable.remove(l_i);
        }
        System.out.println("editneighbour command was executed\n");
    }

    /**
     * Displays the map to the user in a readable format.
     */
    public void showMap() {
        if (!checkLoadStatus())
            return;
        d_currTargetMap.printMap(true);
    }

    /**
     * Saves the current map to a file.
     */
    public void saveMap() {
        if (!checkLoadStatus())
            return;
        if (!validateMap()) {
                System.out.println("Cannot save map as it is not valid");
                return;
        }
        Scanner l_scan = new Scanner(System.in);
        int l_choice;
        System.out.println("Choose the map type \n1. Domination\n2. Conquest");
        l_choice = l_scan.nextInt();
        String l_mapType = l_choice == 1 ? "Domination" : "Conquest";
        System.out.println("Selected Map Type for save:- " + l_mapType);
        MapFileWriter l_writer;
        if(l_mapType.equals("Domination"))
            l_writer = new MapFileWriter(d_currTargetMap);
        else l_writer = new MapWriterAdapter(new ConquestFileWriter(d_currTargetMap));
        l_writer.saveMap();

    }

    public static String checkMapType(String p_fileName) {
        String l_path = "src/main/resources/maps/";
        String l_fileName = p_fileName + ".map";
        File l_map = new File(l_path + l_fileName);

        if (!l_map.exists()) {
            return "Domination";
        } else {
            Scanner l_mapReader;
            try {
                l_mapReader = new Scanner(l_map);
                while (l_mapReader.hasNextLine()) {
                    String l_line = l_mapReader.nextLine();
                    if (l_line.equals("[Continents]")) {
                        l_line = l_mapReader.nextLine();
                        if (l_line.contains("=")) {
                            return "Conquest";
                        } else {

                            return "Domination";
                        }
                    }
                }
            } catch (FileNotFoundException e) {
            }
        }
        return "Domination";
    }

    /**
     * Sets focus of the Maputils object to either an existing .map file or a new
     * one.
     *
     * @param p_arguments The arguments specifying the operations to be performed on
     *                    the map.
     */
    public void editMap(List<String> p_arguments) {
        try {
            String l_mapType = checkMapType(p_arguments.get(0));
            System.out.println("Selected Map Type:- " + l_mapType);
            File l_myObj = new File("src/main/resources/maps/" + p_arguments.get(0) + ".map");
            if (!l_myObj.exists()) {
                d_currTargetMap = new Map(p_arguments.get(0),l_mapType);
                if (l_myObj.createNewFile()) {
                    System.out.println("File not found created new");
                }
            } else {
                MapFileParser l_fileParser;
                if(l_mapType.equals("Domination")) l_fileParser = new MapFileParser(p_arguments.get(0));
                else l_fileParser = new MapFileAdapter(new ConquestFileParser(p_arguments.get(0)));
                Scanner l_fileScanner = new Scanner(l_myObj);
                d_currTargetMap = l_fileParser.parseMapFile(l_fileScanner);
                System.out.println(p_arguments.get(0) + " has been loaded successfully");
                l_fileScanner.close();
            }
            d_isMapLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean validateMap() {
        boolean l_result = MapValidator.validateMap(d_currTargetMap);
        if (!(MapValidator.d_alertMsg.isEmpty()))
            System.out.println("Map Validation Result:- " + MapValidator.d_alertMsg);
        return l_result;
    }

}