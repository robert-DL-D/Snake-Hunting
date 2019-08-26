import java.util.HashMap;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class SLGameNew {

    private static final int MAX_GUARDS = 3;

    private boolean isGameOver = false;
    private GameStage gameStage;
    private Board board;
    private Dice dice;

    private Player currentPlayer;
    private HashMap<Integer, Player> playerMap;

    private int numOfGuards = 0;
    private int numOfTurns = 0;

    public SLGameNew(Board board, Dice dice, HashMap<Integer, Player> playerMap) {
        this.board = board;
        this.dice = dice;
        this.playerMap = playerMap;

        setGameStage(GameStage.INITIAL);
    }

    public void start() {
        while (!isGameOver()) {
            nextTurn();
        }
        gameOver();
    }

    void gameOver() {
        // TODO: print the info of game
    }

    void nextTurn() {
        numOfTurns++;
        currentPlayer = getPlayer(numOfTurns);
    }

    Player getPlayer(int numOfTurns) {
        int index = numOfTurns % playerMap.size();
        return playerMap.get(index);
    }

    boolean addGuards(int position) {
        if (numOfGuards == MAX_GUARDS) {
            return false;
        }

        // TODO:
        //  1. check if position available
        //  2. put the guard into the corresponding position
        numOfGuards++;
        return true;
    }

    void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    GameStage getGameStage() {
        return gameStage;
    }

    //region getter/setter
    public static int getMaxGuards() {
        return MAX_GUARDS;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getNumOfGuards() {
        return numOfGuards;
    }

    public void setNumOfGuards(int numOfGuards) {
        this.numOfGuards = numOfGuards;
    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    public void setNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
    }

    public boolean isGameOver() {
        // TODO: check every conditions which can finish the game
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
    //endregion
}
