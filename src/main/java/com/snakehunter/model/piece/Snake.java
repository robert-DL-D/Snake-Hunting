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
//        if(isGuarded = false){
//
//
//        }
//        else {
//            return getHead();
//        }

        //TODO snake movement using square instead of int
//      //can move up, down, left or right one position
//
//
//        int candidateMove; //need to be square?
//
//
//            for(int currentCandidate : potentialMoves) {
//               // candidateMove = this.topPos + currentCandidate;
//
//                if (true) {
//                    /*
//                     *if candidateMove is a valid tile
//                     * i.e. within the constraints of the board
//                     * Square candidateSquare = getSquare(candidateMove);
//                     *
//                     */
//                    if(true){
//                        /*
//                         * if candidateSquare.isOccupied = false;
//                         * add it as a legal move
//                         */
//                    }
//                    else {
//                        /*
//                         * else if candidateSquare.isOccupied = true;
//                         * check to see if it is a snake head or tail or another piece
//                         * if it is a snake head or snake tail, it is not a valid move
//                         * if it is a piece, it is a legal move
//                         * piece either gets eaten or is moved to the tail position
//                         */
//                    }
//                }
//            }
//            //update tail position
//        }
//    }
        return 0;
    }
}
