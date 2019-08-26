public class Ladder {

    private int ID;
    private int bottom;
    private int top;

    public Ladder(int ID, int b, int t) {
        this.ID = ID;
        this.bottom = b;
        this.top = t;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }

    public int getID(){
        return ID;
    }
}