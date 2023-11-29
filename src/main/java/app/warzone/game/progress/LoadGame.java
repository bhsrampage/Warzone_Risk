package app.warzone.game.progress;

import app.warzone.game.GameUtils;
import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.map.Map;
import app.warzone.player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static app.warzone.map.parser.MapFileParser.lineExtractor;

/**
 * Class to load a games progress from specified file
 * @author Burhanuddin
 */
public class LoadGame {

    /**
     * Helper to parse the players section of the game file and adds players to
     * the game instance.
     *
     * @param p_fileText The file text.
     */
    void parsePlayers(StringBuilder p_fileText){
        String[] l_playerLines = lineExtractor(p_fileText, "[players]", "[continents]");
        for(String l_playerInfo : l_playerLines){
            String[] l_infoTokens = l_playerInfo.split(",");
            if (l_infoTokens.length < 3)
                continue;
            Player l_p = new Player(l_infoTokens[0],l_infoTokens[1]);
            l_p.d_currentArmyCount = Integer.parseInt(l_infoTokens[2]);
            for(int l_i = 3; l_i < l_infoTokens.length; l_i++){
                l_p.addCardToHolding(l_infoTokens[l_i]);
            }
            GameUtils.d_playerList.add(l_p);
        }
    }

    /**
     * Helper to parse the continents section of the game file and adds continents to
     * the parsed map and sets its holder.
     *
     * @param p_fileText The file text.
     */
    void parseContinents(StringBuilder p_fileText) {
        String[] l_continentLines = lineExtractor(p_fileText, "[continents]", "[countries]");
        int i = 1;
        for (String l_continentInfo : l_continentLines) {
            String[] l_infoTokens = l_continentInfo.split(",");
            if (l_infoTokens.length < 3) {
                if (l_infoTokens[0].equals("$$")) {
                    GameUtils.d_currTargetMap.addContinent(null);
                    i++;
                }
                continue;
            }
            Continent l_continent = new Continent(i, l_infoTokens[0], Integer.parseInt(l_infoTokens[1]));
            l_continent.setD_holder(GameUtils.getPlayerByName(l_infoTokens[2]));
            GameUtils.d_currTargetMap.addContinent(l_continent);
            i++;
        }
    }

    /**
     * Helper to parse the countries section of the game file and adds countries to
     * the parsed map and assigns its holder.
     *
     * @param p_fileText The file text.
     */
    void parseCountries(StringBuilder p_fileText) {
        String[] l_countryLines = lineExtractor(p_fileText, "[countries]", "[borders]");
        for (String l_countryInfo : l_countryLines) {
            String[] l_infoTokens = l_countryInfo.split(",");
            if (l_infoTokens.length < 4)
                continue;
            Country l_country = new Country(Integer.parseInt(l_infoTokens[0]), l_infoTokens[1],
                    GameUtils.d_currTargetMap.getContinentById(Integer.parseInt(l_infoTokens[2])));
            Player holder = GameUtils.getPlayerByName(l_infoTokens[3]);
            assert holder != null;
            holder.addCountryToHolderList(l_country,Integer.parseInt(l_infoTokens[4]));
            GameUtils.d_currTargetMap.addCountry(l_country);
        }
    }

    /**
     * Helper to parse the connections section of the game file and establishes
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
            Country l_targetCountry = GameUtils.d_currTargetMap.getCountryById(Integer.parseInt(l_infoTokens[0]));
            for (int l_i = 1; l_i < l_infoTokens.length; l_i++) {
                l_targetCountry.addRemoveNeighbour(GameUtils.d_currTargetMap.getCountryById(Integer.parseInt(l_infoTokens[l_i])),
                        true);
            }
        }
    }

    /**
     * Method called by phase to load a previous game
     * @param p_fileName
     * @return true if game loads successfully false otherwise
     */
    public boolean gameLoad(String p_fileName) {
        GameUtils.d_currTargetMap = new Map(p_fileName);
        File l_myObj = new File("src/main/resources/games/" + p_fileName + ".game");
        if (!l_myObj.exists()) {
            return false;
        }
        try {
            Scanner l_fileScanner = new Scanner(l_myObj);
            StringBuilder l_file = new StringBuilder();
            while (l_fileScanner.hasNextLine()) {
                l_file.append("\n");
                l_file.append(l_fileScanner.nextLine());
            }
            parsePlayers(l_file);
            parseContinents(l_file);
            parseCountries(l_file);
            parseConnections(l_file);
            return true;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }

    }
}
