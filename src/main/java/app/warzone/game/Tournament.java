package app.warzone.game;


import app.warzone.map.Map;
import app.warzone.map.MapUtils;
import app.warzone.player.Player;

import java.util.ArrayList;
import java.util.List;

import static app.warzone.game.GameUtils.d_playerList;
import static app.warzone.game.GameEngine.gamePhase;
import static app.warzone.game.GameEngine.d_gameUtil;
import app.warzone.map.MapUtils.*;

public class Tournament {

    static ArrayList<String> d_mapsList;
    static ArrayList<String> d_playerStrategiesList;

    static int d_gamesCount;
    static int d_maximumTurns;

    Tournament() {
        d_mapsList = new ArrayList<>();
        d_playerStrategiesList = new ArrayList<>();
    }

    public static void startTournament(List<String> p_arguments) {

        d_mapsList = new ArrayList<>();
        d_playerStrategiesList = new ArrayList<>();
        d_playerList.clear();

        if (!validateTournamentCommands(p_arguments)) {
            System.out.println("Validation Failed!!! So Exiting Tournament Mode...");
            return;
        }

        for (int i = 0; i < d_playerStrategiesList.size(); i++) {
            Player l_player = new Player(d_playerStrategiesList.get(i) + i, d_playerStrategiesList.get(i));
            System.out.println("Player Name and Strategy : " + l_player.d_playerName + "   " + l_player.d_strategy);
            d_playerList.add(l_player);
        }

        for (int i = 0; i < d_mapsList.size(); i++) {
            List<String> l_mapName = new ArrayList<>();
            l_mapName.add(d_mapsList.get(i));
            boolean l_mapLoadStatus = false;

            for (int j = 0; j < d_gamesCount; j++) {

                try {
                    l_mapLoadStatus = app.warzone.map.MapUtils.d_isMapLoaded;

                    if (!l_mapLoadStatus) {
                        System.out.println("Map " + d_mapsList.get(i) + " could not be loaded, moving to next one");
                        continue;
                    }


                    System.out.println("\n\n\n\n\nGAME " + (j + 1) + " FOR MAP " + (i + 1) + ":\n\n\n");

                    automaticGame();

                    MapUtils.clearMapData();
                } catch (Exception e) {
                }
                for (Player l_player : d_playerList)
                    l_player.clearPlayerData();

            }
        }
        d_playerStrategiesList.clear();
        d_mapsList.clear();
        d_playerList.clear();

    }

    public static void automaticGame() /* throws InvalidMapException */ {
        String l_Winner = "";

        gamePhase.assignCountries();
        System.out.println("\n\nCountries assigning DONE\n");

        for (int turns=0; turns < d_maximumTurns; turns++)
        {
            d_gameUtil.assignReinforcementArmies();
            gamePhase.createOrders();
            gamePhase.executeOrders();

            if (gameWinnerName() != "") {
                l_Winner = gameWinnerName();
                System.out.println(l_Winner + " Won the Game!!!");

                break;
            }
        }
        if (l_Winner.equals("")) {
            System.out.println("\nTurns Exceeded and Nobody won, So It is a DRAW Match.\n\n\n");
        }

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
