package com.snakehunter.model;

import com.snakehunter.GameStage;
import com.snakehunter.model.exceptions.LadderClimbedThresholdException;
import com.snakehunter.model.exceptions.MaxPositionExceedException;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;
import com.snakehunter.view.GameView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class GameModel {

    private static final int MAX_GUARDS = 3;
    private static final int NUM_OF_HUMANS = 4;
    private static final int HUMAN_INITAL_PARALYZED_TURN = 3;

    private Square[][] squares = new Square[10][10];

    private GameView gameView;
    private GameStage gameStage;

    private Player<Human> humanPlayer;
    private Player<Snake> snakePlayer;
    private final List<Ladder> ladderList = new ArrayList<>();

    private int numOfGuards;
    private int numOfTurns;

    private String errorMessageTest;

    public GameModel() {
        initSquare();
        initPlayers();
        initHumans();
    }

    //region interaction

    public void addSnake(Snake snake) {
        String errorMessage = validateSnake(snake);
        if (errorMessage != null && gameView != null) {
            gameView.onAddSnakeFailed(errorMessage);
            return;
        }

        Square square = getSquare(snake.getPosition());

        if (square.getSnake() != null) {
            gameView.onAddSnakeFailed("Cannot place two snakes in the same position.");
            return;
        }

        snakePlayer.addPiece(snake);
        square.addPiece(snake);

        Square tailSquare = getSquare(snake.getConnectedPosition());
        tailSquare.addPiece(snake);
    }

    public void addLadder(Ladder ladder) {
        String errorMessage = validateLadder(ladder);

        if (errorMessage != null && gameView != null) {
            gameView.onAddLadderFailed(errorMessage);
            return;
        }

        Square square = getSquare(ladder.getPosition());
        if (square.getLadder() != null) {
            gameView.onAddLadderFailed("Cannot place two ladders in the same position.");
            return;
        }

        ladderList.add(ladder);
        square.addPiece(ladder);
    }

    public void addGuard(int position) {
        if (numOfGuards == MAX_GUARDS) {
            gameView.onExceedMaxNumOfGuards();
            return;
        }

        numOfGuards++;
        getSquare(position).setGuarded(true);

        nextTurn();
    }

    /*
    public void addHumans(int numOfHumans) {
        if (numOfHumans < 2 || numOfHumans > 4) {
            if (listener != null) {
                listener.onNumOfHumansEnteredError();
            }
            return;
        }

        initHumans(numOfHumans);
    }*/

    public Player getCurrentPlayer() {
        if (numOfTurns % 2 == 0) {
            return snakePlayer;
        } else {
            return humanPlayer;
        }
    }

    public void nextTurn() {
        if (gameStage == GameStage.SECOND) {
            Player player = getCurrentPlayer();
            if (player == humanPlayer) {
                for (Human h : getHumanList()) {
                    h.isParalyzed(numOfTurns);
                }
            }
        }

        numOfTurns++;

        for (int i = 0; i < snakePlayer.getPieceList().size(); i++) {
            if (snakePlayer.getPieceList().get(i).isSnakeDead()) {
                snakePlayer.removePiece(snakePlayer.getPieceList().get(i));

            }
        }

        if (numOfTurns >= gameStage.getMaxTurns()) {
            gameView.onGameOver(snakePlayer);
            return;
        }

        if (gameStage == GameStage.FINAL) {

            for (Human h : humanPlayer.getPieceList()) {
                if (h.isDead()) {
                    gameView.onGameOver(snakePlayer);
                }
            }

            if (numOfTurns >= gameStage.getMaxTurns()) {
                gameView.onGameOver(humanPlayer);
            }

            boolean allSnakesDead = true;
            for (Snake snake : snakePlayer.getPieceList()) {
                if (!snake.isSnakeDead()) {
                    allSnakesDead = false;
                    break;
                }
            }
            if (allSnakesDead) {
                gameView.onGameOver(humanPlayer);
            } else {
                gameView.onNextTurn(getCurrentPlayer());
            }
        } else {
            gameView.onNextTurn(getCurrentPlayer());
        }
    }

    /**
     * Human movement for Second stage
     */

    public int movePlayer(int index, int steps) {
        Human piece = humanPlayer.getPiece(index);

        try {
            String movementMsg = piece.move(squares, steps, index);
            if (piece.getParalyzeTurns() == HUMAN_INITAL_PARALYZED_TURN) {
                piece.setParalyzedAtTurn(numOfTurns);
            }
            gameView.onHumanMoved(movementMsg);
        } catch (MaxPositionExceedException e) {
            gameView.onExceedMaxPosition();
        } catch (LadderClimbedThresholdException e) {
            gameView.onLadderClimbedThresholdException();
        }
        return piece.getPosition();
    }

    /**
     * Human movement for Final stage
     */

    public void movePlayer(int index, Square destSquare) {
        Square newSquare = humanPlayer.getPiece(index).moveKnight(squares, destSquare);
        if (newSquare != null) {
            nextTurn();
        }
    }

    public String moveSnake(int index, int steps) {
        Snake piece = snakePlayer.getPiece(index);

        try {
            String temp = piece.move(squares, steps, index);

            gameView.onHumanMoved("Snake " + (index + 1) + " moved to position " + getSnakeList().get(index).getPosition());

            if (gameStage == GameStage.SECOND) {
                String movementMsg = piece.eatHuman(this, piece.getSquare(squares, piece.getPosition()));
                if (movementMsg != null) {
                    gameView.onHumanMoved(movementMsg);
                }

            } else if (gameStage == GameStage.FINAL) {
                piece.killHuman(piece.getSquare(squares, piece.getPosition()));
            }
            return temp;
        } catch (NullPointerException nullEx) {
            return "Please select a snake";
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
        humanPlayer = new Player<>("");
        snakePlayer = new Player<>("");
    }

    private void initHumans() {
        humanPlayer.getPieceList().clear();

        for (int i = 0; i < NUM_OF_HUMANS; i++) {
            Human human = new Human(1);
            humanPlayer.addPiece(human);
            squares[0][0].addPiece(human);
        }
    }

    /*void initHumans(int numOfHumans) {
        humanPlayer.getPieceList().clear();

        Square startSquare = squares[0][0];
        for (int i = 0; i < numOfHumans; i++) {
            Human human = new Human(1);
            humanPlayer.addPiece(human);
            startSquare.addPiece(human);
        }
    }*/

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
                    if (snake != s && head >= 81 && s.getPosition() >= 81) {
                        errorMessage = "Only one snake head can be in the range 81-100";
                        break;
                    } else if (snake != s && (head == s.getPosition() || tail == s.getPosition())) {
                        errorMessage = "Snake's head or tail position is same as another snake's head position";
                        break;
                    } else if (head - 1 == s.getPosition() || head + 1 == s.getPosition()) {
                        errorMessage = "Snake head cannot be next to another snake head position";
                    } else if (snake != s && head == s.getConnectedPosition()) {
                        errorMessage = "Snake's head position is same as another snake's tail position";
                    }
                }
            }
            // converting from ladder validation
            //checking if new snake is overlapping ladder's top or base
            else if (!ladderList.isEmpty()) {
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
                errorMessage = "Ladder's base position cannot be at 1.";
            } else if (base > top) {
                errorMessage = "Top's position need to greater than base.";
            } else if (top == 100) {
                errorMessage = "Cannot put a ladder at position 100.";
            } else if (top == base) {
                errorMessage = "Cannot put the top and base in the same position.";
            } else if (top - base > 30) {
                errorMessage = "Ladder's length cannot be greater than 30.";
            } else if (!snakePlayer.getPieceList().isEmpty()) {
                for (Snake snake : snakePlayer.getPieceList()) {
                    if (top == snake.getPosition() || base == snake.getPosition()) {
                        errorMessage = "Ladder's top or base position is same as another snake's head position";
                        break;
                    }
                }
            } else if (!ladderList.isEmpty()) {

                for (Ladder ladderInList : ladderList) {
                    if (ladder != ladderInList && base == ladderInList.getConnectedPosition()) {
                        errorMessage = "Ladder's base position is same as another ladder's top position";
                        break;
                    }

                    if (ladder != ladderInList && top == ladderInList.getPosition()) {
                        errorMessage = "Ladder's top position is same as another ladder's base position";
                        break;
                    }

                }
            }
        }
        errorMessageTest = errorMessage; // For testing only
        return errorMessage;
    }
    //endregion

    //region getter/setter

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

    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public void setNumOfGuards(int num) {
        numOfGuards = num;
    }

    public void setOnDataChangedListener(GameView listener) {
        gameView = listener;
    }

    public List<Human> getHumanList() {
        return humanPlayer.getPieceList();
    }

    public Square[][] getSquares() {
        return squares;
    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    public int getRemainingGuards() {
        return MAX_GUARDS - numOfGuards;
    }

    public void resetGameModel() {
        numOfTurns = 0;

        for (Human human : getHumanList()) {
            human.setParalyzedTurns(0);
        }

        for (Square[] squareArray : squares) {
            for (Square square : squareArray) {
                if (square.isGuarded()) {
                    square.setGuarded(false);
                }
            }
        }
    }

    public List<Snake> getSnakeList() {
        return snakePlayer.getPieceList();
    }

    public List<Ladder> getLadderList() {
        return ladderList;
    }

    public void setNumOfTurns(int turns) {
        numOfTurns = turns;
    }

    public Player<Human> getHumanPlayer() {
        return humanPlayer;
    }

    public Player<Snake> getSnakePlayer() {
        return snakePlayer;
    }

    //endregion

    // Testing method
    public String getErrorMessageTest() {
        return errorMessageTest;
    }

}
