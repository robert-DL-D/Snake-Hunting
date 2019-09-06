package com.snakehunter.model;

import com.snakehunter.GameContract;
import com.snakehunter.GameStage;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class GameModelImpl
        implements GameContract.GameModel {

    private static final int MAX_GUARDS = 3;

    private Square[][] squares = new Square[10][10];

    private GameModelListener listener;

    private GameStage gameStage;

    private Map<Integer, Player> playerMap;
    private List<Snake> snakeList;
    private List<Ladder> ladderList;

    private int numOfGuards = 0;
    private int numOfTurns = 0;

    public GameModelImpl() {
        initSquare();
        playerMap = new HashMap<>();
        snakeList = new ArrayList<>();
        ladderList = new ArrayList<>();
    }

    //region interaction
    @Override
    public void addSnake(Snake snake) {
        String errorMessage = validateSnake(snake);

        if (errorMessage != null && listener != null) {
            listener.onAddSnakeFailed(errorMessage);
            return;
        }

        Square square = getSquare(snake.getPosition());
        square.addPiece(snake);

        if (listener != null) {
            listener.onSnakeAdded(snake);
        }
    }

    @Override
    public void addLadder(Ladder ladder) {
        // TODO: validate ladder position
        Square square = getSquare(ladder.getPosition());
        square.addPiece(ladder);

        if (listener != null) {
            listener.onLadderAdded(ladder);
        }
    }

    @Override
    public void addGuard(int position) {
        if (numOfGuards == MAX_GUARDS) {
            if (listener != null) {
                listener.onExceedMaxNumOfGuards();
            }
            return;
        }

        Square square = getSquare(position);
        square.setGuarded(true);

        if (listener != null) {
            listener.onGuardAdded(position);
        }
    }

    @Override
    public void addPlayers(int numOfPlayers) {
        if (numOfPlayers < 2 || numOfPlayers > 4) {
            if (listener != null) {
                listener.onNumOfPlayersEnteredError();
            }
            return;
        }

        initPlayers(numOfPlayers);

        if (listener != null) {
            listener.onPlayersAdded(numOfPlayers);
        }
    }

    @Override
    public Player getCurrentPlayer() {
        int index = numOfTurns % playerMap.size();
        return playerMap.get(index);
    }

    @Override
    public boolean isGameReady() {
        return playerMap.size() != 0 && snakeList.size() != 0 && ladderList.size() != 0;
    }

    @Override
    public void nextTurn() {
        numOfTurns++;

        if (listener != null) {
            listener.onNextTurn(getCurrentPlayer());
        }
    }

    @Override
    public void movePlayer(int steps) {
        Player currentPlayer = getCurrentPlayer();
        int currentPosition = currentPlayer.getPosition();
        int destPosition = currentPosition + steps;
        Square currentSquare = getSquare(currentPlayer.getPosition());
        Square destSquare = getSquare(destPosition);
        currentSquare.removePiece(currentPlayer);
        currentPlayer.setPosition(destPosition);

        if (listener != null) {
            listener.onPlayerMoved(currentPlayer, destPosition);
        }

        // Check if the destSquare has snakes or ladders
        Snake snake = destSquare.getSnake();
        Ladder ladder = destSquare.getLadder();
        if (snake != null && listener != null) {
            destPosition = snake.getConnectedPosition();
            listener.onPlayerSwallowedBySnake(currentPlayer, destPosition);
        } else if (ladder != null && listener != null) {
            destPosition = ladder.getConnectedPosition();
            listener.onPlayerClimbLadder(currentPlayer, destPosition);
        }

        destSquare = getSquare(destPosition);
        destSquare.addPiece(currentPlayer);
        currentPlayer.setPosition(destPosition);
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

    @Override
    public Square getSquare(int squareNo) {
        int x, y;

        y = (int) Math.ceil(squareNo / 10f) - 1;

        if (y % 2 == 0) {   // Odd row
            x = squareNo % 10 - 1;
            if (x == -1) {
                x = 9;
            }
        } else {    // Oven row
            x = 10 - squareNo % 10;
            if (x == 10) {
                x = 0;
            }
        }
        return squares[x][y];
    }

    private void initPlayers(int numOfPlayers) {
        playerMap.clear();
        Square startPoint = squares[0][0];

        for (int i = 1; i <= numOfPlayers; i++) {
            Player player = new Player(1, "Player " + i);
            player.setPosition(startPoint.getSquareNo());
            playerMap.put(i % numOfPlayers, player);

            startPoint.addPiece(player);
        }
    }

    private String validateSnake(Snake snake) {
        String errorMessage = null;

        if (snake == null) {
            errorMessage = "Please enter valid positions.";
        } else {
            int head = snake.getPosition();
            int tail = snake.getConnectedPosition();
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
        }

        return errorMessage;
    }
    //endregion

    //region getter/setter
    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    @Override
    public void setNumOfGuards(int num) {
        numOfGuards = num;
    }

    @Override
    public void setListener(GameModelListener listener) {
        this.listener = listener;
    }
    //endregion

    public interface GameModelListener {
        void onSnakeAdded(Snake snake);

        void onLadderAdded(Ladder ladder);

        void onAddSnakeFailed(String errorMessage);

        void onGuardAdded(int position);

        void onExceedMaxNumOfGuards();

        void onPlayersAdded(int numOfPlayers);

        void onNextTurn(Player player);

        void onPlayerMoved(Player player, int destPosition);

        void onPlayerClimbLadder(Player player, int destPosition);

        void onPlayerSwallowedBySnake(Player player, int destPosition);

        void onNumOfPlayersEnteredError();
    }
}
