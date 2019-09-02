package com.snakehunter.model;

import com.snakehunter.GameStage;
import com.snakehunter.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class GameModel {

    private static final int MAX_GUARDS = 3;

    private GameStage gameStage;

    private Map<Integer, Player> playerMap;
    private Player currentPlayer;

    private int numOfGuards = 0;
    private int numOfTurns = 0;

    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    private Player getPlayer(int numOfTurns) {
        int index = numOfTurns % playerMap.size();
        return playerMap.get(index);
    }

    public void setPlayers(int numOfPlayers) {
        playerMap = initPlayers(numOfPlayers);
    }

    private static Map<Integer, Player> initPlayers(int numOfPlayers) {
        Map<Integer, Player> players = new HashMap<>();

        for (int i = 1; i <= numOfPlayers; i++) {
            Player player = new Player("Player " + i);
            players.put(i % numOfPlayers, player);
        }
        return players;
    }
}
