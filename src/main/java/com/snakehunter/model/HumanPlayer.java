package com.snakehunter.model;

class HumanPlayer
        extends Player {

    private static final int MAX_Piece = 4;

    private int numGuards = 3;

    private Placeable[] Piece = new Placeable[MAX_Piece];

    public HumanPlayer(String name) {
        super(name);
    }

    public int getNumGuards() {
        return numGuards;
    }

    public void setNumGuards(int numGuards) {
        this.numGuards = numGuards;
    }
}
