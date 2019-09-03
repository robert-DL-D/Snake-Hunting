package com.snakehunter.model;

import com.snakehunter.GameContract;
import com.snakehunter.GameStage;
import com.snakehunter.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class GameModel
        implements GameContract.GameModel {

    private static final int MAX_GUARDS = 3;

    private Square[][] squares = new Square[10][10];

    private GameModelListener listener;

    private GameStage gameStage;

    private Map<Integer, Player> playerMap;
    private Player currentPlayer;

    private int numOfGuards = 0;
    private int numOfTurns = 0;

    public GameModel() {
        initSquare();
    }

    //region interaction
    @Override
    public void addSnake(Snake snake) {
        String errorMessage = validateSnakePosition(snake.getHead(), snake.getTail());

        if (errorMessage != null && listener != null) {
            listener.onAddFailed(errorMessage);
            return;
        }

        Square square = getSquare(snake.getHead());
        square.addPlaceable(snake);

        if (listener != null) {
            listener.onSnakeAdded(snake);
        }
    }

    @Override
    public void addLadder(Ladder ladder) {
        // TODO: validate ladder position
        Square square = getSquare(ladder.getBottom());
        square.addPlaceable(ladder);

        if (listener != null) {
            listener.onLadderAdded(ladder);
        }
    }

    @Override
    public void addGuard(int position) {

    }

    @Override
    public void addPlayers(int numOfPlayers) {
        playerMap = initPlayers(numOfPlayers);

        if (listener != null) {
            listener.onPlayersAdded(numOfPlayers);
        }
    }
    //endregion

    //region private method
    private void initSquare() {
        squares = new Square[10][10];

        int x = 0;
        int y = 0;
        int increment = 1;

        for (int i = 1; i <= 100; i++) {
            squares[x][y] = new Square(i);
            if (i % 10 == 0) {
                increment = -increment;
                y++;
            } else {
                x += increment;
            }
        }
    }

    private String validateSnakePosition(int head, int tail) {
        String errorMessage = null;
        if (head < 2 || tail < 1) {
            errorMessage = "Please enter valid positions.";
        } else if (tail > head) {
            errorMessage = "Head's position need to greater than tail's.";
        } else if (head == 100) {
            errorMessage = "Cannot put a snake at position 100.";
        } else if (head == tail) {
            errorMessage = "Cannot put the head and tail in the same position.";
        } else if (head - tail > 30) {
            errorMessage = "Snake's length cannot greater than 30.";
        }
        return errorMessage;
    }

    private Square getSquare(int position) {
        int x, y;

        y = (int) Math.ceil(position / 10f) - 1;

        if (y % 2 == 0) {   // Odd row
            x = position % 10 - 1;
            if (x == -1) {
                x = 9;
            }
        } else {    // Oven row
            x = 10 - position % 10;
            if (x == 10) {
                x = 0;
            }
        }
        return squares[x][y];
    }

    private Map<Integer, Player> initPlayers(int numOfPlayers) {
        Map<Integer, Player> players = new HashMap<>();

        for (int i = 1; i <= numOfPlayers; i++) {
            Player player = new Player("Player " + i);
            players.put(i % numOfPlayers, player);
        }
        return players;
    }
    //endregion

    //region getter/setter
    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    private Player getPlayer(int numOfTurns) {
        int index = numOfTurns % playerMap.size();
        return playerMap.get(index);
    }

    public void setListener(GameModelListener listener) {
        this.listener = listener;
    }
    //endregion

    public interface GameModelListener {
        void onSnakeAdded(Snake snake);

        void onLadderAdded(Ladder ladder);

        void onAddFailed(String errorMessage);

        void onPlayersAdded(int numOfPlayers);
    }
}
