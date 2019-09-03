package com.snakehunter;

import com.snakehunter.model.Ladder;
import com.snakehunter.model.Player;
import com.snakehunter.model.Snake;
import com.snakehunter.model.exception.NumberRangeException;

/**
 * @author WeiYi Yu
 * @date 2019-09-03
 */
public class GameContract {
    public interface GameView {
        void rollTheDice();

        void showSnakeBuilder();

        void showHowManyPlayers() throws NumberRangeException;

        void showErrorDialog(String message);

        void hideSettingPanel();
    }

    public interface GameModel {
        void addSnake(Snake snake);

        void addLadder(Ladder ladder);

        void addGuard(int position);

        void addPlayers(int numOfPlayers);

        Player getCurrentPlayer();

        boolean isGameReady();

        void nextTurn();

        void movePlayer(int steps);
    }

}
