// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.List;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.MutablePosition;

public class ModifyablePosition extends MutablePosition
{
    public ModifyablePosition() {
    }
    
    public ModifyablePosition(final FEN fen) {
        super(fen);
    }
    
    public ModifyablePosition(final Position position) {
        super(position.getFEN());
    }
    
    public ModifyablePosition(final List<PieceInformation> list) {
        super(list);
    }
    
    public void setActiveColor(final boolean activeColor) {
        this._activeColor = activeColor;
    }
    
    public void setBlackKingSideCastling(final boolean blackCanCastleShort) {
        this._blackCanCastleShort = blackCanCastleShort;
    }
    
    public void setBlackQueenSideCastling(final boolean blackCanCastleLong) {
        this._blackCanCastleLong = blackCanCastleLong;
    }
    
    public void setEnPassantColumn(final int epColumn) {
        this._epColumn = epColumn;
    }
    
    public void setMoveCounter(final int moveCounter) {
        this._moveCounter = moveCounter;
    }
    
    public void setPiece(final Piece piece, final Square square) {
        super.setPiece(piece, square);
    }
    
    public void setWhiteKingSideCastling(final boolean whiteCanCastleShort) {
        this._whiteCanCastleShort = whiteCanCastleShort;
    }
    
    public void setWhiteQueenSideCastling(final boolean whiteCanCastleLong) {
        this._whiteCanCastleLong = whiteCanCastleLong;
    }
}
