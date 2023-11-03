package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

/**
 * The Preload class represents the initial phase of the Warzone game, where the map is loaded.
 * It allows loading the map and transitioning to the PostLoad phase.
 */
public class Preload extends Edit {

    /**
     * Constructor for the Preload phase.
     *
     * @param p_ge The GameEngine instance associated with this phase.
     */
    public Preload(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Load a map using the specified arguments and transition to the PostLoad phase.
     *
     * @param arguments The list of arguments needed to load the map.
     */
    @Override
    public void loadMap(List<String> arguments) {
        ge.d_targetMapUtil.editMap(arguments);
        ge.setPhase(new PostLoad(ge));
    }

    /**
     * Display an error message indicating that editing a country is not allowed in the Preload phase.
     *
     * @param arguments The list of arguments for editing a country.
     */
    @Override
    public void editCountry(List<String> arguments) {
        printInvalidCommandMessage();
    }

    /**
     * Display an error message indicating that editing a continent is not allowed in the Preload phase.
     *
     * @param arguments The list of arguments for editing a continent.
     */
    @Override
    public void editContinent(List<String> arguments) {
        printInvalidCommandMessage();
    }

    /**
     * Display an error message indicating that editing neighbors is not allowed in the Preload phase.
     *
     * @param arguments The list of arguments for editing neighbors.
     */
    @Override
    public void editNeighbour(List<String> arguments) {
        printInvalidCommandMessage();
    }

    /**
     * Display an error message indicating that map validation is not allowed in the Preload phase.
     */
    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Display an error message indicating that saving the map is not allowed in the Preload phase.
     */
    public void saveMap() {
        printInvalidCommandMessage();
    }

    /**
     * Display an error message indicating that showing the map is not allowed in the Preload phase.
     */
    public void showMap() {
        printInvalidCommandMessage();
    }

    /**
     * Display a message indicating that a map must be loaded before proceeding to the next phase.
     */
    public void next() {
        System.out.println("Must load map");
    }
}