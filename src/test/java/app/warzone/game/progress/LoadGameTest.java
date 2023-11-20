package app.warzone.game.progress;

import app.warzone.game.GameUtils;
import app.warzone.map.Map;
import app.warzone.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoadGameTest {

    private LoadGame loadGame;

    @BeforeEach
    void setUp() {
        loadGame = new LoadGame();
        GameUtils.d_currTargetMap = new Map("testMap");
    }

    @Test
    void testGameLoadFileNotFound() {
        assertFalse(loadGame.gameLoad("nonexistentFile"));
    }

    @Test
    void testGameLoadSuccess() {
        // Assuming you have a valid game file for testing
        assertTrue(loadGame.gameLoad("savegame"));

        // Add more assertions based on your specific requirements
        assertNotNull(GameUtils.d_currTargetMap);
        assertNotNull(GameUtils.d_playerList);
        // Add more assertions as needed
    }

    @Test
    void testParsePlayers() {
        StringBuilder fileText = new StringBuilder("[players]\nplayer1,human,100,card1,card2\nplayer2,aggressive,50,card3,card4,card5[continents]");
        loadGame.parsePlayers(fileText);

        assertEquals(2, GameUtils.d_playerList.size());

        Player player1 = GameUtils.d_playerList.get(0);
        assertEquals("player1", player1.d_playerName);
        assertTrue(player1.d_isHuman);
        assertEquals(100, player1.d_currentArmyCount);
        assertEquals(2, player1.d_holdingCards.size());

        Player player2 = GameUtils.d_playerList.get(1);
        assertEquals("player2", player2.d_playerName);
        assertEquals("aggressive", player2.d_strategy.getStrategyName());
        assertEquals(50, player2.d_currentArmyCount);
        assertEquals(3, player2.d_holdingCards.size());
    }
}
