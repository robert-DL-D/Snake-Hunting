package com.snakehunter;

import com.snakehunter.controller.GameController;
import com.snakehunter.model.GameModel;
import com.snakehunter.view.GameView;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class Main {

    public static void main(String[] args) {
        GameView gameView = new GameView();
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameView, gameModel);

        gameModel.setListener(gameView);
        gameView.setListener(gameController);
    }
}
