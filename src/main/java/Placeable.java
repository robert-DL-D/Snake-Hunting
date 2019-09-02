
interface Placeable {

//    Square topPos = 0;
//    Square bottomPos = 0;
    int length = 0;
    int maxLength = 0;
    int minLength = 0;

    int getTopPos();
    int getBottomPos();

    void setTopPos(int i);
    void setBottomPos(int i);
    int getLength();

    int getMaxLength();
    int getMinLength();

    void setMaxLength(int i);
    void setMinLength(int i);

    void move(int i);


}
