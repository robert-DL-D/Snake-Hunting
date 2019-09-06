/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-08-26
 */

//public class PieceTest {
//
//    Piece piece;
//    Ladder ladder;
//    Ladder ladder2;
//    Ladder ladder3;
//    Dice dice;
//
//    Square start = new Square(1);
//    Square square1 = new Square(7);
//    Square square2 = new Square(18);
//
//    Square square3 = new Square(22);
//    Square square4 = new Square(36);
//
//    Square square5 = new Square(45);
//    Square square6 = new Square(60);
//
//    @Before
//    public void setUp() throws Exception {
//        piece = new Piece(start, start, 0);
//        ladder = new Ladder(1, square1, square2);
//        ladder2 = new Ladder(2, square3, square4);
//        ladder3 = new Ladder(3,square5, square6);
//        dice = new Dice();
//
//    }
//
//
//    @Test
//    public void test1(){
//        piece.addLadderClimbed(ladder);
//        int result = piece.getLaddersClimbed();
//
//        assertEquals(1, result);
//
//    }
//
//
//    @Test
//    public void test2(){
//        piece.addLadderClimbed(ladder);
//        piece.addLadderClimbed(ladder);
//
//        int result = piece.getLaddersClimbed();
//
//        assertEquals(1, result);
//    }
//
//
//    @Test
//    public void test3() {
//        piece.addLadderClimbed(ladder);
//        piece.addLadderClimbed(ladder2);
//
//        int result = piece.getLaddersClimbed();
//
//        assertEquals(2, result);
//    }
//
//
//    @Test
//    public void test4() {
//       piece.addLadderClimbed(ladder);
//       piece.addLadderClimbed(ladder2);
//       piece.addLadderClimbed(ladder2);
//
//        int result = piece.getLaddersClimbed();
//
//        assertEquals(2, result);
//    }
//
//
//    @Test
//    public void test5() {
//        piece.addLadderClimbed(ladder);
//        piece.addLadderClimbed(ladder2);
//        piece.addLadderClimbed(ladder3);
//
//        int result = piece.getLaddersClimbed();
//
//        assertEquals(3, result);
//    }
//
//
//    //Test when piece is paralyzed it can not move
//    @Test
//    public void test6(){
//        piece.setParalyzedTurns();
//        dice.roll();
//        int roll = dice.getLastNum();
//        piece.move(roll);
//
//        Square position = piece.getTopPos();
//
//        //when piece is paralyzed, piece should not move
//        assertEquals(start, position);
//    }
//
//    //test to see if a piece moves correctly from a dice roll
//    @Test
//    public void test7(){
//        dice.roll();
//        int roll = dice.getLastNum();
//        piece.move(roll);
//
//        Square expectedSquare = new Square(dice.getLastNum() + start.getSquareNo());
//
//        assertEquals(expectedSquare.getSquareNo(), piece.getTopPos().getSquareNo());
//
//    }
//
//    //Test multiple rolls on a piece
//    @Test
//    public void test8(){
//        int total = 0;
//        for (int i = 0; i < 10; i++){
//            dice.roll();
//            int roll = dice.getLastNum();
//
//            piece.move(roll);
//            total += dice.getLastNum();
//        }
//
//        Square expectedSquare = new Square(start.getSquareNo() + total);
//
//        assertEquals(expectedSquare.getSquareNo(), piece.getTopPos().getSquareNo());
//    }
//
//    //test a move from dice roll followed by a move when piece is paralyzed
//    @Test
//    public void test9(){
//        dice.roll();
//        int roll = dice.getLastNum();
//        piece.move(roll);
//        Square expectedSquare = new Square(roll + start.getSquareNo());
//
//        //piece becomes paralyzed
//        piece.setParalyzedTurns();
//        dice.roll();
//        roll = dice.getLastNum();
//        piece.move(roll);
//
//        assertEquals(expectedSquare.getSquareNo(), piece.getTopPos().getSquareNo());
//
//    }
//
//    //test multiple dice rolls on a paralyzed piece
//    @Test
//    public void test10(){
//       piece.setParalyzedTurns();
//
//        for (int i = 0; i < 4; i++){
//           piece.move(4);
//           piece.decreaseParalyzedTurns();
//       }
//
//        assertEquals(5, piece.getTopPos().getSquareNo());
//
//    }
//
//
//    @After
//    public void tearDown() throws Exception {
//    }
//}