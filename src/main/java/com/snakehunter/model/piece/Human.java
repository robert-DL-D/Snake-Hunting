package com.snakehunter.model.piece;

import com.snakehunter.model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Manolitsas
 * @date 2019-09-08
 */
public class Human
        extends Piece
        implements Movable {

    private static final int PARALYZE_TURNS = 3;

    private int paralyzedTurns = 0;
    private List<Ladder> ladderClimbedList;

    public Human(int position) {
        super(position);

        ladderClimbedList = new ArrayList<>();
    }

    public void addLadderClimbed(Ladder ladder) {
        ladderClimbedList.add(ladder);
    }

    public boolean hasLadderClimbed(Ladder ladder) {
        return ladderClimbedList.contains(ladder);
    }

    public boolean canClimbsLadder() {
        return ladderClimbedList.size() < 3;
    }

    public boolean isParalyzed() {
        if (paralyzedTurns == 0) {
            return false;
        } else {
            paralyzedTurns--;
            return true;
        }
    }

    public void paralyze() {
        paralyzedTurns = PARALYZE_TURNS;
    }

    @Override
    public int move(Square[][] squares, int steps) {
        if (isParalyzed()) {
            //if paralyzed return current position
            return getPosition();
        } else {
            int newPosition = getPosition() + steps;
            if (newPosition > 100) {
                newPosition = 100;
            }

            Square currentSquare = getSquare(squares, getPosition());
            Square destSquare = getSquare(squares, newPosition);
            currentSquare.removePiece(this);

            // Check if the destSquare has snakes or ladders
            Snake snake = destSquare.getSnake();
            Ladder ladder = destSquare.getLadder();
            if (snake != null) {
                newPosition = snake.getConnectedPosition();
            } else if (ladder != null) {
                newPosition = ladder.getConnectedPosition();
            }

            destSquare = getSquare(squares, newPosition);
            destSquare.addPiece(this);
            setPosition(newPosition);
            setPosition(newPosition);
            return newPosition;
        }
    }
}
