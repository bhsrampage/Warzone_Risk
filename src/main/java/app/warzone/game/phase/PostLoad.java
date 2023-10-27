package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

public class PostLoad extends Edit {

    PostLoad(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * @param arguments
     */
    @Override
    public void loadMap(List<String> arguments) {
        ge.d_targetMapUtil.editMap(arguments);
    }

    public void showMap() {
        ge.d_targetMapUtil.showMap();
    }

    /**
     * @param arguments
     */
    @Override
    public void editCountry(List<String> arguments) {
        ge.d_targetMapUtil.editCountry(arguments);
    }

    /**
     * @param arguments
     */
    @Override
    public void editContinent(List<String> arguments) {
        ge.d_targetMapUtil.editCountry(arguments);
    }

    /**
     * @param arguments
     */
    @Override
    public void editNeighbour(List<String> arguments) {
        ge.d_targetMapUtil.editNeighbour(arguments);
    }

    /**
     *
     */
    @Override
    public void validateMap() {
        ge.d_targetMapUtil.validateMap();
    }

    public void saveMap() {
        ge.d_targetMapUtil.saveMap();
        ge.setPhase(null);
    }

    /**
     * @param arguments
     */
    @Override
    public void setPlayers(List<String> arguments) {
        printInvalidCommandMessage();
    }

    public void next() {
        System.out.println("Must save map");
    }
}
