package com.snakehunter;

import com.snakehunter.controller.GameNotReadyException;
import com.snakehunter.model.Square;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;
import com.snakehunter.view.TurnPanel;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-09-03
 */
public class GameContract {
    public interface GameView
            extends DataChangedListener {
        void rollTheDice();

        void showSnakeBuilder();

        void showLadderBuilder();

        void showHumanBuilder();

        void showErrorDialog(String message);

        void showInfoDialog(String message);

        void hideDicePanel();

        void showDicePanel();

        void showTurnPanel();

        void showGuardPlacer();

        void hideTurnPanel();

        void updateTurnNo(int turnNo);

        void updateStage(GameStage s);

        void updateGuardNo();

        void updateParalyzedTurn();

        void hideSettingPanel();

        void showSettingPanel();

        void hideGameOverPanel();

        void showGameOverPanel(Player p);

        TurnPanel getTurnPanel();

        void setOnViewEventListener(ViewEventListener listener);

    }

    public interface ViewEventListener {
        void onAddSnakeClick();

        void onSnakeBuilt(Snake snake);

        void onAddLadderClick();

        void onLadderBuilt(Ladder ladder);

        void onAddHumansClick();

        void onStartClick() throws GameNotReadyException;

        void onSaveClick();

        void onLoadClick();

        void onRandomSnakeClick();

        void onRandomLadderClick();

        void onDiceClick();

        void onPlaceGuard();

        void onDiceRolled(int player, int num);

        void onSnakeMove(int snake, int steps);

        void onNumOfHumansEntered(int numOfHumans);

        void onKnightClick(int humanPiece);
    }

    public interface GameModel {
        void setGameStage(GameStage gameStage);

        GameStage getGameStage();

        Square[][] getSquares();

        void addSnake(Snake snake);

        void addLadder(Ladder ladder);

        void addGuard(int position);

        void addHumans(int numOfHumans);

        Player getCurrentPlayer();

        boolean isGameReady();

        void nextTurn();

        int movePlayer(int index, int steps);

        void movePlayer(int index, Square destSquare);

        String moveSnake(int index, int steps);

        void setOnDataChangedListener(DataChangedListener listener);

        Square getSquare(int squareNo);

        int getNumOfTurns();

        int getRemainingGuards();

        void resetGameModel();

        // Test only
        void setNumOfGuards(int num);

        void setNumOfTurns(int num);

        List<Human> getHumanList();

        List<Snake> getSnakeList();

        List<Ladder> getLadderList();
    }

    public interface DataChangedListener {
        void onSnakeAdded(Snake snake);

        void onLadderAdded(Ladder ladder);

        void onAddSnakeFailed(String errorMessage);

        void onAddLadderFailed(String errorMessage);

        void onGuardAdded(int position);

        void onHumanMoved(String message);

        void onExceedMaxNumOfGuards();

        void onExceedMaxPosition();

        void onLadderClimbedThresholdException();

        void onNextTurn(Player player);

        void onGameOver(Player winner);

        void onNumOfHumansEnteredError();
    }
}
