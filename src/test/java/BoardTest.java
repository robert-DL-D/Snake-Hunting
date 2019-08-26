import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author WeiYi Yu
 * @date 2019-08-26
 */
public class BoardTest {

    public Board testBoard;
    @Before
    public void setUp() throws Exception {

        testBoard = new Board(2);
    }

    @Test
    public void getDice_before_initialization() throws Exception {
        assertEquals(true, true);

    }


    @After
    public void tearDown() throws Exception {
    }
}