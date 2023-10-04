package app.warzone.player.orders;

import app.warzone.map.Country;
import app.warzone.player.Player;

/**
 * The Deploy class represents a deployment order issued by a player in the
 * Warzone game.
 */
public class Deploy extends Order {

	Player d_deployingPlayer;
	int d_armyCount;
	Country d_targetCountry;
	boolean d_isExecuted;

	/**
	 * Constructor for the Deploy order.
	 *
	 * @param p_player          The player issuing the deployment order.
	 * @param p_deployArmyCount The number of armies to deploy.
	 * @param p_targetCountry   The target country where the deployment will occur.
	 */
	public Deploy(Player p_player, int p_deployArmyCount, Country p_targetCountry) {
		d_deployingPlayer = p_player;
		d_armyCount = p_deployArmyCount;
		d_targetCountry = p_targetCountry;
		d_isExecuted = false;
		d_deployingPlayer.d_currentArmyCount -= d_armyCount;
	}

	/**
	 * Execute the deployment order by assigning armies to the target country. After
	 * execution, the deployment order is marked as executed.
	 */
	@Override
	public void execute() {
		d_targetCountry.assignHolderWithArmies(d_deployingPlayer, d_targetCountry.getCurrentArmyCount() + d_armyCount);
		d_isExecuted = true;
	}
}
