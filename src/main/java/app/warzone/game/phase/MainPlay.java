package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

/**
 * ConcreteState of the State pattern. Defines behavior
 * for commands that are valid in this state, and for the others signifies
 * that the command is invalid.
 * <p>
 * This state represents a group of states, and defines the behavior
 * that is common to all the states in its group. All the states in its
 * group need to extend this class.
 */
public abstract class MainPlay extends Play {

    MainPlay(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap(List<String> p_arguments) {
        printInvalidCommandMessage();
    }

    public void setPlayers(List<String> p_arguments){
        printInvalidCommandMessage();
    }

    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    public void assignCountries() {
        this.printInvalidCommandMessage();
    }
}
