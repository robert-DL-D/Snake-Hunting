package com.snakehunter.model.piece;

import com.snakehunter.model.GameModelImpl;
import com.snakehunter.model.Square;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-09-18
 */
public class SnakeMoveTest {

    Snake snake1, snake2, snake3, snake4;
    GameModelImpl gameModel;
    Square[][] squares;

    @Before
    public void setUp() throws Exception {
        snake1 = new Snake (63, 41); //left col
        snake2 = new Snake (97, 75 ); //top row
        snake3 = new Snake (25, 6); //bottom row
        snake4 = new Snake (71, 53); //right col
        gameModel = new GameModelImpl();
        initSquare();

    }

//    [col][row]
//    private static final int UP = 0;
//    private static final int DOWN = 1;
//    private static final int LEFT = 2;
//    private static final int RIGHT = 3;

    @Test
    public void snakesMovesOutOfBounds(){
        //Actions
        snake1.move(squares, 2);
        snake2.move(squares, 0);
        snake3.move(squares, 1);
        snake4.move(squares, 3);

        //Actual
        int test1 = snake1.getPosition();
        int test2 = snake2.getPosition();
        int test3 = snake3.getPosition();
        int test4 = snake4.getPosition();


        //Assertions
        assertEquals(63, test1);
        assertEquals(97, test2);
        assertEquals(25, test3);
        assertEquals(71, test4);
    }


    @Test
    public void snakesMovesGuardedSquare(){
        //Actions
        squares[3][6].setGuarded(true); //64
        squares[3][8].setGuarded(true); //84
        squares[4][3].setGuarded(true); //36
        squares[8][7].setGuarded(true); //72

        snake1.move(squares, 3);
        snake2.move(squares, 1);
        snake3.move(squares, 0);
        snake4.move(squares, 2);

        //Actual
        int test1 = snake1.getPosition();
        int test2 = snake2.getPosition();
        int test3 = snake3.getPosition();
        int test4 = snake4.getPosition();

        //Assertions
        assertEquals(63, test1);
        assertEquals(97, test2);
        assertEquals(25, test3);
        assertEquals(71, test4);
    }


    @Test
    public void snakesValidMoves(){
        //Actions
        snake1.move(squares, 0); //Up
        snake2.move(squares, 1); //Down
        snake3.move(squares, 3); //Right
        snake4.move(squares, 2); //Left

        //Actual
        int testHead1 = snake1.getPosition();
        int testTail1 = snake1.getConnectedPosition();

        int testHead2 = snake2.getPosition();
        int testTail2 = snake2.getConnectedPosition();

        int testHead3 = snake3.getPosition();
        int testTail3 = snake3.getConnectedPosition();

        int testHead4 = snake4.getPosition();
        int testTail4 = snake4.getConnectedPosition();


        //Assertions
        assertEquals(78, testHead1);
        assertEquals(60, testTail1);

        assertEquals(84, testHead2);
        assertEquals(66, testTail2);

        assertEquals(26, testHead3);
        assertEquals(7, testTail3);

        assertEquals(72, testHead4);
        assertEquals(54, testTail4);
    }


    @Test
    public void snakeMultiMove(){
        snake1.move(squares, 1); //Down
        snake1.move(squares, 3); //Right
        snake1.move(squares, 3); //Right
        snake1.move(squares, 1); //Down
        snake1.move(squares, 0); //Up

        //Actual
        int testHead = snake1.getPosition();
        int testTail = snake1.getConnectedPosition();

        //Assertions
        assertEquals(56, testHead);
        assertEquals(38, testTail);

    }


    @After
    public void tearDown() throws Exception {
    }

    public void initSquare() {
        squares = new Square[10][10];

        int x = 0; //col
        int y = 0; //row
        int increment = 1;

        for (int i = 1; i <= 100; i++) {
            squares[x][y] = new Square(i, x, y);
            if (i % 10 == 0) {
                increment = -increment;
                y++;
            } else {
                x += increment;
            }
        }
    }

}