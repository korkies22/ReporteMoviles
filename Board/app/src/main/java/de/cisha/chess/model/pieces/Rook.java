// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.pieces;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.MoveDirection;
import java.io.Serializable;
import de.cisha.chess.model.Piece;

public class Rook extends Piece implements Serializable
{
    public static final Rook BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final Rook WHITE;
    private static final long serialVersionUID = -5216263788075767043L;
    
    static {
        MOVE_DIRECTIONS = new MoveDirection[] { MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT };
        WHITE = new Rook(true);
        BLACK = new Rook(false);
    }
    
    private Rook(final boolean b) {
        super(b);
    }
    
    public static Rook instanceForColor(final boolean b) {
        if (b) {
            return Rook.WHITE;
        }
        return Rook.BLACK;
    }
    
    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'R';
        }
        return 'r';
    }
    
    @Override
    public MoveDirection[] getMoveDirections(final Square square) {
        return Rook.MOVE_DIRECTIONS;
    }
    
    @Override
    public int getMoveNumberOfSquares(final Square square) {
        return 7;
    }
    
    @Override
    public String getPieceFigurineNotation() {
        return this.getPieceUnicodeChar();
    }
    
    @Override
    public String getPieceNotation() {
        return "R";
    }
    
    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2656";
        }
        return "\u265c";
    }
    
    @Override
    public MoveDirection[] getTakeDirections(final Square square) {
        return Rook.MOVE_DIRECTIONS;
    }
    
    @Override
    public int getTakeNumberOfSquares(final Square square) {
        return 7;
    }
}
