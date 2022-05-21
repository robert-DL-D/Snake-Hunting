package com.snakehunter.model.piece;

public class ConnectorPiece
        extends Piece {

    private int connectedPosition;

    ConnectorPiece(int position, int connectedPosition) {
        super(position);
        this.connectedPosition = connectedPosition;
    }

    public int getConnectedPosition() {
        return connectedPosition;
    }

    public void setConnectedPosition(int connectedPosition) {
        this.connectedPosition = connectedPosition;
    }

    int getLength() {
        return Math.abs(connectedPosition - getPosition());
    }

}
