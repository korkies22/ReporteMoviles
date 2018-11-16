// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.pieces;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.MoveDirection;
import java.io.Serializable;
import de.cisha.chess.model.Piece;

public class Knight extends Piece implements Serializable
{
    public static final Knight BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final Knight WHITE;
    private static final long serialVersionUID = -2118662142567753854L;
    
    static {
        MOVE_DIRECTIONS = new MoveDirection[] { MoveDirection.DOWN_KNIGHT_LEFT, MoveDirection.DOWN_KNIGHT_RIGHT, MoveDirection.RIGHT_KNIGHT_DOWN, MoveDirection.RIGHT_KNIGHT_UP, MoveDirection.UP_KNIGHT_LEFT, MoveDirection.UP_KNIGHT_RIGHT, MoveDirection.LEFT_KNIGHT_DOWN, MoveDirection.LEFT_KNIGHT_UP };
        WHITE = new Knight(true);
        BLACK = new Knight(false);
    }
    
    private Knight(final boolean b) {
        super(b);
    }
    
    public static Knight instanceForColor(final boolean b) {
        if (b) {
            return Knight.WHITE;
        }
        return Knight.BLACK;
    }
    
    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'N';
        }
        return 'n';
    }
    
    @Override
    public MoveDirection[] getMoveDirections(final Square square) {
        return Knight.MOVE_DIRECTIONS;
    }
    
    @Override
    public int getMoveNumberOfSquares(final Square square) {
        return 1;
    }
    
    @Override
    public String getPieceFigurineNotation() {
        return this.getPieceUnicodeChar();
    }
    
    @Override
    public String getPieceNotation() {
        return "N";
    }
    
    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2658";
        }
        return "\u265e";
    }
    
    @Override
    public MoveDirection[] getTakeDirections(final Square square) {
        return Knight.MOVE_DIRECTIONS;
    }
    
    @Override
    public int getTakeNumberOfSquares(final Square square) {
        return 1;
    }
}
