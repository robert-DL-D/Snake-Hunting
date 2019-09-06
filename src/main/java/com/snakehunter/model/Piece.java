package com.snakehunter.model;

import java.util.HashMap;

/**
 * @author David Manolitsas
 * @project SnakeHunting
 * @date 2019-08-26
 */

public class Piece implements Placeable{

    private int topPos;
    private int bottomPos;
    private int paralyzedTurns;
    private HashMap <Integer, Ladder> laddersClimbed;


    public Piece(int topPos, int bottomPos, int paralyzedTurns){
        this.topPos = 1;
        this.bottomPos = 1;
        this.paralyzedTurns = 0;
        laddersClimbed = new HashMap<Integer, Ladder>();
    }

    @Override
    public int getTopPos(){
        return topPos;
    }

    @Override
    public int getBottomPos(){
        return bottomPos;
    }

    @Override
    public void setTopPos(int topPos){
        this.topPos = topPos;
    }

    @Override
    public void setBottomPos(int bottomPos){
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
