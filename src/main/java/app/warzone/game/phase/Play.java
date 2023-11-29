
package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

/**
 * ConcreteState of the State pattern. In this example, defines behavior
 * for commands that are valid in this state, and for the others signifies
 * that the command is invalid.
 * <p>
 * This state represents a group of states, and defines the behavior
 * that is common to all the states in its group. All the states in its
 * group need to extend this class.
 */
public abstract class Play extends Phase {

    Play(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * At any point while playing showmap command should be accessible
     */
    public void showMap() {
        ge.d_gameUtil.showMap();
    }

    @Override
    public void editCountry(List<String> arguments) {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinent(List<String> arguments) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighbour(List<String> arguments) {
        printInvalidCommandMessage();
    }

    public void saveMap() {
        printInvalidCommandMessage();
    }

    public void endGame() {
        GameEngine.setPhase(new End(ge));
    }
}
