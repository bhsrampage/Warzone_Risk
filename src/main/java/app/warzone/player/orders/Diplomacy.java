package app.warzone.player.orders;

import app.warzone.player.Player;

/**
 * The Diplomacy class represents an order for a player to negotiate with another player in the Warzone game.
 */
public class Diplomacy extends Order {

	Player d_issuingPlayer;
	Player d_targetPlayer;

    /**
     * Constructor for the Diplomacy order.
     *
     * @param p_issuingPlayer    The player issuing the order
     * @param p_targetPlayer     The player with whom diplomacy is to be negotiated
     */
	public Diplomacy(Player p_issuingPlayer, Player p_targetPlayer){
		d_issuingPlayer = p_issuingPlayer;
		d_targetPlayer = p_targetPlayer;
	}

    /**
     * Checks if the Diplomacy order is valid.
     *
     * @return true if the order is valid, false otherwise.
     */
    public boolean isValid() {
        if (d_issuingPlayer.d_holdingCards.contains("negotiate")) {
            if (!d_issuingPlayer.d_playerName.equalsIgnoreCase(d_targetPlayer.d_playerName)) {
                return true;
            } else {
                System.out.println("Invalid: Both players have the same name. This is not allowed.");
                return false;
            }
        } else {
            System.out.println("Invalid: Issuing player does not contain diplomacy card");
            return false;
        }
    }
    

    /**
     * Print details of the Diplomacy order.
     */
    public void printOrder() {
        System.out.println("Order Type: Diplomacy\nIssuing Player: " + d_issuingPlayer.d_playerName +
                "\nTarget Player: " + d_targetPlayer.d_playerName);
    }

    /**
     * Execute the Diplomacy order, and remove the card from the holding list
     */
	@Override
	public void execute() {
		printOrder();	
		if (isValid()) {
			d_issuingPlayer.addDiplomacyPlayer(d_targetPlayer);
			d_targetPlayer.addDiplomacyPlayer(d_issuingPlayer);
			d_issuingPlayer.removeCardFromHolding("negotiate");
		}
	}

}