package com.snakehunter.model;

class SnakePlayer
        extends Player {

    private static final int MAX_SNAKES = 5;

    private Placeable[] objects = new Placeable[MAX_SNAKES];

    public SnakePlayer(String name) {
        super(name);
    }
}
