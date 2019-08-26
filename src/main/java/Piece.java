import java.util.HashMap;

class Piece {

    private int paralyzedTurns;
    private HashMap <Integer, Ladder> laddersClimbed;

    public Piece(){
        laddersClimbed = new HashMap<Integer, Ladder>();
    }

    public int getParalyzedTurns(){
        return paralyzedTurns;
    }

    public void setParalyzedTurns(){
        if (this.paralyzedTurns > 0) {
            this.paralyzedTurns -= 1;
        }
    }

    public int getLaddersClimbed(){
        int ladderCount = laddersClimbed.size();
        return ladderCount;
    }

    public void addLadderClimbed(Ladder ladder){
        if (laddersClimbed.containsKey(ladder.getID())){
            //do nothing
        } else {
            laddersClimbed.put(ladder.getID(), ladder);
        }
    }

}
