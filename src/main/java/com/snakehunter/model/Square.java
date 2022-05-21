package com.snakehunter.model;

import com.snakehunter.controller.GameController;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Piece;
import com.snakehunter.model.piece.Snake;
import com.snakehunter.view.ClickablePanel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-08-26
 */
public class Square extends ClickablePanel {

    private final int squareNo;
    private final int column;
    private final int row;
    private boolean isGuarded;
    private final List<Piece> pieceList = new ArrayList<>();

    private GameController listener;

    public Square(int squareNo, int column, int row) {
        this.squareNo = squareNo;
        this.row = row;
        this.column = column;
        addMouseListener(this);
    }

    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    public void removePiece(Piece piece) {
        pieceList.remove(piece);
    }

    public int getSquareNo() {
        return squareNo;
    }

    void setGuarded(boolean guarded) {
        isGuarded = guarded;
    }

    public boolean isGuarded() {
        return isGuarded;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Snake getSnake() {
        for (Piece piece : pieceList) {
            if (piece instanceof Snake) {
                return (Snake) piece;
            }
        }

        return null;
    }

    public Ladder getLadder() {
        for (Piece piece : pieceList) {
            if (piece instanceof Ladder) {
                return (Ladder) piece;
            }
        }

        return null;
    }

    public void setOnViewEventListener(GameController listener) {
        this.listener = listener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("No listener");

        listener.onSquareClick(squareNo);
    }

}
