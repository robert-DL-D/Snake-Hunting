
public class Snake implements Placeable{

    private int head;
    private int tail;

    public Snake(int h, int t) {
        head = h;
        tail = t;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public void setTail(int tail) {
        this.tail = tail;
    }


    @Override
    public int getTopPos() {
        return 0;
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
}
