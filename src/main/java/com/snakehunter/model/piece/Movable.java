package com.snakehunter.model.piece;

import com.snakehunter.model.Square;
import com.snakehunter.model.exceptions.InvalidParamsException;

/**
 * @author WeiYi Yu
 * @date 2019-09-06
 */
public interface Movable {
    // return a message describes the movement
    String move(Square[][] squares, int steps) throws InvalidParamsException;
}
