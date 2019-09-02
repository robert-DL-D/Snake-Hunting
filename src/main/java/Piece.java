import java.util.HashMap;

class Piece implements Placeable{

    //hello
    private int paralyzedTurns = 3;
    private HashMap <Integer, Ladder> laddersClimbed;

    public Piece(){
        laddersClimbed = new HashMap<Integer, Ladder>();
    }

    public int getParalyzedTurns(){
        return paralyzedTurns;
    }

    public void setParalyzedTurns(){
        this.paralyzedTurns = 3;
    }

    public void decreaseParalyzedTurns(){
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

    @Override
    public int getBottomPos() {
        return 0;
    }

    @Override
    public void setTopPos(int i) {

    }

    @Override
    public void setBottomPos(int i) {

    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public int getMaxLength() {
        return 0;
    }

    @Override
    public int getMinLength() {
        return 0;
    }

    @Override
    public void setMaxLength(int i) {

    }

    @Override
    public void setMinLength(int i) {

    }

    @Override
    public void move(int i) {

    }

    public int getTopPos(){
        return 0;
    }
}
