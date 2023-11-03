package app.warzone.game;

import app.warzone.Main.Phase;
import app.warzone.map.MapFileParser;
import app.warzone.map.MapUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for maintaining the rules for the Warzone game along with generating the flow
 * of gameplay.
 */
public class GameEngine {
    Scanner SCAN;
    Phase d_currPhase = null;

    public GameEngine() {
        SCAN = new Scanner(System.in);
    }

    public Phase getD_currPhase() {
        return d_currPhase;
    }

    void listenMapCommands() {
        System.out.println("**Map Editor**\n");
        MapUtils l_targetMapUtil = new MapUtils();
        boolean l_isEditing = true;

        while (l_isEditing) {
            String l_userInput = SCAN.nextLine();
            String[] l_cmdTokens = l_userInput.split(" ");
            List<String> arguments = Arrays.asList(Arrays.copyOfRange(l_cmdTokens, 1, l_cmdTokens.length));

            switch (l_cmdTokens[0]) {
                case "editcontinent":
                    l_targetMapUtil.editContinent(arguments);
                    break;
                case "editcountry":
                    l_targetMapUtil.editCountry(arguments);
                    break;
                case "editneighbor":
                    l_targetMapUtil.editNeighbour(arguments);
                    break;
                case "savemap":
                    l_targetMapUtil.saveMap();
                    l_isEditing = false;
                    break;
                case "editmap":
                    l_targetMapUtil.editMap(arguments);
                    break;
                case "validatemap":
                    l_targetMapUtil.validateMap();
                    break;
                case "showmap":
                    l_targetMapUtil.showMap();
                    break;
                case "exit":
                    l_isEditing = false;
                    break;
                default:
                    System.out.println("Invalid Command try again..");
            }
        }


    }

    void listenGameCommands() {
        System.out.println("**Gameplay**\n");
        GameUtils l_gameUtil = new GameUtils();
        boolean l_isEditing = true;

        while (l_isEditing) {
            String l_userInput = SCAN.nextLine();
            String[] l_cmdTokens = l_userInput.split(" ");
            List<String> arguments = Arrays.asList(Arrays.copyOfRange(l_cmdTokens, 1, l_cmdTokens.length));

            switch (l_cmdTokens[0]) {
                case "loadmap":
                    l_gameUtil.loadMap(arguments);
                    break;
                case "gameplayer":
                    l_gameUtil.addRemovePlayers(arguments);
                    break;
                case "assigncountries":
                    l_gameUtil.assignCountries();
                    break;
                case "showmap":
                    l_gameUtil.showMap();
                    break;
                case "airlift":
                    l_gameUtil.airlift();
                    break;
                default:
                    System.out.println("Invalid Command try again..");
                    break;
            }
        }

    }



    public void initialize() {
        System.out.println("Welcome to Risk (Warzone) by U6 build1");
        String choice;
        boolean playing = true;
        while (playing) {
            System.out.println("1. Map Editor\n2. Play Game\nEnter your choice:- ");
            choice = SCAN.nextLine();
            switch (choice) {
                case "1":
                    d_currPhase = Phase.MAP_ACTIONS;
                    listenMapCommands();
                    break;
                case "2":
                    d_currPhase = Phase.GAMEPLAY;
                    listenGameCommands();
                    break;
                default:
                    playing = false;
                    break;
            }
            System.out.println("Quitting...");
        }

    }


}
