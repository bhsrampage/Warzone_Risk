package app.warzone;

import app.warzone.game.GameEngine;

/**
 * This is the main runner class which is the root of the project
 * @author Burhanuddin
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