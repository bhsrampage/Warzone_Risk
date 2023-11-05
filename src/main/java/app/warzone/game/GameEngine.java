package app.warzone.game;

import app.warzone.game.phase.*;
import app.warzone.map.MapUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for generating the flow of game play by maintaining
 * the rules of the Warzone game.
 */
public class GameEngine {
    Scanner SCAN;
    public MapUtils d_targetMapUtil;

    public GameUtils d_gameUtil;
    Phase gamePhase;

    /**
     * Method that allows the GameEngine object to change its state.
     *
     * @param p_phase new state to be set for the GameEngine object.
     */
    public void setPhase(Phase p_phase) {
        gamePhase = p_phase;
        System.out.println("New phase: " + (p_phase == null ? "Main Menu" : p_phase.getClass().getSimpleName()));
    }

    /**
     * Listen to map editing commands and perform corresponding actions.
     */
    void listenMapCommands() {
        System.out.println("**Map Editor**\n");
        d_targetMapUtil = new MapUtils();

        while (gamePhase instanceof Edit) {
            String l_userInput = SCAN.nextLine();
            String[] l_cmdTokens = l_userInput.split(" ");
            List<String> arguments = Arrays.asList(Arrays.copyOfRange(l_cmdTokens, 1, l_cmdTokens.length));

            switch (l_cmdTokens[0]) {
                case "editcontinent":
                    gamePhase.editContinent(arguments);
                    break;
                case "editcountry":
                    gamePhase.editCountry(arguments);
                    break;
                case "editneighbor":
                    gamePhase.editNeighbour(arguments);
                    break;
                case "savemap":
                    gamePhase.saveMap();
                    break;
                case "editmap":
                    gamePhase.loadMap(arguments);
                    break;
                case "validatemap":
                    gamePhase.validateMap();
                    break;
                case "showmap":
                    gamePhase.showMap();
                    break;
                case "exit":
                    setPhase(new End(this));
                    break;
                default:
                    System.out.println("Invalid Command try again..");
                    break;
            }
        }

    }

    /**
     * Listen to startup commands to set up the game.
     */
    void listenStartupCommands() {
        System.out.println("**Gameplay**\n");
        d_gameUtil = new GameUtils();

        while (gamePhase instanceof PlaySetup) {
            String l_userInput = SCAN.nextLine();
            String[] l_cmdTokens = l_userInput.split(" ");
            List<String> arguments = Arrays.asList(Arrays.copyOfRange(l_cmdTokens, 1, l_cmdTokens.length));

            switch (l_cmdTokens[0]) {
                case "loadmap":
                    gamePhase.loadMap(arguments);
                    break;
                case "gameplayer":
                    gamePhase.setPlayers(arguments);
                    break;
                case "assigncountries":
                    gamePhase.assignCountries();
                    break;
                case "showmap":
                    gamePhase.showMap();
                    break;
                default:
                    System.out.println("Invalid Command try again..");
                    break;
            }
        }
        listenGameplayCommands();
    }

    /**
     * Take the game command as the input and execute it. Display map after
     * execution
     */
    private void listenGameplayCommands() {
        while(!(gamePhase instanceof End)) {
            gamePhase.createOrders();
            gamePhase.executeOrders();
        }

    }

    /**
     * Initialize the game and provide options for map editing or gameplay.
     */

    public void initialize() {
        SCAN = new Scanner(System.in);
        System.out.println("Welcome to Risk (Warzone) by U6 build1");
        String l_choice;
        while (!(gamePhase instanceof End)) {
            System.out.println("1. Map Editor\n2. Play Game\n3. Exit\n Enter your choice:- ");
            l_choice = SCAN.nextLine();
            switch (l_choice) {
                case "1":
                    setPhase(new Preload(this));
                    listenMapCommands();
                    break;
                case "2":
                    setPhase(new PlaySetup(this));
                    listenStartupCommands();
                    break;
                case "3":
                    setPhase(new End(this));
                default:
                    System.out.println("Invalid command Try again");
                    break;
            }
            System.out.println("Quitting...");
        }

    }

}