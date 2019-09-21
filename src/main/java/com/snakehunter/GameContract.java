package com.snakehunter;

import com.snakehunter.model.Square;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

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

        void hideDicePanel();

        void showDicePanel();

        void hideSettingPanel();

        void setOnViewEventListener(ViewEventListener listener);
    }

    public interface ViewEventListener {
        void onAddSnakeClick();

        void onSnakeBuilt(Snake snake);

        void onAddLadderClick();

        void onLadderBuilt(Ladder ladder);

        void onAddHumansClick();

        void onStartClick();

        void onRandomSnakeClick();

        void onRandomLadderClick();

        void onDiceClick();

        void onDiceRolled(int num);

        void onNumOfHumansEntered(int numOfHumans);


    }

    public interface GameModel {
        void setGameStage(GameStage gameStage);

        GameStage getGameStage();

        void addSnake(Snake snake);

        void addLadder(Ladder ladder);

        void addGuard(int position);

        void addHumans(int numOfHumans);

        Player getCurrentPlayer();

        boolean isGameReady();

        void nextTurn();

        void movePlayer(int steps);

        void setOnDataChangedListener(DataChangedListener listener);

        Square getSquare(int squareNo);

        // Test only
        void setNumOfGuards(int num);
    }

    public interface DataChangedListener {
        void onSnakeAdded(Snake snake);

        void onLadderAdded(Ladder ladder);

        void onAddSnakeFailed(String errorMessage);

        void onAddLadderFailed(String errorMessage);

        void onGuardAdded(int position);

        void onExceedMaxNumOfGuards();

        void onHumansAdded(List<Human> humanList);

        void onNextTurn(Player player);

        void onPlayerMoved(Player player, int destPosition);

        void onNumOfHumansEnteredError();
    }
}
