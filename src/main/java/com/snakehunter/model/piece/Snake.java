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

    @Override
    public String move(Square[][] squares, int steps) throws ArrayIndexOutOfBoundsException {
        String message;
        Square currSquare = getSquare(squares, getPosition());

        if (steps == UP){
            if (moveUp(squares, currSquare) == true) {
                //message = "Snake moved up to position " + getPosition();
                message = null;
                return message;
            }
            else {
                message = "Snake can not move up";
                return message;
            }
        }
        else if (steps == DOWN){
            if(moveDown(squares, currSquare) == true) {
                //message = "Snake moved down to position " + getPosition();
                message = null;
                return message;
            }
            else {
                message = "Snake can not move down";
                return message;
            }
        }
        else if (steps == LEFT){
            if (moveLeft(squares, currSquare) == true) {
                //message = "Snake moved left to position " + getPosition();
                message = null;
                return message;
            }
            else {
                message = "Snake can not move left";
                return message;
            }
        }
        else if(steps == RIGHT){
            if (moveRight(squares, currSquare) == true) {
                //message = "Snake moved right to position " + getPosition();
                message = null;
                return message;
            }
            else {
                message = "Snake can not move right";
                return message;
            }
        } else {
            message = "Invalid Input";
            return message;
        }

    }

    public boolean moveUp(Square[][] squares, Square currSquare) {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;

        if (headRow + 1 > 9) {
            return false;
        }
        else {
            newHeadSquare = squares[headCol][headRow + 1];
            if (newHeadSquare.isGuarded() == true){
                return false;
            }
            else {
                //move head up
                currSquare.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail up
                newTailSquare = squares[tailCol][tailRow + 1];
                newTailPosition = newTailSquare.getSquareNo();
                setConnectedPosition(newTailPosition);
                return true;
            }
        }
    }


    public boolean moveDown(Square[][] squares, Square currSquare) {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;

        if (tailRow - 1 < 0) {
            return false;
        }
        else {
            newHeadSquare = squares[headCol][headRow - 1];
            if (newHeadSquare.isGuarded() == true){
                return false;
            }
            else {
                //move head down
                currSquare.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);


                //move tail down
                newTailSquare = squares[tailCol][tailRow - 1];
                newTailPosition = newTailSquare.getSquareNo();
                setConnectedPosition(newTailPosition);

                return true;
            }
        }
    }


    public boolean moveRight(Square[][] squares, Square currSquare) {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;

        if ( (headCol + 1 > 9) || (tailCol + 1 > 9) ) {
            return false;
        }
        else {
            newHeadSquare = squares[headCol + 1][headRow];
            if (newHeadSquare.isGuarded() == true){
                return false;
            }
            else {
                //move head right
                currSquare.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail right
                newTailSquare = squares[tailCol + 1][tailRow];
                newTailPosition = newTailSquare.getSquareNo();
                setConnectedPosition(newTailPosition);

                return true;
            }
        }
    }


    public boolean moveLeft(Square[][] squares, Square currSquare) {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        int tailRow = tail.getRow();
        int tailCol = tail.getColumn();
        int newHeadPosition, newTailPosition;
        Square newHeadSquare, newTailSquare;

        if ( (headCol - 1 < 0) || (tailCol - 1 < 0) ) {
            return false;
        }
        else {
            newHeadSquare = squares[headCol - 1][headRow];
            if (newHeadSquare.isGuarded() == true){
                return false;
            }
            else {
                //move head left
                currSquare.removePiece(this);
                newHeadPosition = newHeadSquare.getSquareNo();
                setPosition(newHeadPosition);
                newHeadSquare = getSquare(squares, getPosition());
                newHeadSquare.addPiece(this);

                //move tail left
                newTailSquare = squares[tailCol - 1][tailRow];
                newTailPosition = newTailSquare.getSquareNo();
                setConnectedPosition(newTailPosition);

                return true;
            }
        }
    }
}
