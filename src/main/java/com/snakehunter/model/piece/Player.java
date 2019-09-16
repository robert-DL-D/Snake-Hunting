package com.snakehunter.model.piece;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Manolitsas
 * @date 2019-09-08
 */
public class Player
        extends Piece
        implements Movable {

    private static final int PARALYZE_TURNS = 3;

    private String name;
    private int paralyzedTurns = 0;
    private List<Ladder> ladderClimbedList;

    public Player(int position, String name) {
        super(position);
        this.name = name;

        ladderClimbedList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addLadderClimbed(Ladder ladder) {
        ladderClimbedList.add(ladder);
    }

    public boolean hasLadderClimbed(Ladder ladder) {
        return ladderClimbedList.contains(ladder);
    }

    public boolean canClimbsLadder() {
        return ladderClimbedList.size() < 3;
    }

    public boolean isParalyzed() {
        if (paralyzedTurns == 0) {
            return false;
        } else {
            paralyzedTurns--;
            return true;
        }
    }

    public void paralyze() {
        paralyzedTurns = PARALYZE_TURNS;
    }

    @Override
    public int move(int steps) {
        if (isParalyzed()){
            //if paralyzed return current position
            return getPosition();
        }
        else {
            int newPosition = getPosition() + steps;
            if (newPosition > 100){
                newPosition = 100;
            }
            setPosition(newPosition);
            return newPosition;
        }
    }
}
