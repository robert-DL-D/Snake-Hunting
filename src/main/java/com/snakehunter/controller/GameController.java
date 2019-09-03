package com.snakehunter.controller;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameContract.GameView;
import com.snakehunter.model.Snake;
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
    public void onSnakeBuilt(Snake snake) {
        gameModel.addSnake(snake);
    }

    @Override
    public void onAddLadderClick() {
        gameView.showLadderBuilder();
    }

    @Override
    public void onAddPlayersClick() {
        gameView.showHowManyPlayers();
    }

    @Override
    public void onStartClick() {
        if (gameModel.isGameReady()) {
            gameView.hideSettingPanel();
            gameModel.nextTurn();
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
