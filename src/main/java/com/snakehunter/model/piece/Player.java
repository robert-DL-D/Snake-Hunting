package com.snakehunter.model.piece;

import java.util.ArrayList;
import java.util.List;

public class Player<T> {

    private String name;
    private final List<T> pieceList;

    public Player(String name) {
        this.name = name;
        pieceList = new ArrayList<>();
    }

    public boolean isSnake(){
        return pieceList.get(0) instanceof Snake;
    }

    public List<T> getPieceList() {
        return pieceList;
    }

    public void addPiece(T piece) {
        pieceList.add(piece);
    }

    public T getPiece(int index) {
        if (index >= pieceList.size() || index < 0) {
            return null;
        }

        return pieceList.get(index);
    }

    public void removePiece(T t){
        pieceList.remove(t);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
