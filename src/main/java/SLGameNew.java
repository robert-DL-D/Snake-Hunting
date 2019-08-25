import java.util.HashMap;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class SLGameNew {

    private static final int MAX_GUARDS = 3;

    private GameStage gameStage;
    private Board board;
    private Dice dice;

    private Player currentPlayer;
    private HashMap<Integer, Player> playerMap;

    private int numOfGuards = 0;
    private int numOfTurns = 0;

    public static void main(String[] args) {
        SLGameNew slGame = new SLGameNew();
        slGame.execute();
    }

    public void execute() {
        setupVariables();
    }

    private void setupVariables() {
        setGameStage(GameStage.INITIAL);
        setupPlayers(2);

        dice = new Dice();
        board = new Board();
    }

    // Fixed to 2 players for now
    private void setupPlayers(int numOfPlayers) {
        playerMap = new HashMap<>();

        for (int i = 1; i <= numOfPlayers; i++) {
            Player player = new Player();
            playerMap.put(i % numOfPlayers, player);
        }
    }

    private void nextTurn() {
        numOfTurns++;
        currentPlayer = getNextPlayer(numOfTurns);
    }

    private Player getNextPlayer(int numOfTurns) {
        int index = numOfTurns % playerMap.size();
        return playerMap.get(index);
    }

    private boolean addGuards(int position) {
        if (numOfGuards == MAX_GUARDS) {
            return false;
        }

        // TODO:
        //  1. check if position available
        //  2. put the guard into the corresponding position
        numOfGuards++;
        return true;
    }

    private void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }
}
