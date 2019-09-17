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

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;


    //TODO add final ints for up down left right

    @Override
    public String move(Square[][] squares, int steps) {
        String message;
        Square currSquare = getSquare(squares, getPosition());

        if (steps == UP){
            moveUp(squares, currSquare);
            message = "Snake moved up to  position " + getPosition();
            return message;
        }
        else if (steps == DOWN){
            moveDown(squares, currSquare);
            message = "Snake moved down to  position " + getPosition();
            return message;
        }
        else if (steps == LEFT){
            moveLeft(squares, currSquare);
            message = "Snake moved left to  position " + getPosition();
            return message;
        }
        else if(steps == RIGHT){
            moveRight(squares, currSquare);
            message = "Snake moved right to  position " + getPosition();
            return message;
        } else {
            message = "Invalid Input";
            return message;
        }

    }

    //TODO move method return string and
    public int moveUp(Square[][] squares, Square currSquare) throws ArrayIndexOutOfBoundsException {
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
