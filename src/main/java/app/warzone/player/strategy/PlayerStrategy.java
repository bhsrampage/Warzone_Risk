package app.warzone.player.strategy;

import app.warzone.player.Player;
import app.warzone.player.orders.Order;

public abstract class PlayerStrategy {

    protected Player d_targetPlayer;
    public PlayerStrategy(Player p_player){
        d_targetPlayer = p_player;
    }

    abstract public Order createOrder();
}
