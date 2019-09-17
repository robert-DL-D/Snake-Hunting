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

    public boolean isParalyzed() {
        if (paralyzedTurns == 0) {
            return false;
        } else {
            paralyzedTurns--;
            return true;
        }
    }

    @Override
    public String move(Square[][] squares, int steps) {
        StringBuilder stringBuilder = new StringBuilder();

        // First: Move by the dice number
        int newPosition = getPosition() + steps;
        Square currentSquare = getSquare(squares, getPosition());
        currentSquare.removePiece(this);
        stringBuilder.append(String.format("Move to new position: %1s", newPosition));

        // Second: Check if the destSquare has snakes or ladders
        Square destSquare = getSquare(squares, newPosition);
        Snake snake = destSquare.getSnake();
        Ladder ladder = destSquare.getLadder();
        if (snake != null) {    // Square has a snake
            newPosition = snake.getConnectedPosition();
            destSquare = getSquare(squares, newPosition);
            stringBuilder.append(String.format("%nSwallowed by a snake and back to: %1s", newPosition));

            // Make human paralyze as it got swallowed by a snake
            paralyze();
        } else if (ladder != null) {    // Square has a ladder
            if (!canClimbsLadder()) {
                stringBuilder.append("\nExceed the maximum number of ladders you can climb with");
            } else if (hasLadderClimbed(ladder)) {
                stringBuilder.append("\nAlready climbed the same ladder before");
            } else {
                newPosition = ladder.getConnectedPosition();
                destSquare = getSquare(squares, newPosition);
                addLadderClimbed(ladder);
                stringBuilder.append(String.format("%nClimbed a ladder to: %1s", newPosition));
            }
        }

        destSquare.addPiece(this);
        setPosition(newPosition);
        return stringBuilder.toString();
    }

    //region private methods
    private void paralyze() {
        paralyzedTurns = PARALYZE_TURNS;
    }

    private boolean canClimbsLadder() {
        return ladderClimbedList.size() < 3;
    }

    private boolean hasLadderClimbed(Ladder ladder) {
        return ladderClimbedList.contains(ladder);
    }

    private void addLadderClimbed(Ladder ladder) {
        ladderClimbedList.add(ladder);
    }
    //endregion


    // Method for testing
    List<Ladder> getLadderClimbedList() {
        return ladderClimbedList;
    }
}
