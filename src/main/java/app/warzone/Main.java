package app.warzone;

import app.warzone.game.GameEngine;

/**
 * This is the main runner class which is the root of the project
 *
 * @author Burhanuddin
 */
public class Main {

    /**
     * The main method of the Warzone game application. It initializes and starts
     * the GameEngine to manage the game's flow.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        GameEngine l_game = new GameEngine();
        l_game.initialize();
    }
}