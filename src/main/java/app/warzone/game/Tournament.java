package app.warzone.game;

import app.warzone.game.phase.End;
import app.warzone.game.phase.Play;
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
    public static String[][] l_winners;

    /**
     * Starts a tournament based on the provided arguments.
     * Prints the winners of each game.
     *
     * @param p_arguments List of arguments for the tournament.
     */
    public static void startTournament(List<String> p_arguments) {
        if (!validateTournamentCommands(p_arguments)) {
            System.out.println("Validation Failed!!! So Exiting Tournament Mode...");
            return;
        }
        l_winners = new String[d_mapsList.size()][d_gamesCount];

        for (int i = 0; i < d_mapsList.size(); i++) {

            for (int j = 0; j < d_gamesCount; j++) {

                try {
                    System.out.println("\n\n\n\n\nGAME " + (j + 1) + " FOR MAP " + (i + 1) + ":\n\n\n");
                   l_winners[i][j] = automaticGame(d_mapsList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        String l_name = "N/A";
        int l_i = 1 , l_j = 1;
        for(String[] l_winnerList : l_winners) {
            l_j = 1;
            for (String l_winner : l_winnerList){
                if (l_winner != null) l_name = l_winner;
                System.out.printf("\n Winner of Map %d , Game %d is %s\n",l_i,l_j,l_name);
                l_j++;
            }
            l_i++;
        }
        d_playerStrategiesList.clear();
        d_mapsList.clear();
        GameEngine.setPhase(new TournamentEnd(d_ge));
    }

    /**
     * Executes an automatic game on the specified map and returns the winner's name.
     *
     * @param p_mapName The name of the map for the game.
     * @return The name of the winner of the game.
     */
    public static String automaticGame(String p_mapName) /* throws InvalidMapException */ {
        String l_Winner = null;
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
            if (gamePhase instanceof End) {
                l_Winner = gameWinnerName();
                System.out.println(l_Winner + " Won the Game!!!");
                break;
            }
            gamePhase.executeOrders();
        }
        if (l_Winner == null) {
            System.out.println("\nTurns Exceeded and Nobody won, So It is a DRAW Match.\n\n\n");
        }
        GameUtils.clearInstance();
        return l_Winner;
    }

    /**
     * Retrieves the name of the winner of the current game.
     *
     * @return The name of the game winner or null if no winner.
     */
    public static String gameWinnerName() {
        ArrayList<Player> l_tempList = new ArrayList<>(d_playerList);
        l_tempList.removeIf(p -> p.d_hasLost);
        if (l_tempList.size() == 1) return l_tempList.get(0).d_playerName;
        else return null;
    }

    /**
     * Validates the provided tournament commands.
     *
     * @param p_arguments List of arguments for the tournament.
     * @return True if the commands are valid, false otherwise.
     */
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
