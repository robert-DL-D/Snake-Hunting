package com.snakehunter.model.piece;

import com.snakehunter.model.GameModel;
import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.SnakeMoveOutOfBoundsException;
import com.snakehunter.model.exceptions.SnakeMoveToGuardedSquareException;

import java.util.LinkedList;

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
    private boolean isSnakeDead;

    void killSnake() {
        isSnakeDead = true;
    }

    public boolean isSnakeDead() {
        return isSnakeDead;
    }

    @Override
    public String move(Square[][] squares, int steps, int index) {
        Square currSquare = getSquare(squares, getPosition());

        try {
            switch (steps) {
                case UP:
                    if (moveUp(squares, currSquare)) {
                        return null;
                    }
                    break;
                case DOWN:
                    if (moveDown(squares, currSquare)) {
                        return null;
                    }

                    break;
                case LEFT:
                    if (moveLeft(squares, currSquare)) {
                        return null;
                    }

                    break;
                case RIGHT:
                    if (moveRight(squares, currSquare)) {
                        return null;
                    }
                    break;
            }
        } catch (SnakeMoveOutOfBoundsException | SnakeMoveToGuardedSquareException e) {
            return e.getMessage();
        }
        return "";
    }

    @Override
    public Square moveKnight(Square[][] squares, Square newSquare) {
        return null;
    }

    public String eatHuman(GameModel gameModel, Square newHeadSquare) {
        LinkedList<Piece> tempList = new LinkedList<>();

        for (Piece p : newHeadSquare.getPieceList()) {
            if (p instanceof Human) {
                if (((Human) p).getParalyzeTurns() == 0) {
                    tempList.add(p);
                }
            }
        }

        if (!tempList.isEmpty()) {
            for (Piece piece : tempList) {
                gameModel.getSquare(newHeadSquare.getSquareNo()).removePiece(piece);

                piece.setPosition(getConnectedPosition());
                gameModel.getSquare(getConnectedPosition()).addPiece(piece);

                ((Human) piece).paralyzeHuman(this);

                return "and swallowed piece " + (gameModel.getHumanList().indexOf(piece) + 1)
                        + " and dragged it down to position " + gameModel.getSquare(getConnectedPosition()).getSquareNo();
            }
        }

        return null;
    }

    public void killHuman(Square newHeadSquare) {
        for (Piece p : newHeadSquare.getPieceList()) {
            if (p instanceof Human) {
                if (!((Human) p).isUnkillable()) {
                    ((Human) p).killHuman();
                }
            }
        }
    }

    /*private boolean moveToDirection(int step, Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        Square newHeadSquare;
        int initialLength = getLength();

        if (headRow + 1 > 9) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            newHeadSquare = squares[headCol][headRow + 1];
            return moveSnake(squares, currSquare, tail, newHeadSquare, initialLength);
        }
    }*/

    private boolean moveUp(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        Square newHeadSquare;
        int initialLength = getLength();

        if (headRow + 1 > 9) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            newHeadSquare = squares[headCol][headRow + 1];
            return moveSnake(squares, currSquare, tail, newHeadSquare, initialLength);
        }
    }

    private boolean moveDown(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        Square newHeadSquare;
        int initialLength = getLength();

        if (headRow - 1 < 0) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            newHeadSquare = squares[headCol][headRow - 1];
            return moveSnake(squares, currSquare, tail, newHeadSquare, initialLength);
        }
    }

    private boolean moveRight(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headRow = currSquare.getRow();
        int headCol = currSquare.getColumn();
        Square tail = getSquare(squares, getConnectedPosition());
        Square newHeadSquare;
        int initialLength = getLength();

        if ((headCol + 1 > 9)) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            newHeadSquare = squares[headCol + 1][headRow];
            return moveSnake(squares, currSquare, tail, newHeadSquare, initialLength);
        }
    }

    private boolean moveLeft(Square[][] squares, Square currSquare) throws SnakeMoveOutOfBoundsException, SnakeMoveToGuardedSquareException {
        int headCol = currSquare.getColumn();
        int headRow = currSquare.getRow();
        Square tail = getSquare(squares, getConnectedPosition());
        Square newHeadSquare = squares[headCol - 1][headRow];
        int initialLength = getLength();

        if ((headCol - 1 < 0)) {
            throw new SnakeMoveOutOfBoundsException();
        } else {

            return moveSnake(squares, currSquare, tail, newHeadSquare, initialLength);
        }
    }

    private boolean moveSnake(Square[][] squares, Square currSquare, Square tail, Square newHeadSquare, int initialLength) throws SnakeMoveToGuardedSquareException, SnakeMoveOutOfBoundsException {

        if (newHeadSquare.isGuarded()) {
            throw new SnakeMoveToGuardedSquareException();
        } else if ((newHeadSquare.getSquareNo() - initialLength) < 1) {
            throw new SnakeMoveOutOfBoundsException();
        } else {
            //move head up
            currSquare.removePiece(this);
            tail.removePiece(this);
            setPosition(newHeadSquare.getSquareNo());
            newHeadSquare = getSquare(squares, getPosition());
            newHeadSquare.addPiece(this);

            //move tail up
            setConnectedPosition(getPosition() - initialLength);
            getSquare(squares, getConnectedPosition()).addPiece(this);

            return true;
        }
    }
}
