package com.snakehunter.file;

import com.snakehunter.model.Square;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SaveGame extends FileGame {

    public void saveGame() {
        try {
            if (saveFile != null) {
                file = saveFile;
            } else {
                while (file.exists()) {
                    saveNumber++;
                    saveFileName = saveFileNameTemplate + saveNumber;
                    file = new File("src/main/java/com/snakehunter/file/"
                            + saveFileName + ".txt");
                }
            }

            FileWriter fileWriter = new FileWriter(file, false);

            fileWriter.write("humanName" + DELIMITER + gameModel.getHumanPlayer().getName());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("snakeName" + DELIMITER + gameModel.getSnakePlayer().getName());
            fileWriter.write(System.lineSeparator());

            fileWriter.write("stage" + DELIMITER + gameModel.getGameStage());
            fileWriter.write(System.lineSeparator());

            StringBuilder stringBuilder = new StringBuilder();

            List<Human> humanList = gameModel.getHumanList();

            stringBuilder.append("piecePos");
            for (Human humanInlist : humanList) {
                stringBuilder.append(DELIMITER).append(humanInlist.getPosition());
            }
            writeToFile(stringBuilder, fileWriter);

            stringBuilder.append("ladderPos");
            for (Ladder ladderInList : gameModel.getLadderList()) {
                stringBuilder.append(DELIMITER).append(ladderInList.getPosition()).append(DELIMITER)
                        .append(ladderInList.getConnectedPosition());
            }
            writeToFile(stringBuilder, fileWriter);

            stringBuilder.append("snakePos");
            for (Snake snakeInList : gameModel.getSnakeList()) {
                stringBuilder.append(DELIMITER).append(snakeInList.getConnectedPosition())
                        .append(DELIMITER).append(snakeInList.getPosition());
            }
            writeToFile(stringBuilder, fileWriter);

            stringBuilder.append("pieceParalyzedTurnRemaining");
            for (Human humanInlist : humanList) {
                stringBuilder.append(DELIMITER).append(humanInlist.getParalyzeTurns());
            }
            writeToFile(stringBuilder, fileWriter);

            stringBuilder.append("climbedLadder");
            for (Human humanInlist : humanList) {
                List<Ladder> ladderClimbedList = humanInlist.getLadderClimbedList();

                if (ladderClimbedList.isEmpty()) {
                    for (int i = 0; i < HUMAN_LADDER_CLIMBED_MINIMUM; i++) {
                        stringBuilder.append(DELIMITER).append(PLACEHOLDER_FOR_NO_LADDER_CLIMBED);
                    }
                } else {
                    for (Ladder ladder : ladderClimbedList) {
                        stringBuilder.append(DELIMITER).append(ladder.getPosition());
                    }

                    for (int i = 0; i < HUMAN_LADDER_CLIMBED_MINIMUM - ladderClimbedList.size(); i++) {
                        stringBuilder.append(DELIMITER).append(PLACEHOLDER_FOR_NO_LADDER_CLIMBED);
                    }
                }
            }
            writeToFile(stringBuilder, fileWriter);

            stringBuilder.append("snakeGuardPos");
            if (gameModel.getRemainingGuards() == SNAKE_GUARD_LIMIT) {
                for (int i = 0; i < SNAKE_GUARD_LIMIT; i++) {
                    stringBuilder.append(DELIMITER).append(NOT_PLACED_SNAKE_GUARD_POSITION);
                }
            } else {
                int loopCounter = 0;

                for (Square[] squareArray : gameModel.getSquares()) {
                    for (Square square : squareArray) {
                        if (square.isGuarded()) {
                            stringBuilder.append(DELIMITER).append(square.getSquareNo());
                            loopCounter++;
                        }
                    }
                }

                for (int i = loopCounter; i < SNAKE_GUARD_LIMIT; i++) {
                    stringBuilder.append(DELIMITER).append(NOT_PLACED_SNAKE_GUARD_POSITION);
                }
            }
            writeToFile(stringBuilder, fileWriter);

            fileWriter.write("turnNumber" + DELIMITER + gameModel.getNumOfTurns());
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        saveFile = file;
        JOptionPane.showMessageDialog(new JFrame(), "Game Saved Successfully\n"
                        + "Your save file is " + file.getName(),
                "Game Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    private void writeToFile(StringBuilder stringBuilder, FileWriter fileWriter)
            throws IOException {
        fileWriter.write(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
        fileWriter.write(System.lineSeparator());
    }

}
