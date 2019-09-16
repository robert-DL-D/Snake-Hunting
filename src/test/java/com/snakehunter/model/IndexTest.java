package com.snakehunter.model;

import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Snake;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-09-15
 */
public class IndexTest {

    Snake snake;
    Human human;
    GameModelImpl gameModel;
    Square[][] squares;

    @Before
    public void setUp() throws Exception {
        human = new Human(23, "John");
        snake = new Snake (65, 46);
        gameModel = new GameModelImpl();
         initSquare();
    }

    //[col][row]

    //snake test up
    @Test
    public void snakeUpHeadTest(){
       Square square = squares[4][6];

        int testPos = snake.moveUp(squares, square);

        assertEquals(76, testPos);
    }

    @Test
    public void snakeUpTailTest(){
        Square square = squares[4][6];

        snake.moveUp(squares, square);
        int testPos = snake.getConnectedPosition();

        assertEquals(55, testPos);
    }


    //Snake test Left
    @Test
    public void snakeLeftHeadTest(){
        Square square = squares[4][6];

        System.out.println("before: " + snake.getPosition());
        int testPos = snake.moveLeft(squares, square);

        System.out.println("After: " + snake.getPosition());
        assertEquals(64, testPos);
    }

    @Test
    public void snakeLeftTailTest(){
        Square square = squares[4][6];

        snake.moveLeft(squares, square);
        int testPos = snake.getConnectedPosition();

        assertEquals(45, testPos);
    }

    //snake test right
    @Test
    public void snakeRightHeadTest(){
        Square square = squares[4][6];

        int testPos = snake.moveRight(squares, square);

        assertEquals(66, testPos);
    }

    @Test
    public void snakeRightTailTest(){
        Square square = squares[4][6];

        snake.moveRight(squares, square);
        int testPos = snake.getConnectedPosition();

        assertEquals(47, testPos);
    }


    //snake test down
    @Test
    public void snakeDownHeadTest(){
        Square square = squares[4][6];

        int testPos = snake.moveDown(squares, square);

        assertEquals(56, testPos);
    }

    @Test
    public void snakeDownTailTest(){
        Square square = squares[4][6];

        snake.moveDown(squares, square);
        int testPos = snake.getConnectedPosition();

        assertEquals(35, testPos);
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