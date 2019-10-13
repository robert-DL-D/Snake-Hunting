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
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SaveLoadGame {

    private GameModelImpl gameModel;
    private GameContract.GameView gameView;

    private static final String DELIMITER = ":";

    private static final String saveFileNameTemplate = "savefile";
    private int saveNumber = 1;
    private String saveFileName = saveFileNameTemplate + saveNumber;
    private File file = new File("src/main/java/com/snakehunter/file/" + saveFileName + ".txt");
    private static final String FOLDER_PATH = "src/main/java/com/snakehunter/file";

    private boolean loadedGame = false;
    private boolean savedGame = false;
    private File saveFile;

    private String humanName;
    private String snakeName;

    public SaveLoadGame() {
    }

    public void saveGame() {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            if (savedGame) {
                file = saveFile;
            } else if (!loadedGame) {
                while (file.exists()) {
                    saveNumber++;
                    saveFileName = saveFileNameTemplate + saveNumber;
                    file = new File("src/main/java/com/snakehunter/file/" + saveFileName + ".txt");
                }
            }
            FileWriter fileWriter = new FileWriter(file, false);

            fileWriter.write("humanName" + DELIMITER + gameModel.getHumanPlayer().getName());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("snakeName" + DELIMITER + gameModel.getSnakePlayer().getName());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("stage" + DELIMITER + gameModel.getGameStage());
            fileWriter.write(System.lineSeparator());

            stringBuilder.append("snakePos");
            for (Snake snakeInList : gameModel.getSnakeList()) {
                stringBuilder.append(DELIMITER).append(snakeInList.getConnectedPosition()).append(DELIMITER)
                        .append(snakeInList.getPosition());
            }
            writing(stringBuilder, fileWriter);

            stringBuilder.append("ladderPos");
            for (Ladder ladderInList : gameModel.getLadderList()) {
                stringBuilder.append(DELIMITER).append(ladderInList.getPosition()).append(DELIMITER)
                        .append(ladderInList.getConnectedPosition());
            }
            writing(stringBuilder, fileWriter);

            List<Human> humanList = gameModel.getHumanList();
            stringBuilder.append("piecePos");
            for (Human humanInlist : humanList) {
                stringBuilder.append(DELIMITER).append(humanInlist.getPosition());
            }
            writing(stringBuilder, fileWriter);

            stringBuilder.append("pieceParalyzedTurnRemaining");
            for (Human humanInlist : humanList) {
                stringBuilder.append(DELIMITER).append(humanInlist.getParalyzeTurns());
            }
            writing(stringBuilder, fileWriter);

            stringBuilder.append("snakeGuardPos");
            String[] snakeGuardPos = new String[3];
            Square[][] twoDSquareArray = gameModel.getSquares();
            if (gameModel.getRemainingGuards() == 3) {
                for (int i = 0; i < 3; i++) {
                    stringBuilder.append(DELIMITER).append("-1");
                }
            } else {
                for (Square[] squareArray : twoDSquareArray) {
                    for (Square square : squareArray) {
                        if (square.isGuarded()) {
                            for (int i = 0; i < snakeGuardPos.length; i++) {
                                if (snakeGuardPos[i] == null) {
                                    snakeGuardPos[i] = String.valueOf(square.getSquareNo());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < snakeGuardPos.length; i++) {
                if (snakeGuardPos[i] != null) {
                    stringBuilder.append(DELIMITER).append(snakeGuardPos[i]);
                } else {
                    stringBuilder.append(DELIMITER).append("-1");
                }
            }
            writing(stringBuilder, fileWriter);

            fileWriter.write("turnNumber" + DELIMITER + gameModel.getNumOfTurns());

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        savedGame = true;
        saveFile = file;
        JOptionPane.showMessageDialog(new JFrame(), "Game Saved Successfully\n" + "Your save file is " + file.getName(), "Game Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    private void writing(StringBuilder stringBuilder, FileWriter fileWriter) throws IOException {
        fileWriter.write(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
        fileWriter.write(System.lineSeparator());
    }

    public void loadGame() {
        JFileChooser jFileChooser = new JFileChooser(FOLDER_PATH);
        jFileChooser.showOpenDialog(null);
        file = jFileChooser.getSelectedFile();

        getPlayerNameInFile(file);

        if (gameModel.getHumanPlayer().getName().equals(humanName) && gameModel.getSnakePlayer().getName().equals(snakeName)) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String st;
                String[] stringArray;

                while (true) {
                    try {
                        if ((st = br.readLine()) == null) {
                            break;
                        }

                        stringArray = st.split(DELIMITER);

                        switch (stringArray[0]) {
                            case "humanName":
                                gameModel.getHumanPlayer().setName(stringArray[1]);
                                break;
                            case "snakeName":
                                gameModel.getSnakePlayer().setName(stringArray[1]);
                                break;
                            case "stage":
                                setStage(stringArray[1]);
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
                            case "pieceParalyzedTurnRemaining":
                                setParalyzedTurn(stringArray);
                                break;
                            case "snakeGuardPos":
                                setGuardPos(stringArray);
                                break;
                            case "turnNumber":
                                setTurnNumber(stringArray[1]);
                                break;
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(new JFrame(), e.toString(), "IOException", JOptionPane.ERROR_MESSAGE);
                    }
                }
                loadedGame = true;
                saveFile = file;
                gameView.hideSettingPanel();
                gameView.showTurnPanel();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(new JFrame(), "File not found", "File not found", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                if (file.getName().contains(saveFileNameTemplate)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Incorrect save file for current players", "Incorrect save file", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Not a valid save file", "Invalid save file", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NullPointerException nullEx) {
            }
        }
    }

    private void getPlayerNameInFile(File file) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] stringArray;

            while (true) {
                try {
                    if ((st = br.readLine()) == null) {
                        break;
                    }

                    stringArray = st.split(DELIMITER);

                    switch (stringArray[0]) {
                        case "humanName":
                            humanName = stringArray[1];
                            break;
                        case "snakeName":
                            snakeName = stringArray[1];
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "No save file found in folder", "No save file found", JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException nullEx) {
        }
    }

    private void setStage(String stage) {
        GameStage gameStage;

        if (stage.equals("SECOND")) {
            gameStage = GameStage.SECOND;
        } else {
            gameStage = GameStage.FINAL;
        }

        gameModel.setGameStage(gameStage);
        gameView.updateStage(gameStage);
    }

    private void setSnakePos(String[] stringArray) {

        for (Square[] squares : gameModel.getSquares()) {
            for (Square square : squares) {
                if (square.getPieceList() != null) {
                    square.getPieceList().clear();
                }

            }
        }

        gameModel.getSnakeList().clear();

        for (int i = 0; i < 5; i++) {
            gameModel.addSnake(new Snake(Integer.parseInt(stringArray[i * 2 + 2]), Integer.parseInt(stringArray[i * 2 + 1])));

            if (gameModel.getSnakeList().size() != i + 1) {
                for (int j = 3; j < gameModel.getSquares().length * gameModel.getSquares()[0].length; j++) {
                    if (gameModel.getSquare(j).getPieceList().isEmpty()
                            && gameModel.getSquare(j - 1).getPieceList().isEmpty()) {
                        Snake snake = new Snake(j, j - 1);

                        gameModel.addSnake(snake);
                        gameModel.getSquare(j).getPieceList().remove(snake);

                        snake.setPosition(Integer.parseInt(stringArray[i * 2 + 2]));
                        snake.setConnectedPosition(Integer.parseInt(stringArray[i * 2 + 1]));
                        gameModel.getSquare(Integer.parseInt(stringArray[i * 2 + 2])).addPiece(snake);
                        break;
                    }
                }
            }
        }
    }

    private void setLadderPos(String[] stringArray) {
        gameModel.getLadderList().clear();

        for (int i = 0; i < 5; i++) {
            Ladder ladder =
                    new Ladder(Integer.parseInt(stringArray[i * 2 + 1]), Integer.parseInt(stringArray[i * 2 + 2]));
            gameModel.addLadder(ladder);
        }
    }

    private void setPiecePos(String[] stringArray) {
        gameModel.initHumans(4);

        for (int i = 0; i < 4; i++) {

            gameModel.getHumanList().get(i).setPosition(Integer.parseInt(stringArray[i + 1]));
        }
    }

    private void setParalyzedTurn(String[] stringArray) {

        for (int i = 0; i < 4; i++) {
            gameModel.getHumanList().get(i).setParalyzedTurns(Integer.parseInt(stringArray[i + 1]));
        }
    }

    private void setGuardPos(String[] stringArray) {

        for (int i = 0; i < 3; i++) {

            int guardPos = Integer.parseInt(stringArray[i + 1]);

            if (guardPos != -1) {
                gameModel.addGuard(guardPos);
            }
        }
    }

    private void setTurnNumber(String s) {
        int turn = Integer.parseInt(s);
        gameModel.setNumOfTurns(turn);
        gameView.updateTurnNo(turn);
    }

    public void setGameModel(GameModelImpl gameModel) {
        this.gameModel = gameModel;
    }

    public void setGameView(GameContract.GameView gameView) {
        this.gameView = gameView;
    }
}
