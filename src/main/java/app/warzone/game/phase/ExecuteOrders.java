package app.warzone.game.phase;

import app.warzone.game.GameEngine;
import app.warzone.player.Player;
import app.warzone.player.orders.Order;

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
        for (Player l_player : ge.d_gameUtil.d_playerList) {
            while (true) {
                Order l_order = l_player.next_order();
                if (l_order == null)
                    break;
                l_order.execute();
            }
            ge.d_gameUtil.showMap();
        }
        ge.setPhase(new End(ge));
    }

    /**
     *
     */
    @Override
    public void next() {
        printInvalidCommandMessage();
    }
}
