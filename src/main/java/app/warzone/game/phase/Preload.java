package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

public class Preload extends Edit {

    public Preload(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * @param arguments
     */
    @Override
    public void loadMap(List<String> arguments) {
        ge.d_targetMapUtil.editMap(arguments);
        ge.setPhase(new PostLoad(ge));
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

    public void next() {
        System.out.println("must load map");
    }
}
