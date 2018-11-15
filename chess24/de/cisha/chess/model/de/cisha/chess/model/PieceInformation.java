/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import java.io.Serializable;

public class PieceInformation
implements Serializable {
    private static final long serialVersionUID = 861135994045237134L;
    private Piece _piece;
    private Square _square;

    public PieceInformation(Piece piece, Square square) {
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
