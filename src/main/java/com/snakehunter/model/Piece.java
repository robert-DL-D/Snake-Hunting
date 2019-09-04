package com.snakehunter.model;

import java.util.HashMap;

public class Piece implements Placeable{

    private Square topPos;
    private Square bottomPos;
    private int paralyzedTurns;
    private HashMap <Integer, Ladder> laddersClimbed;

    Square start = new Square(1);

    public Piece(Square topPos, Square bottomPos, int paralyzedTurns){
        this.topPos = start;
        this.bottomPos = start;
        this.paralyzedTurns = 0;
        laddersClimbed = new HashMap<Integer, Ladder>();
    }

    @Override
    public Square getTopPos(){
        return topPos;
    }

    @Override
    public Square getBottomPos(){
        return bottomPos;
    }

    @Override
    public void setTopPos(Square topPos){
        this.topPos = topPos;
    }

    @Override
    public void setBottomPos(Square bottomPos){
        this.bottomPos = bottomPos;
    }

    public int getParalyzedTurns(){
        return paralyzedTurns;
    }

    public void setParalyzedTurns(){
        paralyzedTurns = 3;
    }

    public void decreaseParalyzedTurns(){
        if (paralyzedTurns > 0) {
            paralyzedTurns--;
        }
    }

    public int getLaddersClimbed(){
        int ladderCount = laddersClimbed.size();
        return ladderCount;
    }

    public void addLadderClimbed(Ladder ladder){
            laddersClimbed.put(ladder.getID(), ladder);
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

    //move from a dice roll
    @Override
    public Square move(int diceRoll){

        if (paralyzedTurns < 1){
        int newPosition = topPos.getSquareNo();

            newPosition += diceRoll;
            Square newSquare = new Square(newPosition);

            setTopPos(newSquare);
            setBottomPos(newSquare);

            return newSquare;
        }
        else {
         //piece unable to move
            return topPos;
        }
    }


    //TODO move like a knight piece
}
