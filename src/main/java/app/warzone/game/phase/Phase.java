
package app.warzone.game.phase;

import app.warzone.game.GameEngine;
import app.warzone.game.GameUtils;

import java.util.List;

/**
 * State of the State pattern. Here implemented as a abstract class.
 * <p>
 * There are many states, and even a hierarchy of states:
 * <p>
 * Phase
 * Edit phase (abstract)
 * Preload
 * Postload
 * Play (abstract)
 * PlaySetup
 * MainPlay
 * CreateOrders
 * ExecuteOrders
 * End
 * <p>
 * In each state, nextState() is defined so that it goes to the next state
 */
public abstract class Phase {

    /**
     * Contains a reference to the State of the GameEngine
     * so that the state object can change the state of
     * the GameEngine to transition between states.
     */
    GameEngine ge;

    Phase(GameEngine p_ge) {
        ge = p_ge;
    }

    // common commands
    abstract public void loadMap(List<String> p_arguments);

    abstract public void showMap();

    // Edit map commands
    abstract public void editCountry(List<String> p_arguments);

    abstract public void editContinent(List<String> p_arguments);

    abstract public void editNeighbour(List<String> p_arguments);

    abstract public void validateMap();

    abstract public void saveMap();

    // Play commands
    // game setup commands
    abstract public void setPlayers(List<String> p_arguments);

    abstract public void assignCountries();

    // end command
    abstract public void endGame();

    abstract public void createOrders();

    abstract public void executeOrders();

    abstract public void saveGame(List<String> p_arguments);

    abstract public void loadGame(List<String> p_arguments);

    // go to next phase
    abstract public void next();

    /**
     * Common method to all States.
     */
    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state " + this.getClass().getSimpleName());
        GameUtils.updateLog("Invalid command in state " + this.getClass().getSimpleName(), "effect");
    }
}
