package com.snakehunter.model.piece;

import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.LadderClimbedException;
import com.snakehunter.model.exceptions.MaxClimbNumExceedException;
import com.snakehunter.model.exceptions.MaxPositionExceedException;

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

    public int getParalyzeTurns() {
        return paralyzedTurns;
    }

    /**
     * 1. Check params is valid
     * 2. Check new position is not exceed 100
     * 3. Move by the dice number
     * 4. Move to Snake'stail / Ladder'stop if the piece lands on a Snake / Ladder
     * 5. Paralyze the piece if it swallowed by a Snake
     *
     * @param squares
     *         board array
     * @param steps
     *         number rolled from dice
     *
     * @return Message about this movement
     */
    @Override
    public String move(Square[][] squares, int steps) throws MaxPositionExceedException {
        StringBuilder stringBuilder = new StringBuilder();

        // Remove piece from current square
        Square currentSquare = getSquare(squares, getPosition());
        currentSquare.removePiece(this);

        // Calculate new position
        int newPosition = getPosition() + steps;
        if (newPosition > 100) {
            throw new MaxPositionExceedException();
        }

        stringBuilder.append(String.format("Move to position %1s", newPosition));

        // Check if the destSquare has snakes or ladders
        Square destSquare = getSquare(squares, newPosition);
        ConnectorPiece connectorPiece = destSquare.getSnake() == null ? destSquare.getLadder() : destSquare.getSnake();

        if (connectorPiece == null) {
            setPosition(newPosition);
            destSquare.addPiece(this);
            return stringBuilder.toString();
        }

        String message = "";
        if (connectorPiece instanceof Snake) {
            paralyze();
            message = String.format(" then swallowed by a snake and back to position %1s",
                                    connectorPiece.getConnectedPosition());
        } else { // connector piece is a Ladder
            try {
                addLadderClimbed((Ladder) connectorPiece);
                message = String.format(" then climb a ladder to position %1s", connectorPiece.getConnectedPosition());
            } catch (LadderClimbedException | MaxClimbNumExceedException e) {
                destSquare.addPiece(this);
                setPosition(newPosition);

                message = e.getMessage();
                stringBuilder.append("\n").append(message);
                return stringBuilder.toString();
            }
        }

        newPosition = connectorPiece.getConnectedPosition();
        destSquare = getSquare(squares, newPosition);
        destSquare.addPiece(this);
        setPosition(newPosition);

        return stringBuilder.append(message).toString();
    }

    //region private methods
    void paralyze() {
        paralyzedTurns = PARALYZE_TURNS;
    }

    void addLadderClimbed(Ladder ladder) throws LadderClimbedException, MaxClimbNumExceedException {
        if (ladderClimbedList.contains(ladder)) {
            throw new LadderClimbedException();
        } else if (ladderClimbedList.size() == 3) {
            throw new MaxClimbNumExceedException();
        }

        ladderClimbedList.add(ladder);
    }
    //endregion

    public void setParalyzedTurns(int paralyzedTurns) {
        this.paralyzedTurns = paralyzedTurns;
    }

    // Method for testing
    List<Ladder> getLadderClimbedList() {
        return ladderClimbedList;
    }
}
