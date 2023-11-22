package app.warzone.game;


import app.warzone.player.Player;
import app.warzone.map.Map;
import app.warzone.game.phase.CreateOrders;

import java.util.ArrayList;
import java.util.List;

import static app.warzone.game.GameUtils.d_playerList;

public class Tournament {

    static ArrayList<String> d_mapsList;
    static ArrayList<String> d_playerStrategiesList;

    static int d_gamesCount;
    static int d_maximumTurns;

    Tournament() {
        d_mapsList = new ArrayList<>();
        d_playerStrategiesList = new ArrayList<>();
    }

    public static void begin(List<String> p_arguments) {


    }

    public static boolean noOrdersLeftToIssue() {
        for (Player l_Player : d_playerList) {
            if (!l_Player.getD_givenOrders().isEmpty())
                return true;
        }
        return false;
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
            System.out.println("TOKEN : " + p_arguments.get(i));
            if (p_arguments.get(i).equals("-M")) {
                i++;
                while (!p_arguments.get(i).equals("-P")) {
                    System.out.println("MAP : " + p_arguments.get(i));
                    d_mapsList.add(p_arguments.get(i++));
                }
                i--;
            } else if (p_arguments.get(i).equals("-P")) {
                i++;
                while (!p_arguments.get(i).equals("-G")) {
                    System.out.println("Strategy : " + p_arguments.get(i));
                    d_playerStrategiesList.add(p_arguments.get(i++));
                }
                i--;
            } else if (p_arguments.get(i).equals("-G")) {
                i++;
                System.out.println("Number of Games : " + p_arguments.get(i));
                d_gamesCount = Integer.parseInt(p_arguments.get(i));
            } else if (p_arguments.get(i).equals("-D")) {
                i++;
                System.out.println("Number of Turns : " + p_arguments.get(i));
                d_maximumTurns = Integer.parseInt(p_arguments.get(i));
            } else {
                System.out.println("Invalid option. Tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns options");
                // d_Log.notify("Invalid option. Tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns options");
                return false;
            }
        }

        if (d_mapsList.size() > 6 || d_mapsList.size() < 1) {
            System.out.println("Only 1 to 6 Maps allowed");
            // d_Log.notify("Only 1 to 5 Maps allowed");
            return false;
        }

        if (d_playerStrategiesList.size() > 4 || d_playerStrategiesList.size() < 2) {
            System.out.println("Only 2 to 4 Player Strategies are permitted");
            // d_Log.notify("Only 2 to 4 Player Strategies allowed");
            return false;
        }

        if (d_gamesCount > 5 || d_gamesCount < 1) {
            System.out.println("Only 1 to 5 Games can be Played");
            // d_Log.notify("Only 1 to 5 Games allowed");
            return false;
        }

        if (d_maximumTurns > 100 || d_maximumTurns < 5) {
            System.out.println("Only 5 to 100 Turns allowed");
            // d_Log.notify("Only 5 to 100 Turns allowed");
            return false;
        }


        return true;
    }



}
