package com.snakehunter;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameContract.GameView;
import com.snakehunter.controller.GameController;
import com.snakehunter.model.GameModelImpl;
import com.snakehunter.view.GameViewImpl;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class Main {

    private static boolean isDebugMode = true;

    public static void main(String[] args) {
        GameModel gameModel = new GameModelImpl();
        GameView gameView = new GameViewImpl(gameModel);
        GameController gameController = new GameController(gameView, gameModel);

        gameModel.setOnDataChangedListener(gameView);
        gameView.setOnViewEventListener(gameController);
    }

    public static boolean isDebugMode() {
        return isDebugMode;
    }
}
