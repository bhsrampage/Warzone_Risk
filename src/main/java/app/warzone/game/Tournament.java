package app.warzone.game;

import app.warzone.game.phase.End;
import app.warzone.game.phase.PlaySetup;
import app.warzone.game.phase.TournamentEnd;
import app.warzone.player.Player;

import java.util.ArrayList;
import java.util.List;

import static app.warzone.game.GameUtils.d_playerList;

public class Tournament extends GameEngine {

    static ArrayList<String> d_mapsList;
    static ArrayList<String> d_playerStrategiesList;

    static int d_gamesCount;
    static int d_maximumTurns;

    public static GameEngine d_ge;

    Tournament() {
        d_mapsList = new ArrayList<>();
        d_playerStrategiesList = new ArrayList<>();
    }

    public static void startTournament(List<String> p_arguments) {

        d_mapsList = new ArrayList<>();
        d_playerStrategiesList = new ArrayList<>();

        if (!validateTournamentCommands(p_arguments)) {
            System.out.println("Validation Failed!!! So Exiting Tournament Mode...");
            return;
        }

        for (int i = 0; i < d_mapsList.size(); i++) {

            for (int j = 0; j < d_gamesCount; j++) {

                try {
                    System.out.println("\n\n\n\n\nGAME " + (j + 1) + " FOR MAP " + (i + 1) + ":\n\n\n");
                    automaticGame(d_mapsList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        d_playerStrategiesList.clear();
        d_mapsList.clear();
    }

    public static void automaticGame(String p_mapName) /* throws InvalidMapException */ {
        String l_Winner = "";
        GameEngine.setPhase(new PlaySetup(d_ge));
        List<String> l_args = new ArrayList<>();
        //Load map
        l_args.add(p_mapName);
        gamePhase.loadMap(l_args);
        l_args.clear();

        //Add players
        for (int i = 0; i < d_playerStrategiesList.size();i++) {
            l_args.add("-add");
            l_args.add(d_playerStrategiesList.get(i) + i);
            l_args.add(d_playerStrategiesList.get(i));
        }
        gamePhase.setPlayers(l_args);
        l_args.clear();

        //Assign Countries
        gamePhase.assignCountries();
        System.out.println("\n\nCountries assigning DONE\n");

        //Start game
        for (int turns=0; turns < d_maximumTurns; turns++)
        {
            d_gameUtil.assignReinforcementArmies();
            gamePhase.createOrders();
            gamePhase.executeOrders();

            if (gamePhase instanceof End) {
                l_Winner = gameWinnerName();
                System.out.println(l_Winner + " Won the Game!!!");
                break;
            }
        }
        if (l_Winner.isEmpty()) {
            System.out.println("\nTurns Exceeded and Nobody won, So It is a DRAW Match.\n\n\n");
        }
        GameEngine.setPhase(new TournamentEnd(d_ge));

    }

    public static String gameWinnerName() {

        for (Player l_player: d_playerList) {
            if (!l_player.d_hasLost)
                return l_player.d_playerName;
        }
        return "";
    }

    public static boolean validateTournamentCommands(List<String> p_arguments) {
        d_mapsList = new ArrayList<>();
        d_playerStrategiesList = new ArrayList<>();

        for (int i = 0; i < p_arguments.size(); i++) {
            System.out.println("Argument: " + p_arguments.get(i));
            if (p_arguments.get(i).equals("-M")) {
                i++;
                while (!p_arguments.get(i).equals("-P")) {
                    System.out.println("MAP NAME: " + p_arguments.get(i));
                    d_mapsList.add(p_arguments.get(i++));
                }
                i--;
            }
            else if (p_arguments.get(i).equals("-P")) {
                i++;
                while (!p_arguments.get(i).equals("-G")) {
                    System.out.println("STRATEGY USED: " + p_arguments.get(i));
                    d_playerStrategiesList.add(p_arguments.get(i++));
                }
                i--;
            }
            else if (p_arguments.get(i).equals("-G")) {
                i++;
                System.out.println("NUMBER OF GAMES: " + p_arguments.get(i));
                d_gamesCount = Integer.parseInt(p_arguments.get(i));
            } else if (p_arguments.get(i).equals("-D")) {
                i++;
                System.out.println("NUMBER OF TURNS: " + p_arguments.get(i));
                d_maximumTurns = Integer.parseInt(p_arguments.get(i));
            } else {
                System.out.println("Invalid command attempted. Provide commands in the following manner only\nTournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns ");
                return false;
            }
        }

        if (d_playerStrategiesList.size() > 4 || d_playerStrategiesList.size() < 2) {
            System.out.println("Only 2 to 4 Player Strategies are permitted");
            return false;
        }

        if (d_mapsList.size() > 5 || d_mapsList.isEmpty()) {
            System.out.println("Only 1 to 5 Different Maps allowed");
            return false;
        }

        if (d_gamesCount > 5 || d_gamesCount < 1) {
            System.out.println("Only 1 to 5 Games can be Played");
            return false;
        }

        if (d_maximumTurns > 50 || d_maximumTurns < 10) {
            System.out.println("Only 10 to 50 Turns allowed");
            return false;
        }


        return true;
    }



}
