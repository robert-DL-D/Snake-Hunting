package com.snakehunter.controller;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameContract.GameView;
import com.snakehunter.model.piece.Snake;

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
}