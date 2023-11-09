
package app.warzone.game.phase;

import app.warzone.game.GameEngine;
import app.warzone.game.GameUtils;

import java.util.List;

public class PlaySetup extends Play {

    public PlaySetup(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Function to load the map for the game
     * @param arguments For reading the name of the map
     */
    @Override
    public void loadMap(List<String> arguments) {
        ge.d_gameUtil.loadMap(arguments);
    }

    /**
     *
     */
    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Function to create or remove players
     * @param arguments for options to add remove players with their names
     */
    @Override
    public void setPlayers(List<String> arguments) {
        ge.d_gameUtil.addRemovePlayers(arguments);
    }

    /**
     * Assign countries randomly to  all players
     */
    public void assignCountries() {
        if(ge.d_gameUtil.d_playerList.isEmpty()) {
            System.out.println("No players are added");
            return;
        }
        if(GameUtils.d_currTargetMap == null){
            System.out.println("Map isn't loaded");
            return;
        }
        ge.d_gameUtil.assignCountries();
        this.next();
    }

    public void endGame() {
        printInvalidCommandMessage();
    }

    @Override
    public void createOrders() {
        printInvalidCommandMessage();
    }

    @Override
    public void executeOrders() {
        printInvalidCommandMessage();
    }

    /**
     * Move towards create order phase
     */
    public void next() {
        System.out.println("Now play");
        ge.setPhase(new CreateOrders(ge));
    }
}
