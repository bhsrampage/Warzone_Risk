package app.warzone.player.strategy;

import app.warzone.player.Player;
import app.warzone.player.orders.Order;

public class AggressiveStrategy extends PlayerStrategy{
    public AggressiveStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * @return
     */
    @Override
    public Order createOrder() {
        return null;
    }

    @Override
    public String getStrategyName() {
        return "aggressive";
    }
}
