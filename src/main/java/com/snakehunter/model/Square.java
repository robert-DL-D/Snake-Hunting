package com.snakehunter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-08-26
 */
public class Square {

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

    public void removePlaceable(Placeable placeable) {
        currentPlaceables.remove(placeable);
    }

    public int getSquareNo() {
        return squareNo;
    }

    public void setGuarded(boolean guarded) {
        isGuarded = guarded;
    }

    public boolean isGuarded() {
        return isGuarded;
    }

    public Snake getSnake() {
        for (Placeable currentPlaceable : currentPlaceables) {
            if (currentPlaceable instanceof Snake) {
                return (Snake) currentPlaceable;
            }
        }

        return null;
    }

    public Ladder getLadder() {
        for (Placeable currentPlaceable : currentPlaceables) {
            if (currentPlaceable instanceof Ladder) {
                return (Ladder) currentPlaceable;
            }
        }

        return null;
    }
}
