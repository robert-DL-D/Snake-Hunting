package com.snakehunter.file;

import com.snakehunter.controller.GameController;
import com.snakehunter.model.GameModel;
import com.snakehunter.view.GameView;

import java.io.File;

abstract class FileGame {

    static final String FOLDER_PATH = "src/main/java/com/snakehunter/file";
    static final String saveFileNameTemplate = "savefile";
    int saveNumber = 1;
    String saveFileName = saveFileNameTemplate + saveNumber;
    File file = new File(FOLDER_PATH + saveFileName + ".txt");
    static final String DELIMITER = ":";
    File saveFile;

    static final int NUM_OF_HUMANS = 4;
    static final int SNAKE_GUARD_LIMIT = 3;
    static final int SNAKE_LIMIT = 5;
    static final int LADDER_LIMIT = 5;
    static final int NOT_PLACED_SNAKE_GUARD_POSITION = -1;
    static final int FIRST_VALID_SNAKE_PLACEMENT_SQUARE = 3;
    static final int HUMAN_LADDER_CLIMBED_MINIMUM = 3;
    static final int PLACEHOLDER_FOR_NO_LADDER_CLIMBED = -1;

    GameModel gameModel;
    GameView gameView;
    GameController gameController;

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

}
