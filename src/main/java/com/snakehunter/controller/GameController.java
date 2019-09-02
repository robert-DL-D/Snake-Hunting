package com.snakehunter.controller;

import com.snakehunter.GameStage;
import com.snakehunter.model.GameModel;
import com.snakehunter.model.exception.NumberRangeException;
import com.snakehunter.view.GameView;
import com.snakehunter.view.GameView.GameViewListener;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class GameController
        implements GameViewListener {


    private boolean isGameOver = false;
    private GameView gameView;
    private GameModel gameModel;

    public GameController(GameView gameView, GameModel gameModel) {
        this.gameView = gameView;
        this.gameModel = gameModel;

        gameModel.setGameStage(GameStage.INITIAL);
    }

    void gameOver() {
        // TODO: print the info of game
    }

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


    //region interaction
    @Override
    public void onStartClick() {
        try {
            gameView.showHowManyPlayers();
        } catch (NumberRangeException e) {
            gameView.showErrorDialog("Please enter a number between 2 ~ 4.");
        }
    }

    @Override
    public void onDiceClick() {
        gameView.rollTheDice();
    }

    @Override
    public void onNumOfPlayersEntered(int numOfPlayers) {
        gameModel.setPlayers(numOfPlayers);
    }
    //endregion

    //region getter/setter
    public boolean isGameOver() {
        // TODO: check every conditions which can finish the game
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
    //endregion
}
