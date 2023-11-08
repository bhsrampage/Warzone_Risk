package app.warzone.game.phase;

import app.warzone.game.GameEngine;
import app.warzone.game.GameUtils;
import app.warzone.player.Player;
import app.warzone.player.orders.Order;

import java.util.ArrayList;

/**
 * Concrete Class for executing the received orders for all players
 */
public class ExecuteOrders extends MainPlay{
    ExecuteOrders(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void createOrders() {
        printInvalidCommandMessage();
    }

    /**
     * The method that executed orders for all players
     * by iterating through list of players and calling execute order function
     * @see Player
     */
    @Override
    public void executeOrders() {
        ArrayList<Player> l_executingPlayers = new ArrayList<>(GameUtils.d_playerList);
        Player l_targetPlayer;
        Order l_currOrder;
        int l_i = 0;
            while (!l_executingPlayers.isEmpty()) {
                l_targetPlayer = l_executingPlayers.get(l_i % l_executingPlayers.size());
                l_currOrder = l_targetPlayer.next_order();
                if (l_currOrder == null) {
                    l_targetPlayer.d_hasCommittedOrders = false;
                    l_executingPlayers.remove(l_targetPlayer);
                } else {
                    l_currOrder.execute();
                    l_i++;
                }
            }
        ge.setPhase(new CreateOrders(ge));
    }

    /**
     *
     */
    @Override
    public void next() {
        printInvalidCommandMessage();
    }
}
