package app.warzone.game.phase;

import app.warzone.game.GameEngine;
import app.warzone.game.GameUtils;
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
            ge.setPhase(new End(ge));
            System.out.println("This game has concluded\n WINNER:" + l_currPlayingPlayers.get(0).d_playerName);
            ge.d_gameUtil.updateLog("This game has concluded\n WINNER:" + l_currPlayingPlayers.get(0).d_playerName + "\n", "end");
            return;
        }
        int l_i = 0;
        Player l_targetPlayer;

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
        ge.d_gameUtil.updateLog("All orders received !! Now executing", "effect");
        next();
    }

    @Override
    public void executeOrders() {
        printInvalidCommandMessage();
    }


    /**
     * Sets phase to executing the collected orders
     */
    @Override
    public void next() {
        ge.setPhase(new ExecuteOrders(ge));
    }
}
