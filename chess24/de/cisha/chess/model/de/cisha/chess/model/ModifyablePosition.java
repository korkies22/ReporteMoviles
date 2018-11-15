/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.position.Position;
import java.util.List;

public class ModifyablePosition
extends MutablePosition {
    public ModifyablePosition() {
    }

    public ModifyablePosition(FEN fEN) {
        super(fEN);
    }

    public ModifyablePosition(Position position) {
        super(position.getFEN());
    }

    public ModifyablePosition(List<PieceInformation> list) {
        super(list);
    }

    public void setActiveColor(boolean bl) {
        this._activeColor = bl;
    }

    public void setBlackKingSideCastling(boolean bl) {
        this._blackCanCastleShort = bl;
    }

    public void setBlackQueenSideCastling(boolean bl) {
        this._blackCanCastleLong = bl;
    }

    public void setEnPassantColumn(int n) {
        this._epColumn = n;
    }

    public void setMoveCounter(int n) {
        this._moveCounter = n;
    }

    @Override
    public void setPiece(Piece piece, Square square) {
        super.setPiece(piece, square);
    }

    public void setWhiteKingSideCastling(boolean bl) {
        this._whiteCanCastleShort = bl;
    }

    public void setWhiteQueenSideCastling(boolean bl) {
        this._whiteCanCastleLong = bl;
    }
}
