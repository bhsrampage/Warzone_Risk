package app.warzone.player.orders;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test class for the Diplomacy class.
 */
public class DiplomacyTest {

    /**
     * Test the validity of a Diplomacy order with a valid scenario.
     */
    @Test
    public void testIsValidDiplomacyOrder() {
        Player issuingPlayer = new Player("Player1");
        issuingPlayer.addCardToHolding("negotiate");
        String targetPlayer = "Player2";
        Diplomacy diplomacy = new Diplomacy(issuingPlayer, targetPlayer);
        assertTrue(diplomacy.isValid());
    }

    /**
     * Test the validity of a Diplomacy order when the issuing player is the same as the target player.
     */
    @Test
    public void testInvalidDiplomacyOrderSamePlayer() {
        Player issuingPlayer = new Player("Player1");
        issuingPlayer.addCardToHolding("negotiate");
        Diplomacy diplomacy = new Diplomacy(issuingPlayer, "Player1");
        assertFalse(diplomacy.isValid());
    }

    /**
     * Test the validity of a Diplomacy order when the issuing player does not have a diplomacy card.
     */
    @Test
    public void testnvalidDiplomacyOrderNoDiplomacyCard() {
        Player issuingPlayer = new Player("Player1");
        Player targetPlayer = new Player("Player2");
        Diplomacy diplomacy = new Diplomacy(issuingPlayer, "Player2");
        assertFalse(diplomacy.isValid());
    }

    /**
     * Test the execution of a valid Diplomacy order.
     */
    @Test
    public void testExecuteValidDiplomacyOrder() {
        Player issuingPlayer = new Player("Player1");
        issuingPlayer.addCardToHolding("negotiate");
        Player targetPlayer = new Player("Player2");
        Diplomacy diplomacy = new Diplomacy(issuingPlayer, "Player2");
        diplomacy.execute();
        assertTrue(issuingPlayer.hasDiplomacyPlayer(targetPlayer));
        assertTrue(targetPlayer.hasDiplomacyPlayer(issuingPlayer));
        assertFalse(issuingPlayer.hasCardInHolding("negotiate"));
    }
}
