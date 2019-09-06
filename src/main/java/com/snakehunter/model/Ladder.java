package com.snakehunter.model;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-08-26
 */

public class Ladder implements Placeable{

    private int ID;
    private int bottom;
    private int top;

    public Ladder(int ID, int b, int t) {
        this.ID = ID;
        this.top = t;
        this.bottom = b;
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
        return top;
    }

    @Override
    public int getBottomPos() {
        return bottom;
    }

    @Override
    public void setTopPos(int top) {
        this.top = top;
    }

    @Override
    public void setBottomPos(int bottom) {
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