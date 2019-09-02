package com.snakehunter.controller;

import com.snakehunter.GameStage;
import com.snakehunter.Player;
import com.snakehunter.view.BoardView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    private static final int NUM_OF_PLAYERS = 4;

    private GameController gameController;

    @Before
    public void setUp() throws Exception {
        gameController = new GameController(new BoardView(), getPlayers());
    }

    //region getNextPlayer
    @Test
    public void testGetNextPlayer() {
        assertEquals("Player 1", gameController.getPlayer(1).getName());
        assertEquals("Player 2", gameController.getPlayer(2).getName());
        assertEquals("Player 3", gameController.getPlayer(3).getName());
        assertEquals("Player 4", gameController.getPlayer(4).getName());

        assertEquals("Player 1", gameController.getPlayer(45).getName());
        assertEquals("Player 2", gameController.getPlayer(46).getName());
        assertEquals("Player 3", gameController.getPlayer(47).getName());
        assertEquals("Player 4", gameController.getPlayer(48).getName());
    }
    //endregion

    //region GameStage
    @Test
    public void givenSLGame_whenSLGameInitiate_thenSetToInitialStage() {
        assertEquals(GameStage.INITIAL, gameController.getGameStage());
    }
    //endregion

    //region AddGuards
    @Test
    public void givenAlreadyHave3Guards_whenAddGuard_thenReturnFalse() {
        // given
        gameController.setNumOfGuards(3);

        // when
        boolean isSuccess = gameController.addGuards(0);

        // then
        assertFalse(isSuccess);
        assertEquals(3, gameController.getNumOfGuards());
    }

    @Test
    public void givenAlreadyHave2Guards_whenAddGuard_thenNumOfGuardsIncrease_andReturnTrue() {
        // given
        gameController.setNumOfGuards(2);

        // when
        boolean isSuccess = gameController.addGuards(0);

        // then
        assertTrue(isSuccess);
        assertEquals(3, gameController.getNumOfGuards());
    }
    //endregion

    private static Map<Integer, Player> getPlayers() {
        Map<Integer, Player> players = new HashMap<>();

        for (int i = 1; i <= NUM_OF_PLAYERS; i++) {
            Player player = new Player("Player " + i);
            players.put(i % NUM_OF_PLAYERS, player);
        }
        return players;
    }
}