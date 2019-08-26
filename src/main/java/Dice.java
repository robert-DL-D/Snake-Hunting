import java.util.Random;

/**
 * @author WeiYi Yu
 * @date 2019-08-24
 */
public class Dice {

//    private static final int DICE_LENGTH = 45;
//    private static final int DOT_LENGTH = 5;

    public static final int DICE_NUM_MIN = 1;
    public static final int DICE_NUM_MAX = 6;

    private Random random;
    private int lastNum = 1;
    private Board board;

    public Dice() {
        random = new Random();
    }

    public int roll() {
        lastNum = getRandomNumber(DICE_NUM_MIN, DICE_NUM_MAX);
        return lastNum;
    }

    // test method: return a fix number
    public int roll(int num) {
        lastNum = num;
        return lastNum;
    }

    public int getLastNum() {
        return lastNum;
    }

    //    public Dice(Board board) {
//        random = new Random();
//        this.board = board;
//    }

//    public int roll() {
//        int num;
//
//        // fake dice animation
//        for (int i = 1; i <= 20; i++) {
//            do {
//                num = getRandomNumber(1, 6);
//            } while (lastNum == num);
//            lastNum = num;
//            board.repaint();
//
//            // This creates the fake dice roll animation
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//                System.out.println("Dice roll exception " + e);
//            }
//        }
//        board.setPiece(1, board.getP1Location() + lastNum);
//
//        return lastNum;
//    }

//    public void draw(Graphics graphics) {
//        graphics.setColor(Color.BLACK);
//        graphics.fill3DRect(500, 400, DICE_LENGTH, DICE_LENGTH, false);
//        graphics.setColor(Color.WHITE);
//
//        if (lastNum == 1) {
//            graphics.fillOval(520, 420, DOT_LENGTH, DOT_LENGTH);
//        } else if (lastNum == 2) {
//            graphics.fillOval(510, 420, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 420, DOT_LENGTH, DOT_LENGTH);
//        } else if (lastNum == 3) {
//            graphics.fillOval(520, 410, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(510, 430, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 430, DOT_LENGTH, DOT_LENGTH);
//        } else if (lastNum == 4) {
//            graphics.fillOval(510, 410, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 410, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(510, 430, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 430, DOT_LENGTH, DOT_LENGTH);
//        } else if (lastNum == 5) {
//            graphics.fillOval(510, 410, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 410, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(520, 420, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(510, 430, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 430, DOT_LENGTH, DOT_LENGTH);
//        } else if (lastNum == 6) {
//            graphics.fillOval(510, 410, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 410, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(510, 420, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 420, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(510, 430, DOT_LENGTH, DOT_LENGTH);
//            graphics.fillOval(530, 430, DOT_LENGTH, DOT_LENGTH);
//        }
//    }

    private int getRandomNumber(int min, int max) {
        return random.nextInt(max) + min;
    }
}