package app.warzone.game.phase;

import app.warzone.game.GameEngine;
import app.warzone.game.GameUtils;
import app.warzone.game.progress.SaveGame;
import app.warzone.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Class for issuing orders for gameplay on each player's turn
 */
public class CreateOrders extends MainPlay {
    CreateOrders(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Main create order function that loops through the current players and collects their gameplay orders
     */
    @Override
    public void createOrders() {
        List<Player> l_currPlayingPlayers = new ArrayList<>(GameUtils.d_playerList);
        l_currPlayingPlayers.removeIf(p -> p.d_hasLost); //Removing lost players from currently playing player list
        if (l_currPlayingPlayers.size() == 1) {
            GameEngine.setPhase(new End(ge));
            System.out.println("This game has concluded\n WINNER:" + l_currPlayingPlayers.get(0).d_playerName);
            GameUtils.updateLog("This game has concluded\n WINNER:" + l_currPlayingPlayers.get(0).d_playerName + "\n", "end");
            return;
        }
        int l_i = 0;
        Player l_targetPlayer;

        //Clear diplomacy list before every turn
        for (Player l_player : l_currPlayingPlayers){
            l_player.d_diplomacyPlayers.clear();
        }

        //Take orders from players
        while (!l_currPlayingPlayers.isEmpty()) {
            l_targetPlayer = l_currPlayingPlayers.get(l_i % l_currPlayingPlayers.size());
            if (l_targetPlayer.d_hasCommittedOrders) {
                l_currPlayingPlayers.remove(l_targetPlayer);
            } else {
                showMap();
                l_targetPlayer.issue_order();
                l_i++;
            }
        }
        System.out.println("All orders received !! Now executing");
        GameUtils.updateLog("All orders received !! Now executing", "effect");
        next();
    }

    @Override
    public void executeOrders() {
        printInvalidCommandMessage();
    }

    @Override
    public void saveGame(List<String> p_arguments) {
        new SaveGame(GameUtils.d_playerList, GameUtils.d_currTargetMap).gameSave(p_arguments.get(1));
        System.out.printf("\n Saved this game to %s.game",p_arguments.get(1));
        GameEngine.setPhase(new End(ge));
    }

    @Override
    public void loadGame(List<String> p_arguments) {
        printInvalidCommandMessage();
    }


    /**
     * Sets phase to executing the collected orders
     */
    @Override
    public void next() {
        GameEngine.setPhase(new ExecuteOrders(ge));
    }
}
