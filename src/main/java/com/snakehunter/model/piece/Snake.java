package com.snakehunter.model.piece;

import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.SnakeMoveOutOfBoundsException;
import com.snakehunter.model.exceptions.SnakeMoveToGuardedSquareException;

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

    @Override
    public String move(Square[][] squares, int steps) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        String message;
        Square currSquare = getSquare(squares, getPosition());

        try {
            if (steps == UP) {
                if (moveUp(squares, currSquare) == true) {
                    return null;
                }
            } else if (steps == DOWN) {
                if (moveDown(squares, currSquare) == true) {
                    return null;
                }

            } else if (steps == LEFT) {
                if (moveLeft(squares, currSquare) == true) {
                    return null;
                }

            } else if (steps == RIGHT) {
                if (moveRight(squares, currSquare) == true) {
                    return null;
                }
            } else {
                return message = "Invalid Input";
            }
        } catch (SnakeMoveOutOfBoundsException e) {
            return message = e.getMessage();
        } catch(SnakeMoveToGuardedSquareException e1) {
            return message = e1.getMessage();
        }
        return message = "invalid move";
    }

    public boolean moveUp(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;
        int intialLength = getLength();

        if (headRow + 1 > 9) {
            throw new SnakeMoveOutOfBoundsException();
        }
        else {
            newHeadSquare = squares[headCol][headRow + 1];
            if (newHeadSquare.isGuarded() == true){
                throw new SnakeMoveToGuardedSquareException();
            }
            else {
                //move head up
                currSquare.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail up
                setConnectedPosition(getPosition() - intialLength);
                newTailSquare = getSquare(squares, getConnectedPosition());
                return true;
            }
        }
    }


    public boolean moveDown(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;
        int intialLength = getLength();

        if (tailRow - 1 < 0) {
            throw new SnakeMoveOutOfBoundsException();

        }
        else {
            newHeadSquare = squares[headCol][headRow - 1];
            if (newHeadSquare.isGuarded() == true){
                throw new SnakeMoveToGuardedSquareException();
            }
            else {
                //move head down
                currSquare.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);


                //move tail down
                setConnectedPosition(getPosition() - intialLength);
                newTailSquare = getSquare(squares, getConnectedPosition());
                return true;
            }
        }
    }


    public boolean moveRight(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;
        int intialLength = getLength();

        if ( (headCol + 1 > 9) || (tailCol + 1 > 9) ) {
            throw new SnakeMoveOutOfBoundsException();
        }
        else {
            newHeadSquare = squares[headCol + 1][headRow];
            if (newHeadSquare.isGuarded() == true){
                throw new SnakeMoveToGuardedSquareException();
            }
            else {
                //move head right
                currSquare.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail right
                setConnectedPosition(getPosition() - intialLength);
                newTailSquare = getSquare(squares, getConnectedPosition());
                return true;
            }
        }
    }


    public boolean moveLeft(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;
        int intialLength = getLength();

        if ( (headCol - 1 < 0) || (tailCol - 1 < 0) ) {
            throw new SnakeMoveOutOfBoundsException();
        }
        else {
            newHeadSquare = squares[headCol - 1][headRow];
            if (newHeadSquare.isGuarded() == true){
                throw new SnakeMoveToGuardedSquareException();
            }
            else {
                //move head left
                currSquare.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail left
                setConnectedPosition(getPosition() - intialLength);
                newTailSquare = getSquare(squares, getConnectedPosition());
                return true;
            }
        }
    }
}
