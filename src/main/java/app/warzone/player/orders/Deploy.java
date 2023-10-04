package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;

public class Deploy extends Order {

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
	public void execute() {
		d_targetCountry.assignHolderWithArmies(d_deployingPlayer, d_targetCountry.getCurrentArmyCount() + d_armyCount);
		d_isExecuted = true;
	}
}
