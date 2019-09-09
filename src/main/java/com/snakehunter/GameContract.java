package com.snakehunter;

import com.snakehunter.model.GameModelImpl.GameModelListener;
import com.snakehunter.model.Square;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;
import com.snakehunter.view.GameViewImpl.GameViewListener;

/**
 * @author WeiYi Yu
 * @date 2019-09-03
 */
public class GameContract {
    public interface GameView
            extends GameModelListener {
        void rollTheDice();

        void showSnakeBuilder();

        void showLadderBuilder();

        void showHowManyPlayers();

        void showErrorDialog(String message);

        void hideSettingPanel();

        void setListener(GameViewListener listener);
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

        void setListener(GameModelListener listener);

        Square getSquare(int squareNo);

        void setNumOfGuards(int num);
    }

}
