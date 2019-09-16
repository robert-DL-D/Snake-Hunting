package com.snakehunter.model.piece;

import com.snakehunter.model.Square;

/**
 * @author WeiYi Yu
 * @date 2019-09-06
 */
public interface Movable {
    // return the new position
    int move(Square[][] squares, int steps);
}
