package app.warzone.player.strategy;

import app.warzone.player.Player;
import app.warzone.player.orders.Order;

public class RandomStrategy extends PlayerStrategy{
    public RandomStrategy(Player p_player) {
        super(p_player);
    }

    @Override
    public Order createOrder() {
        return null;
    }

    public String getStrategyName() {
        return "random";
    }
}
