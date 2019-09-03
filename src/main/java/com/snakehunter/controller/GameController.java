package com.snakehunter.controller;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameContract.GameView;
import com.snakehunter.model.Snake;
import com.snakehunter.model.exception.NumberRangeException;
import com.snakehunter.view.GameViewImpl.GameViewListener;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class GameController
        implements GameViewListener {

    private GameView gameView;
    private GameModel gameModel;

    public GameController(GameView gameView, GameModel gameModel) {
        this.gameView = gameView;
        this.gameModel = gameModel;

//        gameModel.setGameStage(GameStage.INITIAL);
    }

    //region interaction
    @Override
    public void onAddSnakeClick() {
        gameView.showSnakeBuilder();
    }

    @Override
    public void onSnakeBuilt(int head, int tail) {
        gameModel.addSnake(new Snake(head, tail));
    }

    @Override
    public void onAddLadderClick() {

    }

    @Override
    public void onAddPlayersClick() {
        try {
            gameView.showHowManyPlayers();
        } catch (NumberRangeException e) {
            gameView.showErrorDialog("Please enter a number between 2 ~ 4.");
        }
    }

    @Override
    public void onStartClick() {
        if (true) {
            gameModel.nextTurn();
            gameView.hideSettingPanel();
        } else {
            gameView.showErrorDialog("Make sure you add snakes, ladders and players before start the game.");
        }
    }

    @Override
    public void onDiceClick() {
        gameView.rollTheDice();
    }

    @Override
    public void onDiceRolled(int num) {
        gameModel.movePlayer(num);
        gameModel.nextTurn();
    }

    @Override
    public void onNumOfPlayersEntered(int numOfPlayers) {
        gameModel.addPlayers(numOfPlayers);
    }
    //endregion

//    void nextTurn() {
//        numOfTurns++;
//        currentPlayer = getPlayer(numOfTurns);
//    }
//
//    boolean addGuards(int position) {
//        if (numOfGuards == MAX_GUARDS) {
//            return false;
//        }
//
//        // TODO:
//        //  1. check if position available
//        //  2. put the guard into the corresponding position
//        numOfGuards++;
//        return true;
//    }
}
