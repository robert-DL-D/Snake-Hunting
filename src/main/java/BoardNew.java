import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2019-08-26
 */
class BoardNew {

    List<Snake> snakes = new ArrayList<Snake>();
    List<Ladder> ladders = new ArrayList<Ladder>();
    List<Piece> pieces = new ArrayList<Piece>();
    int width;
    int height;
    Square[][] squares;

    public BoardNew(int width, int height){
        squares = new Square[width][height];
    }

    public Square[][] getSquares(){
       return squares;
    }
}
