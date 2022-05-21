package com.snakehunter.controller;

import com.snakehunter.GameStage;
import com.snakehunter.file.LoadGame;
import com.snakehunter.file.SaveGame;
import com.snakehunter.model.GameModel;
import com.snakehunter.model.Square;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Piece;
import com.snakehunter.model.piece.Snake;
import com.snakehunter.view.GameView;
import com.snakehunter.view.TurnPanel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class GameController {

    private final GameView gameView;
    private final GameModel gameModel;
    private final SaveGame saveGame;
    private final LoadGame loadGame;

    GameController(GameView gameView, GameModel gameModel, SaveGame saveGame, LoadGame loadGame) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        this.saveGame = saveGame;
        this.loadGame = loadGame;
        this.gameModel.setGameStage(GameStage.INITIAL);
    }

    //region interaction

    public void onSnakeBuilt(Snake snake) {
        gameModel.addSnake(snake);
    }

    public void onLadderBuilt(Ladder ladder) {
        gameModel.addLadder(ladder);
    }

    /*public void onAddHumansClick() {
        gameView.showHumanBuilder();
    }*/

    public void onRandomSnakeClick() {
        while (gameModel.getSnakeList().size() < 5) {

            int snakeHead = ThreadLocalRandom.current().nextInt(2, 101);
            int snakeLength = ThreadLocalRandom.current().nextInt(1, 31);
            int snakeTail = snakeHead - snakeLength;
            snakeTail = snakeTail > 100 ? 99 : snakeTail < 2 ? 2 : snakeTail;

            gameModel.addSnake(new Snake(snakeHead, snakeTail));
        }
    }

    public void onRandomLadderClick() {
        while (gameModel.getLadderList().size() < 5) {
            int ladderTop = ThreadLocalRandom.current().nextInt(3, 99);
            int ladderLength = ThreadLocalRandom.current().nextInt(1, 30);
            int ladderBase = ladderTop - ladderLength;
            ladderBase = ladderBase > 101 ? 100 : ladderBase < 2 ? 2 : ladderBase;

            gameModel.addLadder(new Ladder(ladderBase, ladderTop));
        }
    }

    public void onStartClick() throws GameNotReadyException {
        if (gameModel.getSnakeList().size() < 5) {
            throw new GameNotReadyException("Less than 5 snakes placed on board");
        } else if (gameModel.getHumanList().size() < 4) {
            throw new GameNotReadyException("Humans haven't been added to board");
        } else if (gameModel.getLadderList().size() < 5) {
            throw new GameNotReadyException("Less than 5 ladders placed on board");
        }

        gameView.hideGameOverPanel();
        gameView.hideSettingPanel();
        gameView.showTurnPanel();
        gameModel.setGameStage(GameStage.SECOND);
        gameModel.nextTurn();
        gameView.updateStage(gameModel.getGameStage());
    }

    public void onSaveClick() {
        saveGame.setGameModel(gameModel);
        saveGame.saveGame();
    }

    public void onLoadClick() {
        loadGame.setGameModel(gameModel);
        loadGame.setGameView(gameView);
        loadGame.setGameController(this);
        loadGame.loadGame();
    }

    public void onDiceClick() {
        gameView.rollTheDice();
    }

    public void onSquareClick(int square) {
        System.out.println(square);
    }

    public boolean onDiceRolled(int player, int num) {

        if (gameModel.getHumanList().get(player).getParalyzeTurns() != 0) {
            gameView.showInfoDialog("The selected piece is paralyzed, select another human piece.");
            return false;
        }

        TurnPanel turnPanel = gameView.getTurnPanel();

        if (gameModel.movePlayer(player, num) == 100) {
            gameModel.setGameStage(GameStage.FINAL);
            gameView.updateStage(gameModel.getGameStage());

            gameModel.getHumanList().get(player).setUnkillable(true);
            gameModel.resetGameModel();
            gameModel.getLadderList().clear();

            for (Square[] squares : gameModel.getSquares()) {
                for (Square square : squares) {
                    if (!square.getPieceList().isEmpty()) {
                        Collection<Piece> tempLadderList = new LinkedList<>();

                        for (Piece piece : square.getPieceList()) {
                            if (piece instanceof Ladder) {
                                tempLadderList.add(piece);
                            }
                        }

                        for (Piece piece1 : tempLadderList) {
                            square.removePiece(piece1);
                        }
                    }
                }
            }

            turnPanel.hidePieceButtons();
            turnPanel.hideDice();
        } else if (num == 6) {
            turnPanel.updateParalyzedTurn();
            gameView.showInfoDialog("Human rolled a 6, they can roll again!");
            turnPanel.disablePieceButtons();
            return true;
        }

        gameModel.nextTurn();

        return true;
    }

    public void onSnakeMove(int snake, int steps) {
        String errorMsg = gameModel.moveSnake(snake, steps);
        if (errorMsg != null) {
            gameView.showErrorDialog(errorMsg);
            return;
        }

        gameModel.nextTurn();
    }

    public void onMoveKnight(int humanPiece, int squareNo) {
        gameModel.movePlayer(humanPiece, gameModel.getSquare(squareNo));
    }

   /* public void onNumOfHumansEntered(int numOfHumans) {
        gameModel.addHumans(numOfHumans);
    }*/
    //endregion
}
