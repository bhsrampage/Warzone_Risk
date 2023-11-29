package app.warzone.player.orders;

/**
 * This is an abstract base class for various orders that can be issued by players in the Warzone game.
 * It provides a generic structure for orders but does not contain any specific implementation.
 * Subclasses of this abstract class will define the behavior of individual orders.
 */
public abstract class Order {
    public abstract void execute();

    public abstract void printOrder();

    public abstract boolean isValid();
}
