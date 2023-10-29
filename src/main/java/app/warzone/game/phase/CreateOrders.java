package app.warzone.game.phase;

import app.warzone.game.GameEngine;
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
        List<Player> l_currPlayingPlayers = new ArrayList<>(ge.d_gameUtil.d_playerList);
        int l_i = 0;
        Player l_targetPlayer;

        while (!l_currPlayingPlayers.isEmpty()) {
            l_targetPlayer = l_currPlayingPlayers.get(l_i % l_currPlayingPlayers.size());
            if (l_targetPlayer.d_currentArmyCount == 0) {
                l_currPlayingPlayers.remove(l_targetPlayer);
            } else {
                l_targetPlayer.issue_order();
                l_i++;
            }
        }
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
