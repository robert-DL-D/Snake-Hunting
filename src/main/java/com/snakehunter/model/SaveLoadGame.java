package com.snakehunter.model;

import com.snakehunter.GameContract;
import com.snakehunter.GameStage;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveLoadGame {

    private static final String DELIMITER = ":";
    private static File file = new File("src/main/java/com/snakehunter/file/savegame.txt");
    private GameModelImpl gameModel;
    private GameContract.GameView gameView;

    /*public static void main(String[] args) {
        saveGame();
        loadGame();
    }*/

    public SaveLoadGame(GameModelImpl gameModel) {
        this.gameModel = gameModel;
    }

    public SaveLoadGame(GameModelImpl gameModel, GameContract.GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    public void saveGame() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileWriter fileWriter = new FileWriter(file, false);

            fileWriter.write("humanName" + DELIMITER + "getHumanName()");
            fileWriter.write(System.lineSeparator());

            fileWriter.write("snakeName" + DELIMITER + "getSnakeName()");
            fileWriter.write(System.lineSeparator());

            fileWriter.write("stage" + DELIMITER + gameModel.getGameStage());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("turnNumber" + DELIMITER + gameModel.getNumOfTurns());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("playerTurn" + DELIMITER + gameModel.getCurrentPlayer().getClass().getSimpleName());
            fileWriter.write(System.lineSeparator());

            stringBuilder.append("snakePos");
            for (Snake snakeInList : gameModel.getSnakeList()) {
                stringBuilder.append(DELIMITER).append(snakeInList.getConnectedPosition()).append(DELIMITER).append(snakeInList.getPosition());
            }
            fileWriter.write(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            fileWriter.write(System.lineSeparator());

            stringBuilder.append("ladderPos");
            for (Ladder ladderInList : gameModel.getLadderList()) {
                stringBuilder.append(DELIMITER).append(ladderInList.getPosition()).append(DELIMITER).append(ladderInList.getConnectedPosition());
            }
            fileWriter.write(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            fileWriter.write(System.lineSeparator());

            stringBuilder.append("piecePos");
            for (Human humanInlist : gameModel.getHumanList()) {
                stringBuilder.append(DELIMITER).append(humanInlist.getPosition());
            }
            fileWriter.write(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            fileWriter.write(System.lineSeparator());

            stringBuilder.append("snakeGuardPos");
            Square[][] squares = gameModel.getSquares();
            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares[0].length; j++) {
                    if (squares[i][j].isGuarded()) {
                        stringBuilder.append(DELIMITER).append(squares[i][j].getSquareNo());
                    }
                }
            }
            fileWriter.write(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            fileWriter.write(System.lineSeparator());

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Game Saved");
    }

    // FIXME After loadGame DicePanel gets drawn along with SettingPanel
    public void loadGame() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            String[] stringArray;

            while (true) {
                try {
                    if ((st = br.readLine()) == null) {
                        break;
                    }

                    stringArray = st.split(":");
                    //System.out.println(st);

                    switch (stringArray[0]) {
                        case "humanName":
                            break;
                        case "snakeName":
                            break;
                        case "stage":
                            setStage(stringArray[1]);
                            break;
                        case "turnNumber":
                            setTurnNumber(stringArray[1]);
                            break;
                        case "playerTurn":
                            break;
                        case "snakePos":
                            setSnakePos(stringArray);
                            break;
                        case "ladderPos":
                            setLadderPos(stringArray);
                            break;
                        case "piecePos":
                            setPiecePos(stringArray);
                            break;
                        case "snakeGuardPos":
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            //System.out.println(Arrays.toString(stringArray));
        } catch (FileNotFoundException e) {

            System.out.println("Save file does not exists");
        }
    }

    private void setStage(String stage) {

        GameStage gameStage;

        if (stage.equals("SECOND")) {
            gameStage = GameStage.SECOND;
        } else gameStage = GameStage.FINAL;

        gameView.updateStage(gameStage);
    }

    private void setTurnNumber(String s) {
        gameView.updateTurnNo(Integer.parseInt(s));
    }

    private void setSnakePos(String[] stringArray) {
        for (int i = 0; i < 5; i++) {
            Snake snake = new Snake(Integer.parseInt(stringArray[i * 2 + 2]), Integer.parseInt(stringArray[i * 2 + 1]));
            gameModel.addSnake(snake);
        }
    }

    private void setLadderPos(String[] stringArray) {
        for (int i = 0; i < 5; i++) {
            Ladder ladder = new Ladder(Integer.parseInt(stringArray[i * 2 + 1]), Integer.parseInt(stringArray[i * 2 + 2]));
            gameModel.addLadder(ladder);
        }
    }

    private void setPiecePos(String[] stringArray) {

        gameModel.initHumans(4);

        for (int i = 1; i < 5; i++) {

            gameModel.getHumanList().get(i - 1).setPosition(Integer.parseInt(stringArray[i]));
        }
    }

}
