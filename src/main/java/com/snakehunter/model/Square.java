package com.snakehunter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-08-26
 */
class Square {

    private int squareNo;
    private boolean isGuarded = false;
    public List<Placeable> currentPlaceables;

    public Square(int squareNo) {
        this.squareNo = squareNo;
        currentPlaceables = new ArrayList<>();
    }

    public void addPlaceable(Placeable placeable) {
        currentPlaceables.add(placeable);
    }

    public List<Placeable> getCurrentPlaceables() {
        return currentPlaceables;
    }

    public int getSquareNo() {
        return squareNo;
    }

    public boolean isGuarded() {
        return isGuarded;
    }
}
