package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

public class End extends Phase {

    public End(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * @param arguments
     */
    @Override
    public void loadMap(List<String> arguments) {
        printInvalidCommandMessage();
    }

    public void showMap() {
        printInvalidCommandMessage();
    }

    /**
     * @param arguments
     */
    @Override
    public void editCountry(List<String> arguments) {
        printInvalidCommandMessage();

    }

    /**
     * @param arguments
     */
    @Override
    public void editContinent(List<String> arguments) {
        printInvalidCommandMessage();
    }

    /**
     * @param arguments
     */
    @Override
    public void editNeighbour(List<String> arguments) {
        printInvalidCommandMessage();
    }

    /**
     *
     */
    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    public void saveMap() {
        printInvalidCommandMessage();
    }

    /**
     * @param arguments
     */
    @Override
    public void setPlayers(List<String> arguments) {
        printInvalidCommandMessage();
    }

    public void assignCountries() {
        printInvalidCommandMessage();
    }

    public void endGame() {
        printInvalidCommandMessage();
    }

    public void next() {
        printInvalidCommandMessage();
    }
}
