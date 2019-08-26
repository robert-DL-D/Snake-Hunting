import java.util.HashMap;

/**
 * @author WeiYi Yu
 * @date 2019-08-25
 */
public class Main {

    public static void main(String[] args) {
        // TODO:
        //  1. setup board (including any objects position)
        //  2. add players
        // Start the game after initialize finished.
        SLGameNew slGame = new SLGameNew(null, new Dice(), getPlayers());
        slGame.start();
    }

    // Fixed to 2 players for now
    private static HashMap<Integer, Player> getPlayers() {
        int numOfPlayers = 2;
        HashMap<Integer, Player> players = new HashMap<>();

        for (int i = 1; i <= numOfPlayers; i++) {
            Player player = new Player("Player " + i);
            players.put(i % numOfPlayers, player);
        }
        return players;
    }
}
