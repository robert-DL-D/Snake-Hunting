import com.snakehunter.model.Ladder;
import com.snakehunter.model.Piece;
import com.snakehunter.model.Snake;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-08-26
 */
public class SnakeLadderPieceTest {

    Piece piece;
    Snake snake;
    Ladder ladder;
    Ladder ladder2;
    Ladder ladder3;


    @Before
    public void setUp() throws Exception {
        piece = new Piece();
        snake = new Snake(14, 3);
        ladder = new Ladder(1, 4, 16);
        ladder2 = new Ladder(2, 22, 36);
        ladder3 = new Ladder(3, 67, 88);

        System.out.println("Before");
    }


    @Test
    public void test1(){
        piece.addLadderClimbed(ladder);
        int result = piece.getLaddersClimbed();

        assertEquals(1, result);
        System.out.println("test 1");

    }


    @Test
    public void test2(){
        piece.addLadderClimbed(ladder);
        piece.addLadderClimbed(ladder);

        int result = piece.getLaddersClimbed();

        assertEquals(1, result);
        System.out.println("test 2");
    }


    @Test
    public void test3() {
        piece.addLadderClimbed(ladder);
        piece.addLadderClimbed(ladder2);

        int result = piece.getLaddersClimbed();

        assertEquals(2, result);
        System.out.println("test 3");
    }


    @Test
    public void test4() {
       piece.addLadderClimbed(ladder);
       piece.addLadderClimbed(ladder2);
       piece.addLadderClimbed(ladder2);

        int result = piece.getLaddersClimbed();

        assertEquals(2, result);
        System.out.println("test 4");
    }


    @Test
    public void test5() {
        piece.addLadderClimbed(ladder);
        piece.addLadderClimbed(ladder2);
        piece.addLadderClimbed(ladder3);

        int result = piece.getLaddersClimbed();

        assertEquals(3, result);
        System.out.println("test 5");
    }


    @After
    public void tearDown() throws Exception {
    }
}