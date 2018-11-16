// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.io.Serializable;

public class PieceInformation implements Serializable
{
    private static final long serialVersionUID = 861135994045237134L;
    private Piece _piece;
    private Square _square;
    
    public PieceInformation(final Piece piece, final Square square) {
        this._piece = piece;
        this._square = square;
    }
    
    public Piece getPiece() {
        return this._piece;
    }
    
    public Square getSquare() {
        return this._square;
    }
}
