package com.snakehunter.view;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameContract.GameView;
import com.snakehunter.controller.GameController;
import com.snakehunter.model.GameModelImpl;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Piece;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Aspen
 * @date 2019-09-23
 */
public class TurnPanelTest {

    GameModel gameModel = new GameModelImpl();
    GameView gameView = new GameViewImpl(gameModel);
    GameController gameController = new GameController(gameView, gameModel);

    @Before
    public void setUp() throws Exception {

        gameModel.setOnDataChangedListener(gameView);
        gameView.setOnViewEventListener(gameController);

        Snake s1 = new Snake(34, 27);
        Snake s2 = new Snake(74, 63);
        Ladder l1 = new Ladder(23, 46);
        Ladder l2 = new Ladder(45, 63);
        gameModel.addSnake(s1);
        gameModel.addSnake(s2);
        gameModel.addLadder(l1);
        gameModel.addLadder(l2);

        gameModel.addHumans(4);
    }

    @Test
    public void testThatGameIsReady(){
        assertTrue(gameModel.isGameReady());
    }

    @Test
    public void testThatTurnPanelIsHiddenOnStart() {
        assertEquals(false, gameView.getTurnPanel().isVisible());
    }

    @Test
    public void testThatTurnPanelHasHumanButtonsToBegin(){
        gameController.onStartClick();

        List<JButton> buttons = new ArrayList<>();

        for(Component c : gameView.getTurnPanel().getComponents()){
            System.out.println(c);
            if (c instanceof JButton){
                buttons.add((JButton)c);
            }
        }

        assertEquals(3, buttons.size());
    }

    @Test
    public void testThatCurrentPlayerWorks(){
        gameController.onStartClick();
        Player p = gameModel.getCurrentPlayer();

    }

    @Test
    public void testThatTurnNoIsCorrectlyDisplaying(){
        gameController.onStartClick();
        assertEquals("Turn No: 1/50", gameView.getTurnPanel().getTurnNoLabel().getText());

        gameController.onDiceRolled(2, 3);

        assertEquals("Turn No: 2/50", gameView.getTurnPanel().getTurnNoLabel().getText());

        gameController.onSnakeMove(2, 0);

        assertEquals("Turn No: 3/50", gameView.getTurnPanel().getTurnNoLabel().getText());

        gameController.onDiceRolled(2, 3);
        gameController.onSnakeMove(2, 0);

        assertEquals("Turn No: 5/50", gameView.getTurnPanel().getTurnNoLabel().getText());
    }

    @Test
    public void testThatMoveSnakeButtonWorks(){

    }

    @Test
    public void testThatHumanMoverChoosesCorrectHuman(){
        gameController.onStartClick();
        List<Piece> pieces = new ArrayList<>(gameModel.getCurrentPlayer().getPieceList());
        int initPosition = pieces.get(3).getPosition();
        gameController.onDiceRolled(3, 5);
        assertEquals(initPosition + 5, pieces.get(3).getPosition());

    }

    @After
    public void tearDown() throws Exception {
    }
}