package app.warzone.game;

import app.warzone.map.Continent;
import app.warzone.map.Country;
import app.warzone.map.Map;
import app.warzone.map.MapFileParser;
import app.warzone.player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class GameUtils {

    Map d_currTargetMap;
    List<Player> d_playerList;

    public GameUtils() {
        d_playerList = new ArrayList<>();


    }

    public void loadMap(List<String> p_arguments) {
        File l_myObj = new File("src/main/resources/maps/" + p_arguments.get(0) + ".map");
        try {

            if (!l_myObj.exists()) {
                System.out.println("No such map is present");
                return;
            }
            MapFileParser l_fileParser = new MapFileParser(p_arguments.get(0));
            Scanner l_fileScanner = new Scanner(l_myObj);
            d_currTargetMap = l_fileParser.parseMapFile(l_fileScanner);
            System.out.println(p_arguments.get(0) + " has been loaded successfully");
            l_fileScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showMap() {
        d_currTargetMap.printMap(false);
    }

    public void assignCountries() {
        List<Country> l_assignableList = new ArrayList<>(d_currTargetMap.getD_countries());
        int l_i = 0;
        while (l_assignableList.size() != 0) {
            Country l_assignablecountry = l_assignableList.get(
                    generateRandomNumber(0, l_assignableList.size() - 1)
            );
            d_playerList.get(l_i % d_playerList.size()).addCountryToHolderList(l_assignablecountry
                    , 0
            );
            l_assignableList.remove(l_assignablecountry);

            l_i++;
        }
        showMap();
    }

    private int generateRandomNumber(int minValue, int maxValue) {
        // Initialize a random number generator
        Random random = new Random();

        // Calculate the range (inclusive) between minValue and maxValue
        int range = maxValue - minValue + 1;

        // Generate a random number within the specified range and add it to minValue
        int randomNumber = random.nextInt(range) + minValue;

        return randomNumber;
    }

    public void addRemovePlayers(List<String> p_arguments) {

        int l_i;
        List<String> l_modifiable = new ArrayList<>(p_arguments);
        while (true) {
            l_i = l_modifiable.indexOf("-add");
            if (l_i == -1) break;
            d_playerList.add(new Player(l_modifiable.get(l_i + 1)));
            l_modifiable.remove(l_i);
        }

        while (true) {
            l_i = l_modifiable.indexOf("-remove");
            if (l_i == -1) break;
            d_playerList.remove(getPlayerByName(l_modifiable.get(l_i + 1)));
            l_modifiable.remove(l_i);
        }

        System.out.println("Player edit tasks have been performed succesfully");
    }

    private Player getPlayerByName(String p_name) {

        for (Player l_player : d_playerList) {
            if (l_player.d_playerName == p_name) {
                return l_player;
            }

        }
        return null;
    }

}
