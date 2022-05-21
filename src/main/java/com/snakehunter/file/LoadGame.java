package com.snakehunter.file;

import com.snakehunter.GameStage;
import com.snakehunter.controller.GameController;
import com.snakehunter.model.Square;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;
import com.snakehunter.view.GameView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoadGame extends FileGame {

    private String humanName;
    private String snakeName;

    public void loadGame() {
        JFileChooser jFileChooser = new JFileChooser(FOLDER_PATH);

        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser.getSelectedFile();

            getPlayerNameInFile(file);

            if (gameModel.getHumanPlayer().getName().equals(humanName) && gameModel.getSnakePlayer().getName().equals(snakeName)) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String st;
                    String[] stringArray;

                    gameModel.getLadderList().clear();
                    gameModel.getSnakeList().clear();

                    for (Square[] squareArray : gameModel.getSquares()) {
                        for (Square square : squareArray) {
                            if (!square.getPieceList().isEmpty()) {
                                square.getPieceList().clear();
                            }
                        }
                    }

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
                                case "piecePos":
                                    setPiecePos(stringArray);
                                    break;
                                case "ladderPos":
                                    setLadderPos(stringArray);
                                    break;
                                case "snakePos":
                                    setSnakePos(stringArray);
                                    break;
                                case "pieceParalyzedTurnRemaining":
                                    setParalyzedTurn(stringArray);
                                    break;
                                case "climbedLadder":
                                    setClimbedLadder(stringArray);
                                    break;
                                case "snakeGuardPos":
                                    setGuardPos(stringArray);
                                    break;
                                case "turnNumber":
                                    setTurnNumber(stringArray[1]);
                                    break;
                                default:
                                    break;
                            }
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(new JFrame(), e.toString(), "IOException", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    saveFile = file;
                    gameView.hideSettingPanel();
                    gameView.showTurnPanel();

                    gameView.setOnDiceViewEventListener(gameController);
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(new JFrame(), "File not found", "File not found", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (file.getName().contains(saveFileNameTemplate)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Incorrect save file for current players", "Incorrect save file", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Not a valid save file", "Invalid save file", JOptionPane.WARNING_MESSAGE);
                }
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
            JOptionPane.showMessageDialog(new JFrame(), "No save file found in folder",
                    "No save file found", JOptionPane.WARNING_MESSAGE);
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

    private void setPiecePos(String[] stringArray) {
        //gameModel.initHumans(NUM_OF_HUMANS);

        for (int i = 0; i < NUM_OF_HUMANS; i++) {
            gameModel.getHumanList().get(i).setPosition(Integer.parseInt(stringArray[i + 1]));

        }
    }

    private void setLadderPos(String[] stringArray) {
        if (gameModel.getGameStage() == GameStage.SECOND) {
            for (int i = 0; i < LADDER_LIMIT; i++) {
                Ladder ladder =
                        new Ladder(Integer.parseInt(stringArray[i * 2 + 1]),
                                Integer.parseInt(stringArray[i * 2 + 2]));
                gameModel.addLadder(ladder);
            }
        }
    }

    private void setSnakePos(String[] stringArray) {
        for (int i = 0; i < (stringArray.length - 1) / 2; i++) {
            gameModel.addSnake(new Snake(Integer.parseInt(stringArray[i * 2 + 2]),
                    Integer.parseInt(stringArray[i * 2 + 1])));

            if (gameModel.getSnakeList().size() != i + 1) {
                for (int j = FIRST_VALID_SNAKE_PLACEMENT_SQUARE;
                     j < gameModel.getSquares().length * gameModel.getSquares()[0].length; j++) {
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

    private void setParalyzedTurn(String[] stringArray) {
        for (int i = 0; i < NUM_OF_HUMANS; i++) {
            gameModel.getHumanList().get(i).setParalyzedTurns(Integer.parseInt(stringArray[i + 1]));
        }
    }

    private void setClimbedLadder(String[] stringArray) {
        for (int i = 0; i < NUM_OF_HUMANS; i++) {
            for (int j = 1; j <= HUMAN_LADDER_CLIMBED_MINIMUM; j++) {
                int ladderPosition = Integer.parseInt(stringArray[i * HUMAN_LADDER_CLIMBED_MINIMUM + j]);

                if (ladderPosition != -1) {
                    for (Ladder ladder : gameModel.getLadderList()) {
                        if (ladder.getPosition() == ladderPosition) {
                            gameModel.getHumanList().get(i).addToLadderClimbedList(ladder);
                        }
                    }
                }
            }
        }
    }

    private void setGuardPos(String[] stringArray) {

        for (int i = 0; i < SNAKE_GUARD_LIMIT; i++) {

            int guardPos = Integer.parseInt(stringArray[i + 1]);

            if (guardPos != NOT_PLACED_SNAKE_GUARD_POSITION) {
                gameModel.addGuard(guardPos);
            }
        }
        gameView.updateGuardNo();
    }

    private void setTurnNumber(String s) {
        int turn = Integer.parseInt(s);
        gameModel.setNumOfTurns(turn);
        gameView.updateTurnNo(turn);
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
