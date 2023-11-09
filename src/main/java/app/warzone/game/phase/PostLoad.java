
package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

/**
 * The PostLoad class represents the phase of the Warzone game after loading the map.
 * It allows editing the map, showing the map, and performing various map-related actions.
 */
public class PostLoad extends Edit {

    /**
     * Constructor for the PostLoad phase.
     *
     * @param p_ge The GameEngine instance associated with this phase.
     */
    PostLoad(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Load a map using the specified arguments.
     *
     * @param arguments The list of arguments needed to load the map.
     */
    @Override
    public void loadMap(List<String> arguments) {
        ge.d_targetMapUtil.editMap(arguments);
    }

    /**
     * Show the currently loaded map.
     */
    public void showMap() {
        ge.d_targetMapUtil.showMap();
    }

    /**
     * Edit a country on the map using the provided arguments.
     *
     * @param arguments The list of arguments needed to edit a country.
     */
    @Override
    public void editCountry(List<String> arguments) {
        ge.d_targetMapUtil.editCountry(arguments);
    }

    /**
     * Edit a continent on the map using the provided arguments.
     *
     * @param arguments The list of arguments needed to edit a continent.
     */
    @Override
    public void editContinent(List<String> arguments) {
        ge.d_targetMapUtil.editContinent(arguments);
    }

    /**
     * Edit the neighbors of a country on the map using the provided arguments.
     *
     * @param arguments The list of arguments needed to edit the neighbors of a country.
     */
    @Override
    public void editNeighbour(List<String> arguments) {
        ge.d_targetMapUtil.editNeighbour(arguments);
    }

    /**
     * Validate the current map for correctness.
     */
    @Override
    public void validateMap() {
        ge.d_targetMapUtil.validateMap();
    }

    /**
     * Save the edited map and transition to the next phase.
     */
    public void saveMap() {
        ge.d_targetMapUtil.saveMap();
        ge.setPhase(null);
    }

    /**
     * Display a message indicating that the map must be saved before proceeding to the next phase.
     */
    public void next() {
        System.out.println("Must save map");
    }
}
