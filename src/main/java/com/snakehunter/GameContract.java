package com.snakehunter;

import com.snakehunter.model.Square;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

import java.util.Map;

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

        void showHowManyPlayers();

        void showErrorDialog(String message);

        void hideSettingPanel();

        void setOnViewEventListener(ViewEventListener listener);
    }

    public interface ViewEventListener {
        void onAddSnakeClick();

        void onSnakeBuilt(Snake snake);

        void onAddLadderClick();

        void onLadderBuilt(Ladder ladder);

        void onAddPlayersClick();

        void onStartClick();

        void onDiceClick();

        void onDiceRolled(int num);

        void onNumOfPlayersEntered(int numOfPlayers);
    }

    public interface GameModel {
        void setGameStage(GameStage gameStage);

        GameStage getGameStage();

        void addSnake(Snake snake);

        void addLadder(Ladder ladder);

        void addGuard(int position);

        void addPlayers(int numOfPlayers);

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

        void onPlayersAdded(Map<Integer, Player> playerMap);

        void onNextTurn(Player player);

        void onPlayerMoved(Player player, int destPosition);

        void onPlayerClimbLadder(Player player, int destPosition);

        void onPlayerSwallowedBySnake(Player player, int destPosition);

        void onNumOfPlayersEnteredError();
    }
}
