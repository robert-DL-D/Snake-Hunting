package com.snakehunter.controller;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameContract.GameView;
import com.snakehunter.GameContract.ViewEventListener;
import com.snakehunter.GameStage;
import com.snakehunter.model.GameModelImpl;
import com.snakehunter.model.SaveLoadGame;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class GameController
        implements ViewEventListener {

    private GameView gameView;
    private GameModel gameModel;
    private SaveLoadGame saveLoadGame;

    public GameController(GameView gameView, GameModel gameModel, SaveLoadGame saveLoadGame) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        this.saveLoadGame = saveLoadGame;
        this.gameModel.setGameStage(GameStage.INITIAL);
        this.gameView.hideDicePanel();
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
    public void onRandomSnakeClick() {

        while (gameModel.getSnakeList().size() < 5) {

            int snakeHead = ThreadLocalRandom.current().nextInt(3, 99);
            int snakeLength = ThreadLocalRandom.current().nextInt(1, 30);
            int snakeTail = snakeHead - snakeLength;
            snakeTail = snakeTail > 99 ? 99 : snakeTail <= 2 ? 2 : snakeTail;

            Snake s1 = new Snake(snakeHead, snakeTail);
            gameModel.addSnake(s1);
            //Debug dialog
            //gameView.showErrorDialog(Integer.toString(snakeHead) + " " + Integer.toString(snakeLength) + " " +
            // Integer.toString(snakeTail));
        }
    }

    @Override
    public void onRandomLadderClick() {
        while (gameModel.getLadderList().size() < 5) {
            int ladderTop = ThreadLocalRandom.current().nextInt(3, 99);
            int ladderLength = ThreadLocalRandom.current().nextInt(1, 30);
            int ladderBase = ladderTop - ladderLength;
            ladderBase = ladderBase > 99 ? 99 : ladderBase <= 2 ? 2 : ladderBase;

            Ladder l1 = new Ladder(ladderBase, ladderTop);
            gameModel.addLadder(l1);
            //Debug dialog
            //gameView.showErrorDialog(Integer.toString(ladderTop) + " " + Integer.toString(ladderLength) + " " +
            // Integer
            // .toString(ladderBase));
        }
    }

    @Override
    public void onStartClick() throws GameNotReadyException {

        if (gameModel.getSnakeList().size() < 5) {
            throw new GameNotReadyException("Less than 5 snakes placed on board");
        } else if (gameModel.getHumanList().size() < 4) {
            throw new GameNotReadyException("Humans haven't been added to board");
        } else if (gameModel.getLadderList().size() < 5) {
            throw new GameNotReadyException("Less than 5 ladders placed on board");
        }
        if (gameModel.isGameReady()) {
            gameView.hideGameOverPanel();
            gameView.hideSettingPanel();
            gameView.showTurnPanel();
            gameModel.setGameStage(GameStage.SECOND);
            gameModel.nextTurn();
            gameView.updateGuardNo();
            gameView.updateStage(gameModel.getGameStage());
            gameView.updateTurnNo(gameModel.getNumOfTurns());

            System.out.println("DEBUG: The first snake is at " + gameModel.getSnakeList().get(0).getPosition() + " " + gameModel.getSnakeList().get(0).getConnectedPosition());

        } else {

            //gameView.showErrorDialog("Make sure you add snakes, ladders and humans before start the game.");
        }
    }

    @Override
    public void onSaveClick() {
        saveLoadGame.setGameModel((GameModelImpl) gameModel);
        saveLoadGame.saveGame();
    }

    @Override
    public void onLoadClick() {
        saveLoadGame.setGameModel((GameModelImpl) gameModel);
        saveLoadGame.setGameView(gameView);
        saveLoadGame.loadGame();
    }

    @Override
    public void onDiceClick() {
        if (gameModel.getGameStage().equals(GameStage.INITIAL)) {
            return;
        }
        gameView.rollTheDice();
    }

    @Override
    public void onDiceShow() {
        gameView.showDicePanel();
    }

    @Override
    public void onPlaceGuard() {
        gameView.showGuardPlacer();
        gameView.hideDicePanel();
    }


    @Override
    public void onDiceRolled(int player, int num) {
        if (player >= 0) {
            gameModel.movePlayer(player, num);
        }
        gameModel.nextTurn();
        gameView.updateTurnNo(gameModel.getNumOfTurns());
        gameView.hideDicePanel();
    }

    @Override
    public String onSnakeMove(int snake, int steps) {
        String s = gameModel.moveSnake(snake, steps);
        System.out.println(s);
        if (s != null) {
            gameView.showErrorDialog(s);
            return s;
        } else {
            gameModel.nextTurn();
            gameView.updateTurnNo(gameModel.getNumOfTurns());
            return null;
        }

    }

    @Override
    public void onFinalStage() {
        gameModel.setGameStage(GameStage.FINAL);
        gameModel.setNumOfTurns(1);
        gameView.updateStage(GameStage.FINAL);
        gameView.updateTurnNo(gameModel.getNumOfTurns());
    }

    @Override
    public void onNumOfHumansEntered(int numOfHumans) {
        gameModel.addHumans(numOfHumans);
    }
    //endregion
}
