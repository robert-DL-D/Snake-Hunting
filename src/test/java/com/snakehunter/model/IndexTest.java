//package com.snakehunter.model;
//
//import com.snakehunter.GameContract.GameModel;
//import com.snakehunter.model.piece.Player;
//import com.snakehunter.model.piece.Snake;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * @author David Manolitsas
// * @project SnakeHunting
// * @date 2019-09-15
// */
//public class IndexTest {
//
//    Snake snake;
//    Player player;
//    GameModelImpl gameModel;
//
//    @Before
//    public void setUp() throws Exception {
//        player = new Player(23,"John");
//        snake = new Snake (24, 5);
//        gameModel = new GameModelImpl();
//    }
//
//    //position 5 should be [9][4]
//
//    //Testing getting column index on player
//    @Test
//    public void rowTest(){
//    int testRow = gameModel.getRow(gameModel.getSquare(player.getPosition()));
//
//    assertEquals(2, testRow);
//    }
//
//    //Testing getting column index on player
//    @Test
//    public void colTest(){
//        int testRow = gameModel.getCol(gameModel.getSquare(player.getPosition()));
//
//        assertEquals(2, testRow);
//    }
//
//    @Test
//    public void snakeUpTest(){
//        int testPos = snake.moveUp();
//
//        assertEquals(37,testPos);
//    }
//
//    @Test
//    public void snakeUpTest2(){
//        snake.moveUp();
//        int testPos = snake.getConnectedPosition();
//        assertEquals(16 ,testPos);
//    }
//
//
//    @After
//    public void tearDown() throws Exception {
//    }
//}