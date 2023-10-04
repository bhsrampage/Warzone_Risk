package app.warzone;

import app.warzone.game.GameEngine;

/**
 * This is the main runner class which is the root of the Warzone project. It
 * serves as the entry point for the Warzone game application.
 * 
 * @author Burhanuddin Savliwala
 */
public class Main {
	/**
	 * An enumeration representing the different phases of the Warzone game. -
	 * MAP_ACTIONS: Phase for map editing. - GAMEPLAY: Phase for gameplay. -
	 * STARTUP: Phase for game setup.
	 */
	public enum Phase {
		MAP_ACTIONS, GAMEPLAY, STARTUP
	}

	/**
	 * The main method of the Warzone game application. It initializes and starts
	 * the GameEngine to manage the game's flow.
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		// Create an instance of the GameEngine
		GameEngine l_game = new GameEngine();

		// Initialize and start the game
		l_game.initialize();
	}
}
