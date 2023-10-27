package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

public class PlaySetup extends Play {

    public PlaySetup(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * @param arguments
     */
    @Override
    public void loadMap(List<String> arguments) {
        System.out.println("Map has already been loaded");
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

    /**
     * @param arguments
     */
    @Override
    public void setPlayers(List<String> arguments) {
        ge.d_gameUtil.addRemovePlayers(arguments);
    }

    public void assignCountries() {
        ge.d_gameUtil.assignCountries();
    }

    public void endGame() {
        printInvalidCommandMessage();
    }

    public void next() {
        System.out.println("Now play");
    }
}
