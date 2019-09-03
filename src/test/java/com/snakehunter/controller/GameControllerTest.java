package com.snakehunter.controller;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameContract.GameView;
import com.snakehunter.model.Snake;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    private GameController gameController;

    @Mock
    private GameView gameView;

    @Mock
    private GameModel gameModel;

    @Before
    public void setUp() throws Exception {
        gameController = new GameController(gameView, gameModel);
    }

    @Test
    public void whenAddSnakeClick_thenCallShowSnakeBuilder() {
        // when
        gameController.onAddSnakeClick();

        // then
        verify(gameView).showSnakeBuilder();
    }

    @Test
    public void whenSnakeBuilt_thenCallAddSnake() {
        // given
        Snake snake = new Snake(10, 5);

        // when
        gameController.onSnakeBuilt(snake);

        // then
        verify(gameModel).addSnake(snake);
    }

    @Test
    public void whenAddPlayersClick_thenCallShowHowManyPlayers() {
        // when
        gameController.onAddPlayersClick();

        // then
        verify(gameView).showHowManyPlayers();
    }

    @Test
    public void givenGameIsReady_whenStartClick_thenHideTheSettingPanel_andCallNextTurn() {
        // given
        when(gameModel.isGameReady()).thenReturn(true);

        // when
        gameController.onStartClick();

        // then
        verify(gameView).hideSettingPanel();
        verify(gameModel).nextTurn();
    }

    @Test
    public void givenGameIsNotReady_whenStartClick_thenCallShowErrorDialog() {
        // given
        when(gameModel.isGameReady()).thenReturn(false);

        // when
        gameController.onStartClick();

        // then
        verify(gameView).showErrorDialog(anyString());
    }

    @Test
    public void whenDiceClick_thenCallRollTheDice() {
        // when
        gameController.onDiceClick();

        // then
        verify(gameView).rollTheDice();
    }

    @Test
    public void givenDiceRolls5_whenDiceRolled_thenMoveThePlayer_andNextTurn() {
        // when
        gameController.onDiceRolled(5);

        // then
        verify(gameModel).movePlayer(5);
        verify(gameModel).nextTurn();
    }

    @Test
    public void whenNumOfPlayersEntered_thenAddPlayers() {
        // when
        gameController.onNumOfPlayersEntered(4);

        // then
        verify(gameModel).addPlayers(4);
    }

    @Test
    public void whenInputError_thenShowErrorDialog() {
        // when
        gameController.onInputError();

        // then
        verify(gameView).showErrorDialog(anyString());
    }

    //    //region getNextPlayer
////    @Test
////    public void testGetNextPlayer() {
////        assertEquals("Player 1", gameController.getPlayer(1).getName());
////        assertEquals("Player 2", gameController.getPlayer(2).getName());
////        assertEquals("Player 3", gameController.getPlayer(3).getName());
////        assertEquals("Player 4", gameController.getPlayer(4).getName());
////
////        assertEquals("Player 1", gameController.getPlayer(45).getName());
////        assertEquals("Player 2", gameController.getPlayer(46).getName());
////        assertEquals("Player 3", gameController.getPlayer(47).getName());
////        assertEquals("Player 4", gameController.getPlayer(48).getName());
////    }
//    //endregion
//
//    //region GameStage
//    @Test
//    public void givenSLGame_whenSLGameInitiate_thenSetToInitialStage() {
//        assertEquals(GameStage.INITIAL, gameController.getGameStage());
//    }
//    //endregion
//
//    //region AddGuards
//    @Test
//    public void givenAlreadyHave3Guards_whenAddGuard_thenReturnFalse() {
//        // given
//        gameController.setNumOfGuards(3);
//
//        // when
//        boolean isSuccess = gameController.addGuards(0);
//
//        // then
//        assertFalse(isSuccess);
//        assertEquals(3, gameController.getNumOfGuards());
//    }
//
//    @Test
//    public void givenAlreadyHave2Guards_whenAddGuard_thenNumOfGuardsIncrease_andReturnTrue() {
//        // given
//        gameController.setNumOfGuards(2);
//
//        // when
//        boolean isSuccess = gameController.addGuards(0);
//
//        // then
//        assertTrue(isSuccess);
//        assertEquals(3, gameController.getNumOfGuards());
//    }
//    //endregion
//
//    private static Map<Integer, Player> getPlayers() {
//        Map<Integer, Player> players = new HashMap<>();
//
//        for (int i = 1; i <= NUM_OF_PLAYERS; i++) {
//            Player player = new Player("Player " + i);
//            players.put(i % NUM_OF_PLAYERS, player);
//        }
//        return players;
//    }
}