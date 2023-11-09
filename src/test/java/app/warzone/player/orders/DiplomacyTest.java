package app.warzone.player.orders;

import app.warzone.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit test class for the Diplomacy class.
 */
public class DiplomacyTest {

    private Player d_issuingPlayer;
    private Player d_targetPlayer;

    /**
     * Test the validity of a Diplomacy order with a valid scenario.
     */
    @Test
    public void testValidDiplomacyOrder() {
        d_issuingPlayer = new Player("Player 1");
        d_targetPlayer = new Player("Player 2");
        d_issuingPlayer.addCardToHolding("negotiate");
        Diplomacy d_diplomacy = new Diplomacy(d_issuingPlayer, d_targetPlayer);
        assertTrue(d_diplomacy.isValid());
    }

    /**
     * Test the validity of a Diplomacy order when the issuing player is the same as the target player.
     */
    @Test
    public void testInvalidDiplomacyOrderSamePlayer() {
        d_issuingPlayer = new Player("Player1");
        d_issuingPlayer.addCardToHolding("negotiate");
        Diplomacy d_diplomacy = new Diplomacy(d_issuingPlayer, d_issuingPlayer);
        assertFalse(d_diplomacy.isValid());
    }

    /**
     * Test the validity of a Diplomacy order when the issuing player does not have a diplomacy card.
     */
    @Test
    public void testInvalidDiplomacyOrderNoDiplomacyCard() {
        d_issuingPlayer = new Player("Player1");
        d_targetPlayer = new Player("Player2");
        Diplomacy d_diplomacy = new Diplomacy(d_issuingPlayer, d_targetPlayer);
        assertFalse(d_diplomacy.isValid());
    }

    /**
     * Test the execution of a valid Diplomacy order.
     */
    @Test
    public void testExecuteValidDiplomacyOrder() {
        d_issuingPlayer = new Player("Player1");
        d_targetPlayer = new Player("Player2");
        d_issuingPlayer.addCardToHolding("negotiate");
        Diplomacy d_diplomacy = new Diplomacy(d_issuingPlayer, d_targetPlayer);
        d_diplomacy.execute();
        assertTrue(d_issuingPlayer.d_diplomacyPlayers.contains(d_targetPlayer));
        assertTrue(d_targetPlayer.d_diplomacyPlayers.contains(d_issuingPlayer));
        assertFalse(d_issuingPlayer.d_holdingCards.contains("negotiate"));
    }
}
