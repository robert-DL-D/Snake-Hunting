package com.snakehunter.controller;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameContract.GameView;
import com.snakehunter.GameContract.ViewEventListener;
import com.snakehunter.GameStage;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class GameController
        implements ViewEventListener {

    private GameView gameView;
    private GameModel gameModel;

    public GameController(GameView gameView, GameModel gameModel) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        this.gameModel.setGameStage(GameStage.INITIAL);
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
    public void onLadderBuilt(Ladder ladder) {
        gameModel.addLadder(ladder);
    }

    @Override
    public void onAddLadderClick() {
        gameView.showLadderBuilder();
    }

    @Override
    public void onAddHumansClick() {
        gameView.showHumanBuilder();
    }

    @Override
    public void onStartClick() {
        if (gameModel.isGameReady()) {
            gameView.hideSettingPanel();
            gameModel.setGameStage(GameStage.SECOND);
            gameModel.nextTurn();
        } else {
            gameView.showErrorDialog("Make sure you add snakes, ladders and humans before start the game.");
        }
    }

    @Override
    public void onDiceClick() {
        if (gameModel.getGameStage().equals(GameStage.INITIAL)) {
            return;
        }

        gameView.rollTheDice();
    }

    @Override
    public void onDiceRolled(int num) {
        gameModel.movePlayer(num);
        gameModel.nextTurn();
    }

    @Override
    public void onNumOfHumansEntered(int numOfHumans) {
        gameModel.addHumans(numOfHumans);
    }
    //endregion
}
