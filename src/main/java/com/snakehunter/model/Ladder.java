package com.snakehunter.model;

public class Ladder implements Placeable{

    private int ID;
    private Square bottom;
    private Square top;

    public Ladder(int ID, Square b, Square t) {
        this.ID = ID;
        this.top = t;
    }

    public Square getBottom() {
        return bottom;
    }

    public Square getTop() {
        return top;
    }

    public int getID(){
        return ID;
    }

    @Override
    public Square getTopPos() {
        return top;
    }

    @Override
    public Square getBottomPos() {
        return bottom;
    }

    @Override
    public void setTopPos(Square top) {
        this.top = top;
    }

    @Override
    public void setBottomPos(Square bottom) {
        this.bottom = bottom;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public int getMaxLength() {
        return 0;
    }

    @Override
    public int getMinLength() {
        return 0;
    }

    @Override
    public void setMaxLength(int i) {

    }

    @Override
    public void setMinLength(int i) {

    }

    @Override
    public Square move(int i) {
        return null;
    }
}