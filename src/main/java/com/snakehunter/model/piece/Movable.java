package com.snakehunter.model.piece;

import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.LadderClimbedThresholdException;
import com.snakehunter.model.exceptions.MaxPositionExceedException;
import com.snakehunter.model.exceptions.SnakeMoveOutOfBoundsException;
import com.snakehunter.model.exceptions.SnakeMoveToGuardedSquareException;

public interface Movable {
    // return a message describes the movement
    String move(Square[][] squares, int steps, int index) throws SnakeMoveOutOfBoundsException,
            SnakeMoveToGuardedSquareException, MaxPositionExceedException, LadderClimbedThresholdException;

    Square moveKnight(Square[][] squares, Square newSquare);
}

