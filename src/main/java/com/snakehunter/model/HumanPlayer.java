package com.snakehunter.model;

class HumanPlayer
        extends Player {

    private static final int MAX_HUMAN = 4;

    private Placeable[] objects = new Placeable[MAX_HUMAN];

    public HumanPlayer(String name) {
        super(name);
    }

}
