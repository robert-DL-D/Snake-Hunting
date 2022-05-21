package com.snakehunter.view;

import com.snakehunter.GameStage;
import com.snakehunter.controller.GameController;
import com.snakehunter.controller.GameNotReadyException;
import com.snakehunter.model.GameModel;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GameView
        extends JFrame
        implements ActionListener {

    private final GameModel gameModel;

    private GameController gameController;
    private final SettingPanel settingPanel;
    private final TurnPanel turnPanel;
    private final GameOverPanel gameOverPanel;
    private final MessagePanel messagePanel;

    public GameView(GameModel gameModel) {
        super("Snakes n Ladders!");
        this.gameModel = gameModel;

        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 900);

        Container contentPane = getContentPane();

        BoardView boardView = new BoardView(gameModel);
        boardView.setLocation(0, 0);
        contentPane.add(boardView);

        settingPanel = new SettingPanel(this);
        settingPanel.setLocation(450, 20);
        contentPane.add(settingPanel);

        turnPanel = new TurnPanel(this, gameModel, this.getBackground());
        turnPanel.setLocation(450, 20);
        turnPanel.setVisible(false);
        turnPanel.setBorder(new LineBorder(Color.BLACK));
        contentPane.add(turnPanel);

        gameOverPanel = new GameOverPanel(this, gameModel);
        gameOverPanel.setLocation(450, 20);
        gameOverPanel.setVisible(false);
        contentPane.add(gameOverPanel);

        JLabel snakeJLabel = new JLabel("Snake List");
        snakeJLabel.setSize(120, 25);
        snakeJLabel.setLocation(80, 480);
        contentPane.add(snakeJLabel);

        String[] snakesArray = new String[2];
        snakesArray[0] = "Snake 1 @ 23-10";
        snakesArray[1] = "Snake 2 @ 54-34";

        JList<String> snakeJList = new JList<>();
        snakeJList.setBorder(new LineBorder(Color.BLACK));
        snakeJList.setLocation(20, 550);
        snakeJList.setListData(snakesArray);
        contentPane.add(snakeJList);

        JLabel ladderJLabel = new JLabel("Ladder List");
        ladderJLabel.setSize(120, 25);
        ladderJLabel.setLocation(200, 480);
        contentPane.add(ladderJLabel);

        JList<String> ladderJList = new JList<>();
        ladderJList.setBorder(new LineBorder(Color.BLACK));
        ladderJList.setLocation(40, 550);
        contentPane.add(ladderJList);

        Container messagePane = getContentPane();

        messagePanel = new MessagePanel();
        messagePanel.setLocation(620, 20);
        messagePanel.setVisible(true);
        messagePanel.setBorder(new LineBorder(Color.BLACK));
        messagePane.add(messagePanel);

        revalidate();
        repaint();
        setVisible(true);
    }

    //region interaction

    public void rollTheDice() {
        turnPanel.getDiceView().roll();
        turnPanel.enablePieceButtons();
    }

    private void showBuilder(String string) {
        final String addSnake = "Add Snake";
        final String addLadder = "Add Ladder";

        String addConnector = null;

        if (string.equals(addSnake)) {
            addConnector = addSnake;
        } else if (string.equals(addLadder)) {
            addConnector = addLadder;
        }

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        pane.add(new JLabel("Head position"));
        pane.add(new LimitTextField());

        pane.add(new JLabel("Tail position"));
        pane.add(new LimitTextField());

        if (JOptionPane.showConfirmDialog(this, pane, addConnector, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION) {

            try {
                int head = Integer.parseInt(new LimitTextField().getText());
                int tail = Integer.parseInt(new LimitTextField().getText());

                if (string.equals(addSnake)) {
                    gameController.onSnakeBuilt(new Snake(head, tail));
                } else if (string.equals(addLadder)) {
                    gameController.onLadderBuilt(new Ladder(head, tail));
                }

            } catch (NumberFormatException nfe) {
            }
        }
    }

    /*public void showHumanBuilder() {
        if (Main.isDebugMode()) {
            int numOfHumans;

            try {
                numOfHumans = Integer.parseInt(JOptionPane.showInputDialog("How many players? (2 ~ 4)"));
            } catch (NumberFormatException e) {
                if (listener != null) {
                    listener.onNumOfHumansEntered(-1);
                }
                return;
            }

            if (listener != null) {
                listener.onNumOfHumansEntered(numOfHumans);
            }
        } else {
            if (listener != null) {
                listener.onNumOfHumansEntered(4);
            }
        }

    }*/

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showTurnPanel() {
        turnPanel.setVisible(true);
    }

    private void hideTurnPanel() {
        turnPanel.setVisible(false);
    }

    public void updateTurnNo(int turnNo) {
        turnPanel.updateTurnNo(turnNo);
    }

    private void updateParalyzedTurn() {
        turnPanel.updateParalyzedTurn();
    }

    public void updateStage(GameStage s) {
        turnPanel.updateStage(s);
    }

    public void updateGuardNo() {
        turnPanel.updateGuardNo();
    }

    public void hideSettingPanel() {
        settingPanel.setVisible(false);
    }

    public void showSettingPanel() {
        settingPanel.setVisible(true);
    }

    public void hideGameOverPanel() {
    }

    private void showGameOverPanel(Player p) {
        gameOverPanel.setVisible(true);
        gameOverPanel.updateWinner(p);
    }

    private void showGuardPlacer() {
        try {
            int squareNo;
            do {
                squareNo = Integer.parseInt(JOptionPane.showInputDialog("Enter square number to place guard on:"));
            } while (squareNo == 1);

            gameModel.addGuard(squareNo);
            turnPanel.getDiceView().setEnabled(true);
        } catch (Exception e) {
            showErrorDialog("Enter a valid square number");
        }
    }
    //endregion

    //region GameModel interaction

    public void onAddSnakeFailed(String errorMessage) {
        showErrorDialog(errorMessage);
    }

    public void onAddLadderFailed(String errorMessage) {
        showErrorDialog(errorMessage);
    }

    private void showClimbedLadder() {
        turnPanel.showClimbedLadder();
    }

    public void onHumanMoved(String message) {
        //showInfoDialog(message);
        messagePanel.displayMessage(message);
    }

    public void onExceedMaxNumOfGuards() {
        showErrorDialog("Cannot place more than 3 guards.");
    }

    public void onExceedMaxPosition() {
        showErrorDialog("Exceed max square! Stay at the same square.");
    }

    public void onLadderClimbedThresholdException() {
        showErrorDialog("Haven't climbed 3 ladders yet, go back to Square 1.");
    }

    public void onNextTurn(Player player) {
        int numOfTurns = gameModel.getNumOfTurns();

        updateTurnNo(numOfTurns);
        if (numOfTurns % 2 == 1) {
            messagePanel.displayMessage("\nTurn " + (numOfTurns / 2 + 1));
        }

        if (player.isSnake()) {
            turnPanel.hidePieceButtons();
            updateParalyzedTurn();
        }
    }

    public void onGameOver(Player winner) {
        hideTurnPanel();
        showGameOverPanel(winner);
    }

    public void onNumOfHumansEnteredError() {
        showErrorDialog("Please enter a number between 1 ~ 4.");
    }
    //endregion

    //region Getters
    public TurnPanel getTurnPanel() {
        return turnPanel;
    }
    //endregion

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add Snake":
                showBuilder(e.getActionCommand());
                break;
            case "Add Ladder":
                showBuilder(e.getActionCommand());
                break;
            /*case "Add Humans":
                listener.onAddHumansClick();
                break;*/
            case "Start":
                try {
                    gameController.onStartClick();
                } catch (GameNotReadyException gnre) {
                    JOptionPane.showMessageDialog(this, "Game not yet ready: " + gnre.getMessage());
                }
                break;
            case "Add 5 Random Snakes":
                gameController.onRandomSnakeClick();
                break;
            case "Add 5 Random Ladders":
                gameController.onRandomLadderClick();
                break;
            case "Add 5 Random S&L":
                gameController.onRandomLadderClick();
                gameController.onRandomSnakeClick();
                break;
            case "Up":
                gameController.onSnakeMove(turnPanel.getSnakeJListSelectedItem(), 0);
                break;
            case "Down":
                gameController.onSnakeMove(turnPanel.getSnakeJListSelectedItem(), 1);
                break;
            case "Left":
                gameController.onSnakeMove(turnPanel.getSnakeJListSelectedItem(), 2);
                break;
            case "Right":
                gameController.onSnakeMove(turnPanel.getSnakeJListSelectedItem(), 3);
                break;
            case "Place Guard":
                showGuardPlacer();
                break;
            case "Check Ladder":
                showClimbedLadder();
                break;
            case "Save Game":
                gameController.onSaveClick();
                break;
            case "Load Game":
                gameController.onLoadClick();
                break;
            case "1":
                turnPanel.getDiceView().moveSelectedPiece(1);
                break;
            case "2":
                turnPanel.getDiceView().moveSelectedPiece(2);
                break;
            case "3":
                turnPanel.getDiceView().moveSelectedPiece(3);
                break;
            case "4":
                turnPanel.getDiceView().moveSelectedPiece(4);
                break;
            case "Show Valid Moves":
                turnPanel.showValidMoves(turnPanel.getHumanListItem());
                break;

            case "Move Human":
                if (turnPanel.getMovesSquareItem() >= 0) {
                    gameController.onMoveKnight(turnPanel.getHumanListItem(), turnPanel.getMovesSquareItem());
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a square to move to");
                }
                break;

            case "Play Again":
                break;

            default:
                break;
        }
    }

    public void setOnViewEventListener(GameController gameController) {
        this.gameController = gameController;
    }

    public void setOnDiceViewEventListener(GameController gameController) {
        turnPanel.setOnDiceViewEventListener(gameController);
    }
}
