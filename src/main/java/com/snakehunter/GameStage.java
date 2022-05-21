package com.snakehunter;

public enum GameStage {
    INITIAL(0), SECOND(100), FINAL(40);

    private final int maxTurns;

    GameStage(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public int getMaxTurns() {
        return maxTurns;
    }
}
