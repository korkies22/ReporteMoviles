// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.pieces.special;

import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.Piece;

public class NullPiece extends Piece
{
    public static final NullPiece BLACK;
    public static final NullPiece WHITE;
    
    static {
        WHITE = new NullPiece(true);
        BLACK = new NullPiece(false);
    }
    
    protected NullPiece(final boolean b) {
        super(b);
    }
    
    public static NullPiece instanceForColor(final boolean b) {
        if (b) {
            return NullPiece.WHITE;
        }
        return NullPiece.BLACK;
    }
    
    @Override
    public char getFENChar() {
        return 'z';
    }
    
    @Override
    public MoveDirection[] getMoveDirections(final Square square) {
        return new MoveDirection[0];
    }
    
    @Override
    public int getMoveNumberOfSquares(final Square square) {
        return 0;
    }
    
    @Override
    public String getPieceFigurineNotation() {
        return "";
    }
    
    @Override
    public String getPieceNotation() {
        return "";
    }
    
    @Override
    public String getPieceUnicodeChar() {
        return "";
    }
    
    @Override
    public MoveDirection[] getTakeDirections(final Square square) {
        return new MoveDirection[0];
    }
    
    @Override
    public int getTakeNumberOfSquares(final Square square) {
        return 0;
    }
}
