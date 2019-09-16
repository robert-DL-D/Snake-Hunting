package com.snakehunter.model.piece;

import com.snakehunter.model.Square;

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

    @Override
    public int move(Square[][] squares, int steps) {
        return 0;
    }
}