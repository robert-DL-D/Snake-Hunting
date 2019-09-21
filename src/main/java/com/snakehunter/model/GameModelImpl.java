package com.snakehunter.model;

import com.snakehunter.GameContract;
import com.snakehunter.GameContract.DataChangedListener;
import com.snakehunter.GameStage;
import com.snakehunter.model.exceptions.InvalidParamsException;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class GameModelImpl
        implements GameContract.GameModel {

    private static final int MAX_GUARDS = 3;

    private Square[][] squares = new Square[10][10];

    private DataChangedListener listener;

    private GameStage gameStage;

    private Player<Human> humanPlayer;
    private Player<Snake> snakePlayer;
    private List<Ladder> ladderList;

    private int numOfGuards = 0;
    private int numOfTurns = 0;

    private String errorMessageTest;

    public GameModelImpl() {
        initSquare();
        initPlayers();
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

        if (square.getSnake() != null) {
            listener.onAddSnakeFailed("Cannot place two snakes in the same position.");
            return;
        }

        snakePlayer.addPiece(snake);
        square.addPiece(snake);

        if (listener != null) {
            listener.onSnakeAdded(snake);
        }
    }

    @Override
    public void addLadder(Ladder ladder) {
        String errorMessage = validateLadder(ladder);

        if (errorMessage != null && listener != null) {
            listener.onAddLadderFailed(errorMessage);
            return;
        }

        Square square = getSquare(ladder.getPosition());
        if (square.getLadder() != null) {
            listener.onAddLadderFailed("Cannot place two ladders in the same position.");
            return;
        }

        ladderList.add(ladder);
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
    public void addHumans(int numOfHumans) {
        if (numOfHumans < 2 || numOfHumans > 4) {
            if (listener != null) {
                listener.onNumOfHumansEnteredError();
            }
            return;
        }

        initHumans(numOfHumans);
    }

    @Override
    public Player getCurrentPlayer() {
        if (numOfTurns % 2 == 0) {
            return snakePlayer;
        } else {
            return humanPlayer;
        }
    }

    @Override
    public boolean isGameReady() {
        return !humanPlayer.getPieceList().isEmpty() && !snakePlayer.getPieceList().isEmpty() && !ladderList.isEmpty();
    }

    @Override
    public void nextTurn() {
        numOfTurns++;

        if (listener != null) {
            listener.onNextTurn(getCurrentPlayer());
        }
    }

    @Override
    public void movePlayer(int index, int steps) {
        try {
            humanPlayer.getPiece(index).move(squares, steps);
        } catch (InvalidParamsException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region private method
    private void initSquare() {
        squares = new Square[10][10];

        int x = 0; //col
        int y = 0; //row
        int increment = 1;

        for (int i = 1; i <= 100; i++) {
            squares[x][y] = new Square(i, x, y);
            if (i % 10 == 0) {
                increment = -increment;
                y++;
            } else {
                x += increment;
            }
        }
    }

    private void initPlayers() {
        humanPlayer = new Player<>("Human Player");
        snakePlayer = new Player<>("Snake Player");
    }

    private void initHumans(int numOfHumans) {
        humanPlayer.getPieceList().clear();

        Square startSquare = squares[0][0];
        for (int i = 0; i < numOfHumans; i++) {
            Human human = new Human(1);
            humanPlayer.addPiece(human);
            startSquare.addPiece(human);
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
            //converting from ladder validation
            //checking if this new snake is overlapping other snake's head OR tail
            else if (!snakePlayer.getPieceList().isEmpty()) {
                for (Snake s : snakePlayer.getPieceList()) {
                    if (head >= 81 && s.getPosition() >= 81) {
                        errorMessage = "Only one snake head can be in the range 81-100";
                        break;
                    } else if (head == s.getPosition() || tail == s.getPosition()) {
                        errorMessage = "Snake's top or base is same as another snake's head position";
                        break;
                    } else if (head - 1 == s.getPosition() || head + 1 == s.getPosition()) {
                        errorMessage = "Snake head cannot be next to another snake head";
                    }
                }
            }
            // converting from ladder validation
            //checking if new snake is overlapping ladder's top or base
            else if (ladderList.size() > 0) {
                //System.out.println(ladderList.size());
                for (Ladder ladderInList : ladderList) {
                    if ((head == ladderInList.getConnectedPosition() || head == ladderInList.getPosition())) {
                        errorMessage = "Snakes's head position is same as a ladder's top or base";
                        break;
                    }
                }
            }

        }

        return errorMessage;
    }

    private String validateLadder(Ladder ladder) {
        String errorMessage = null;

        if (ladder == null) {
            errorMessage = "Please enter valid positions.";
        } else {
            int top = ladder.getConnectedPosition();
            int base = ladder.getPosition();
            if (base == 1) {
                errorMessage = "Please enter valid positions.";
            } else if (base > top) {
                errorMessage = "Top's position need to greater than base.";
            } else if (top == 100) {
                errorMessage = "Cannot put a ladder at position 100.";
            } else if (top == base) {
                errorMessage = "Cannot put the top and base in the same position.";
            } else if (top - base > 30) {
                errorMessage = "Ladder's length cannot be greater than 30.";
            }
            // This probably doesn't work
            else if (!snakePlayer.getPieceList().isEmpty()) {
                for (Snake snake : snakePlayer.getPieceList()) {
                    if (top == snake.getPosition() || base == snake.getPosition()) {
                        errorMessage = "Ladder's top or base is same as another snake's head position";
                        break;
                    }
                }
            }
            // This probably doesn't work
            // does now work as intended
            else if (ladderList.size() > 0) {
                //System.out.println(ladderList.size());
                for (Ladder ladderInList : ladderList) {
                    if (ladder != ladderInList && (base == ladderInList.getConnectedPosition())) {
                        errorMessage = "Ladder's base position is same as another ladder's top";
                        break;
                    }

                }
            }
        }
        this.errorMessageTest = errorMessage; // For testing only
        return errorMessage;
    }
    //endregion

    //region getter/setter
    @Override
    public Square getSquare(int squareNo) {
        int x, y;

        y = (int) Math.ceil(squareNo / 10f) - 1;

        if (y % 2 == 0) {   // Odd row
            x = squareNo % 10 - 1;
            if (x == -1) {
                x = 9;
            }
        } else {    // Even row
            x = 10 - squareNo % 10;
            if (x == 10) {
                x = 0;
            }
        }
        return squares[x][y];
    }

    @Override
    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    @Override
    public GameStage getGameStage() {
        return gameStage;
    }

    @Override
    public void setNumOfGuards(int num) {
        numOfGuards = num;
    }

    @Override
    public void setOnDataChangedListener(DataChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public List<Human> getHumanList() {
        return humanPlayer.getPieceList();
    }

    //endregion

    // Testing method
    public String getErrorMessageTest() {
        return errorMessageTest;
    }

}
