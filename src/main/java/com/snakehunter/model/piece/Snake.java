package com.snakehunter.model.piece;

/**
 * @author WeiYi Yu
 * @date 2019-09-06
 */
public class Snake
        extends ConnectorPiece
        implements Movable {
    public Snake(int position, int connectedPosition) {
        super(position, connectedPosition);
    }

    @Override
    public int move(int steps) {
        return 0;
    }
}
