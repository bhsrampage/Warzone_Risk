
package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

/**
 * Invalidates all the methods declared in Phase Class
 *
 * @see Phase
 */
public class End extends Phase {

    public End(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap(List<String> arguments) {
        printInvalidCommandMessage();
    }

    public void showMap() {
        printInvalidCommandMessage();
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

    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    public void saveMap() {
        printInvalidCommandMessage();
    }

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

    @Override
    public void createOrders() {
        printInvalidCommandMessage();
    }

    @Override
    public void executeOrders() {
        printInvalidCommandMessage();
    }

    @Override
    public void saveGame(List<String> p_arguments) {
        printInvalidCommandMessage();
    }

    @Override
    public void loadGame(List<String> p_arguments) {
printInvalidCommandMessage();
    }

    public void next() {
        printInvalidCommandMessage();
    }
}
