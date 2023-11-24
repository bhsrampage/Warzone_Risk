package app.warzone.game.progress;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.map.Map;
import app.warzone.player.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that saves a game's progress into a file to be loaded and continued
 *
 * @author Burhanuddin
 */
public class SaveGame {
    private List<Player> d_playingPlayers;
    private Map d_currMap;

    /**
     * Constructs a SaveGame instance with the specified list of players and game map.
     *
     * @param p_players The list of players in the game.
     * @param p_map     The game map representing continents, countries, and borders.
     */
    public SaveGame(List<Player> p_players, Map p_map) {
        d_playingPlayers = p_players;
        d_currMap = p_map;
    }

    /**
     * Saves the current state of the game to a file with the given file name.
     *
     * @param p_fileName The name of the file to which the game state will be saved.
     */
    public void gameSave(String p_fileName) {
        try {
            // Create a file object for the game file.
            File l_myObj = new File("src/main/resources/games/" + p_fileName + ".game");

            if (!l_myObj.exists()) {
                if (l_myObj.createNewFile()) {
                    System.out.println("File not found created new");
                }
                else return;
            }
            // Create FileWriter and BufferedWriter for writing to the file.
            FileWriter fw = new FileWriter(l_myObj);
            BufferedWriter out = new BufferedWriter(fw);

            // List to store lines of the game file.
            List<String> lines = new ArrayList<>();

            //Add Players 
            lines.add("[players]");
            List<Player> l_currPlayingPlayers = new ArrayList<>(d_playingPlayers);
            l_currPlayingPlayers.removeIf(p -> p.d_hasLost);
            for (Player l_player : l_currPlayingPlayers){
                lines.add("\n");
                StringBuilder l_line = new StringBuilder(String.format("%s,%s,%d", l_player.d_playerName, l_player.d_isHuman ? "human" : l_player.d_strategy.getStrategyName(),l_player.d_currentArmyCount));
                for (String l_card : l_player.d_holdingCards){
                    l_line.append(",").append(l_card);
                }
                lines.add(l_line.toString());
            }
            
            lines.add("\n");
            // Add continents section with holders.
            lines.add("[continents]");
            for (Continent l_continent : d_currMap.getD_continents()) {
                lines.add("\n");
                lines.add(l_continent == null ? "$$"
                                : String.format("%s,%d,%s",
                                l_continent.getContinentName(),
                                l_continent.getArmyBonusCount(),
                                l_continent.getHolder() == null ? "$$" : l_continent.getHolder().d_playerName
                        )
                );
            }

            lines.add("\n");

            // Add countries section.
            lines.add("[countries]");
            for (Country l_country : d_currMap.getD_countries()) {
                lines.add("\n");
                lines.add(
                        String.format("%d,%s,%d,%s,%d",
                                l_country.getCountryId(),
                                l_country.getD_countryName(),
                                l_country.getContinentality().getContinentId(),
                                l_country.getCountryHolder().d_playerName,
                                l_country.getCurrentArmyCount()
                        )
                );
            }

            lines.add("\n");

            // Add borders section.
            lines.add("[borders]");
            for (Country l_country : d_currMap.getD_countries()) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
