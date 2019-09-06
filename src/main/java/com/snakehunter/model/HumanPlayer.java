package com.snakehunter.model;

import com.snakehunter.model.piece.Piece;

class HumanPlayer {

    private static final int MAX_Piece = 4;

    private int numGuards = 3;

    private Piece[] Piece = new Piece[MAX_Piece];

    public HumanPlayer(String name) {

    }

    public int getNumGuards() {
        return numGuards;
    }

    public void setNumGuards(int numGuards) {
        this.numGuards = numGuards;
    }
}
