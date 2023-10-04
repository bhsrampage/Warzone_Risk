package app.warzone.game.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;

public class Deploy extends Order{

    Player d_deployingPlayer;
    int d_armyCount;
    Country d_targetCountry;
    boolean d_isExecuted;
    public Deploy(Player p_player, int p_deployArmyCount, Country p_targetCountry) {
        d_deployingPlayer = p_player;
        d_armyCount = p_deployArmyCount;
        d_targetCountry = p_targetCountry;
        d_isExecuted = false;

        d_deployingPlayer.d_currentArmyCount -= d_armyCount;
    }
    @Override
    void execute() {
        if (d_targetCountry == null) {
            System.out.println("You do not Hold the Country.");
        }
        if (d_targetCountry.getCountryHolder() != d_deployingPlayer) {
            System.out.printf("%s cannot deploy to %s since %s are not the Holder", d_deployingPlayer.d_playerName, d_targetCountry.getD_countryName(), d_deployingPlayer.d_playerName);
            return;
        }
        if (d_armyCount > d_deployingPlayer.d_currentArmyCount) {
            System.out.printf("%s cannot deploy to %s since %s are deploying more armies than they hold.", d_deployingPlayer.d_playerName, d_targetCountry.getD_countryName(), d_deployingPlayer.d_playerName);
            return;
        }
        d_targetCountry.assignHolderWithArmies(d_deployingPlayer, d_targetCountry.getCurrentArmyCount()+d_armyCount);
        d_isExecuted = true;
    }
}
