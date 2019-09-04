package com.snakehunter.model;

public class Snake implements Placeable {

    private Square head;
    private Square tail;
    private boolean isGuarded;

    public Snake(Square h, Square t) {
        head = h;
        tail = t;
        isGuarded = false;
    }

    public Square getHead() {
        return head;
    }

    public Square getTail() {
        return tail;
    }

    public void setHead(Square head) {
        this.head = head;
    }

    public void setTail(Square tail) {
        this.tail = tail;
    }


    @Override
    public Square getTopPos() {
        return head;
    }

    @Override
    public Square getBottomPos() {
        return tail;
    }

    @Override
    public void setTopPos(Square head) {
        this.head = head;
    }

    @Override
    public void setBottomPos(Square tail) {
        this.tail = tail;
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

    @Override
    public Square move(int i) {
        if (isGuarded = false){

        }

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
        return null;
    }
}
