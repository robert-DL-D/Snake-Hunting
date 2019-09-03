package com.snakehunter.model;

public class Ladder implements Placeable{

    private int ID;
    private int bottom;
    private int top;

    public Ladder(int ID, int b, int t) {
        this.ID = ID;
        this.top = t;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }

    public int getID(){
        return ID;
    }

    @Override
    public int getTopPos() {
        return 0;
    }

    @Override
    public int getBottomPos() {
        return 0;
    }

    @Override
    public void setTopPos(int i) {

    }

    @Override
    public void setBottomPos(int i) {

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
    public void move(int i) {

    }
}