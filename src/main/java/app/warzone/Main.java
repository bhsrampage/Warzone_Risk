package app.warzone;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import app.warzone.game.GameEngine;

/**
 * This is the main runner class which is the root of the project
 * @author Burhanuddin Savliwala
 */
public class Main {
   public enum Phase {
        MAP_ACTIONS,
        GAMEPLAY,
       STARTUP
    }
    public static void main(String[] args) {
        GameEngine l_game = new GameEngine();
        l_game.initialize();
    }
}