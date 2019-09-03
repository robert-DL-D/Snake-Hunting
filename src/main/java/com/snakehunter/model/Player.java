package com.snakehunter.model;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class Player implements Placeable{
    private String name;
    private int position;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getTopPos() {
        return position;
    }

    @Override
    public int getBottomPos() {
        return 0;
    }

    @Override
    public void setTopPos(int i) {
        position = i;
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
