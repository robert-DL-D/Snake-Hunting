package com.snakehunter.model.piece;

import com.snakehunter.model.Square;

/**
 * @author David Manolitsas
 * @date 2019-09-08
 */
public class Snake
        extends ConnectorPiece
        implements Movable {
    public Snake(int position, int connectedPosition) {
        super(position, connectedPosition);
    }

    @Override
    public int move(Square[][] squares, int steps) {
        return 0;
    }

    public int moveUp(Square[][] squares, Square currSquare) {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;

        //move head up
        newHeadSquare = squares[headCol][headRow + 1];
        newHeadPosition = newHeadSquare.getSquareNo();
        setPosition(newHeadPosition);

        //move tail up
        newTailSquare = squares[tailCol][tailRow + 1];
        newTailPosition = newTailSquare.getSquareNo();
        setConnectedPosition(newTailPosition);

        return newHeadPosition;
    }


    public int moveDown(Square[][] squares, Square currSquare) {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;

        //move head down
        newHeadSquare = squares[headCol][headRow - 1];
        newHeadPosition = newHeadSquare.getSquareNo();
        setPosition(newHeadPosition);

        //move tail down
        newTailSquare = squares[tailCol][tailRow - 1];
        newTailPosition = newTailSquare.getSquareNo();
        setConnectedPosition(newTailPosition);

        return newHeadPosition;
    }


    public int moveRight(Square[][] squares, Square currSquare) {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;

        //move head right
        newHeadSquare = squares[headCol + 1][headRow];
        newHeadPosition = newHeadSquare.getSquareNo();
        setPosition(newHeadPosition);

        //move tail right
        newTailSquare = squares[tailCol + 1][tailRow];
        newTailPosition = newTailSquare.getSquareNo();
        setConnectedPosition(newTailPosition);

        return newHeadPosition;
    }

    public int moveLeft(Square[][] squares, Square currSquare) {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;

        //move head left
        newHeadSquare = squares[headCol - 1][headRow];
        newHeadPosition = newHeadSquare.getSquareNo();
        setPosition(newHeadPosition);

        //move tail left
        newTailSquare = squares[tailCol - 1][tailRow];
        newTailPosition = newTailSquare.getSquareNo();
        setConnectedPosition(newTailPosition);

        return newHeadPosition;
    }
}
