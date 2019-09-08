package com.snakehunter.model.piece;

/**
 * @author David Manolitsas
 * @date 2019-09-08
 */
public class Snake
        extends ConnectorPiece
        implements Movable {
    public Snake(int position, int connectedPosition) {
        super(position, connectedPosition);
    }


    //TODO snake movement, up, down , left or right 1 position
    @Override
    public int move(int steps) {
        return 0;

    }
}