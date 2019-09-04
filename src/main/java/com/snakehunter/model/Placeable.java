package com.snakehunter.model;

interface Placeable {

    Square topPos = null;
    Square bottomPos = null;
    int length = 0;
    int maxLength = 0;
    int minLength = 0;

    Square getTopPos();
    Square getBottomPos();

    void setTopPos(Square i);
    void setBottomPos(Square i);

    int getLength();
    int getMaxLength();
    int getMinLength();

    void setMaxLength(int i);
    void setMinLength(int i);

    Square move(int i);

}
