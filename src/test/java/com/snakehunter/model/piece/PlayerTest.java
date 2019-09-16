package com.snakehunter.model.piece;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-09-08
 */
//public class PlayerTest {
//
//    Player player;
//    DiceView diceView;
//
//    @Before
//    public void setUp() throws Exception {
//        player = new Player(1, "John");
//        diceView = new DiceView();
//    }
//
//    //test player movement
//    @Test
//    public void test1(){
//        player.move(5);
//        assertEquals(6, player.getPosition());
//
//    }
//
//    //test player movement multiple times
//    @Test
//    public void test2(){
//        for(int i = 0; i < 7; i ++){
//            player.move(3);
//        }
//
//        assertEquals(22, player.getPosition());
//    }
//
//    //test player movement when paralyzed
//    @Test
//    public void test3(){
//        player.paralyze();
//
//        diceView.roll();
//        player.move(diceView.getLastNum());
//
//        assertEquals(1, player.getPosition());
//    }
//
//    //player paralyzed
//    //player should move after being paralyzed for 3 turns
//    @Test
//    public void test4(){
//        player.paralyze();
//
//        for (int i = 0; i < 4; i++){
//            player.move(3);
//        }
//        assertEquals(4, player.getPosition());
//    }
//
//    //test player movement from dice roll
//    @Test
//    public void test5(){
//        diceView.roll();
//        int num = diceView.getLastNum();
//        int expected = num + player.getPosition();
//
//        player.move(num);
//
//        System.out.println("dice: " + num);
//        assertEquals(expected, player.getPosition());
//    }
//
//    //test player movement from multiple dice rolls
//    @Test
//    public void test6(){
//        int total = 1;
//
//        for (int i = 0; i < 10; i++){
//            diceView.roll();
//            player.move(diceView.getLastNum());
//
//            total += diceView.getLastNum();
//        }
//
//        assertEquals(total, player.getPosition());
//    }
//
//
//    @After
//    public void tearDown() throws Exception {
//    }
//}