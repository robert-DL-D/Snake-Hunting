package com.snakehunter.model.piece;

/**
 * @author WeiYi Yu
 * @date 2019-09-06
 */
public abstract class Piece {
    private int position;

    public Piece(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
