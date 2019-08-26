import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */

@RunWith(MockitoJUnitRunner.class)
public class SLGameNewTest {

    private static final int NUM_OF_PLAYERS = 4;

    private SLGameNew slGame;

    @Before
    public void setUp() throws Exception {
        slGame = new SLGameNew(null, new Dice(), getPlayers());
    }

    //region getNextPlayer
    @Test
    public void testGetNextPlayer() {
        assertEquals("Player 1", slGame.getPlayer(1).getName());
        assertEquals("Player 2", slGame.getPlayer(2).getName());
        assertEquals("Player 3", slGame.getPlayer(3).getName());
        assertEquals("Player 4", slGame.getPlayer(4).getName());

        assertEquals("Player 1", slGame.getPlayer(45).getName());
        assertEquals("Player 2", slGame.getPlayer(46).getName());
        assertEquals("Player 3", slGame.getPlayer(47).getName());
        assertEquals("Player 4", slGame.getPlayer(48).getName());
    }
    //endregion

    //region GameStage
    @Test
    public void givenSLGame_whenSLGameInitiate_thenSetToInitialStage() {
        assertEquals(GameStage.INITIAL, slGame.getGameStage());
    }
    //endregion

    //region AddGuards
    @Test
    public void givenAlreadyHave3Guards_whenAddGuard_thenReturnFalse() {
        // given
        slGame.setNumOfGuards(3);

        // when
        boolean isSuccess = slGame.addGuards(0);

        // then
        assertFalse(isSuccess);
        assertEquals(3, slGame.getNumOfGuards());
    }

    @Test
    public void givenAlreadyHave2Guards_whenAddGuard_thenNumOfGuardsIncrease_andReturnTrue() {
        // given
        slGame.setNumOfGuards(2);

        // when
        boolean isSuccess = slGame.addGuards(0);

        // then
        assertTrue(isSuccess);
        assertEquals(3, slGame.getNumOfGuards());
    }
    //endregion

    private static HashMap<Integer, Player> getPlayers() {
        HashMap<Integer, Player> players = new HashMap<>();

        for (int i = 1; i <= NUM_OF_PLAYERS; i++) {
            Player player = new Player("Player " + i);
            players.put(i % NUM_OF_PLAYERS, player);
        }
        return players;
    }
}