package app.warzone.game;

import app.warzone.game.log.LogEntryBuffer;
import app.warzone.map.*;
import app.warzone.map.Map;
import app.warzone.map.parser.ConquestFileParser;
import app.warzone.map.parser.MapFileAdapter;
import app.warzone.map.parser.MapFileParser;
import app.warzone.player.Player;

import java.io.File;
import java.util.*;

/**
 * Utility class for managing various game-related actions and operations.
 */
public class GameUtils {

    /**
     * Variable holding the loaded map for gameplay
     */
    public static Map d_currTargetMap = null;

    /**
     * List of players currently playing the game
     */
    public static List<Player> d_playerList = new ArrayList<>();


    public static LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Message to be added in the log.
     *
     * @param p_logMessage Log Message to be set in the Object
     * @param p_logType    Type of Log Message to be Added
     */
    public static void updateLog(String p_logMessage, String p_logType) {

        d_logEntryBuffer.currentLog(p_logMessage, p_logType);
    }

    /**
     * Fetches the most recent Log in current GameState.
     *
     * @return recent Log Message
     */
    public static String getRecentLog() {
        return d_logEntryBuffer.getD_logMessage();
    }

    /**
     * Constructor for GameUtils, initializes the player list.
     */
    public GameUtils() {
        d_playerList = new ArrayList<>();
    }

    /**
     * Load a map from the provided arguments.
     *
     * @param p_arguments List of arguments, including the map file name.
     */
    public void loadMap(List<String> p_arguments) {
        // Create a File object for the specified map file
        File l_myObj = new File("src/main/resources/maps/" + p_arguments.get(0) + ".map");
        try {
            // Check if the map file exists
            if (!l_myObj.exists()) {
                System.out.println("No such map is present");
                return;
            }
            String l_mapType = MapUtils.checkMapType(p_arguments.get(0));
            System.out.println("Selected Map Type:- " + l_mapType);
            // Parse the map file using MapFileParser
            MapFileParser l_fileParser;
            if (l_mapType.equals("Domination"))
                l_fileParser = new MapFileParser(p_arguments.get(0));
            else l_fileParser = new MapFileAdapter(new ConquestFileParser(p_arguments.get(0)));
            Scanner l_fileScanner = new Scanner(l_myObj);
            d_currTargetMap = l_fileParser.parseMapFile(l_fileScanner);
            boolean l_result = MapValidator.validateMap(d_currTargetMap);
            if (!(MapValidator.d_alertMsg.isEmpty()))
                System.out.println("Map Validation Result:- " + MapValidator.d_alertMsg);
            if (!l_result) {
                System.out.println("Map could not be loaded in for gameplay :(");
                d_currTargetMap = null;
            } else {
                System.out.println(p_arguments.get(0) + " has been loaded successfully");
            }

            l_fileScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Display the current state of the map.
     */
    public void showMap() {
        if (d_currTargetMap == null) {
            System.out.println("Map isn't loaded");
            return;
        }
        d_currTargetMap.printMap(false);
    }

    /**
     * Assign countries to players in a random manner.
     */
    public void assignCountries() {
        List<Country> l_assignableList = new ArrayList<>(d_currTargetMap.getD_countries());
        int l_i = 0;
        while (!l_assignableList.isEmpty()) {
            Country l_assignablecountry = l_assignableList.get(generateRandomNumber(0, l_assignableList.size() - 1));
            d_playerList.get(l_i % d_playerList.size()).addCountryToHolderList(l_assignablecountry, 0);
            l_assignableList.remove(l_assignablecountry);
            l_i++;
        }
        showMap();
    }

    /**
     * Calculate reinforcement armies and assign it to each player object
     */

    public void assignReinforcementArmies() {
        ArrayList<Country> l_holdingCountries;
        for (Player l_player : d_playerList) {
            if (l_player.d_hasLost) continue;
            int l_count = 0;
            l_holdingCountries = new ArrayList<>(l_player.d_holdingCountries);
            for (Continent l_continent : d_currTargetMap.getD_continents()) {
                if (l_continent == null) continue;
                if (l_continent.getHolder() == l_player) {
                    l_count += l_continent.getArmyBonusCount();
                    for (Country l_country : l_continent.getMemberCountries()) {
                        l_holdingCountries.remove(l_country);
                    }
                }
            }

            l_count += (l_holdingCountries.size() / 3);
            l_player.d_currentArmyCount += Math.max(l_count, 3);
        }
    }

    /**
     * Generate a random number within the specified range.
     *
     * @param p_minValue Minimum value for the random number.
     * @param p_maxValue Maximum value for the random number.
     * @return The generated random number.
     */
    public static int generateRandomNumber(int p_minValue, int p_maxValue) {
        Random l_random = new Random();
        int l_range = p_maxValue - p_minValue + 1;
        return l_random.nextInt(l_range) + p_minValue;
    }

    /**
     * Add or remove players based on the provided arguments.
     *
     * @param p_arguments List of arguments, including player names and edit
     *                    actions.
     */
    public void addRemovePlayers(List<String> p_arguments) {
        int l_i;
        List<String> l_modifiable = new ArrayList<>(p_arguments);

        // Add players based on "-add" arguments
        while (true) {
            l_i = l_modifiable.indexOf("-add");
            if (l_i == -1)
                break;
            if ((l_i + 2) < l_modifiable.size() && !Objects.equals(l_modifiable.get(l_i + 2), "-add"))
                d_playerList.add(new Player(l_modifiable.get(l_i + 1), l_modifiable.get(l_i + 2)));
            else
                d_playerList.add(new Player(l_modifiable.get(l_i + 1)));
            l_modifiable.remove(l_i);
        }

        // Remove players based on "-remove" arguments
        while (true) {
            l_i = l_modifiable.indexOf("-remove");
            if (l_i == -1)
                break;
            d_playerList.remove(getPlayerByName(l_modifiable.get(l_i + 1)));
            l_modifiable.remove(l_i);
        }

        System.out.println("Player edit tasks have been performed successfully");
    }

    static void clearInstance() {
        d_playerList.clear();
        d_currTargetMap = null;
    }

    /**
     * Get a player by their name.
     *
     * @param p_name The name of the player to find.
     * @return The Player object if found, or null if not found.
     */
    public static Player getPlayerByName(String p_name) {
        for (Player l_player : d_playerList) {
            if (l_player.d_playerName.equals(p_name)) {
                return l_player;
            }
        }
        return null;
    }
}