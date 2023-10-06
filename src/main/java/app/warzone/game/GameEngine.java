package app.warzone.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import app.warzone.Main.Phase;
import app.warzone.map.MapUtils;
import app.warzone.player.Player;
import app.warzone.player.orders.Order;

/**
 * This class is responsible for generating the flow of game play by maintaining
 * the rules of the Warzone game.
 */
public class GameEngine {
	Scanner SCAN;
	Phase d_currPhase = null;

//	public GameEngine() {
//		SCAN = new Scanner(System.in);
//	}

	public Phase getD_currPhase() {
		return d_currPhase;
	}

	/**
	 * Listen to map editing commands and perform corresponding actions.
	 */
	void listenMapCommands() {
		System.out.println("**Map Editor**\n");
		MapUtils l_targetMapUtil = new MapUtils();
		boolean l_isEditing = true;

		while (l_isEditing) {
			String l_userInput = SCAN.nextLine();
			String[] l_cmdTokens = l_userInput.split(" ");
			List<String> arguments = Arrays.asList(Arrays.copyOfRange(l_cmdTokens, 1, l_cmdTokens.length));

			switch (l_cmdTokens[0]) {
			case "editcontinent":
				l_targetMapUtil.editContinent(arguments);
				break;
			case "editcountry":
				l_targetMapUtil.editCountry(arguments);
				break;
			case "editneighbor":
				l_targetMapUtil.editNeighbour(arguments);
				break;
			case "savemap":
				l_targetMapUtil.saveMap();
				break;
			case "editmap":
				l_targetMapUtil.editMap(arguments);
				break;
			case "validatemap":
				l_targetMapUtil.validateMap();
				break;
			case "showmap":
				l_targetMapUtil.showMap();
				break;
			case "exit":
				l_isEditing = false;
				break;
			default:
				System.out.println("Invalid Command try again..");
			}
		}

	}

	/**
	 * Listen to startup commands to set up the game.
	 */
	void listenStartupCommands() {
		System.out.println("**Gameplay**\n");
		GameUtils l_gameUtil = new GameUtils();
		boolean l_isSettingUp = true;

		while (l_isSettingUp) {
			String l_userInput = SCAN.nextLine();
			String[] l_cmdTokens = l_userInput.split(" ");
			List<String> arguments = Arrays.asList(Arrays.copyOfRange(l_cmdTokens, 1, l_cmdTokens.length));

			switch (l_cmdTokens[0]) {
			case "loadmap":
				l_gameUtil.loadMap(arguments);
				break;
			case "gameplayer":
				l_gameUtil.addRemovePlayers(arguments);
				break;
			case "assigncountries":
				l_gameUtil.assignCountries();
				d_currPhase = Phase.GAMEPLAY;
				l_isSettingUp = false;
				break;
			case "showmap":
				l_gameUtil.showMap();
				break;
			default:
				System.out.println("Invalid Command try again..");
				break;
			}
		}
		listenGameplayCommands(l_gameUtil);
	}

	/**
	 * Take the game command as the input and execute it. Display map after
	 * execution
	 *
	 * @param p_gameUtil The GameUtils object containing game-related utilities.
	 */
	private void listenGameplayCommands(GameUtils p_gameUtil) {
		List<Player> l_currPlayingPlayers = new ArrayList<>(p_gameUtil.d_playerList);
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

		for (Player l_player : p_gameUtil.d_playerList) {
			while (true) {
				Order l_order = l_player.next_order();
				if (l_order == null)
					break;
				l_order.execute();
			}
			p_gameUtil.showMap();
		}
	}

	/**
	 * Initialize the game and provide options for map editing or gameplay.
	 */

	public void initialize() {
		SCAN = new Scanner(System.in);
		System.out.println("Welcome to Risk (Warzone) by U6 build1");
		String l_choice;
		boolean l_playing = true;
		while (l_playing) {
			System.out.println("1. Map Editor\n2. Play Game\nEnter your choice:- ");
			l_choice = SCAN.nextLine();
			switch (l_choice) {
			case "1":
				d_currPhase = Phase.MAP_ACTIONS;
				listenMapCommands();
				break;
			case "2":
				d_currPhase = Phase.STARTUP;
				listenStartupCommands();
				break;
			default:
				l_playing = false;
				break;
			}
			System.out.println("Quitting...");
		}

	}

}