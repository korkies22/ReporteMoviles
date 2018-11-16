// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.pieces;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.MoveDirection;
import java.io.Serializable;
import de.cisha.chess.model.Piece;

public class Bishop extends Piece implements Serializable
{
    public static final Bishop BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final Bishop WHITE;
    private static final long serialVersionUID = -4160180296067189515L;
    
    static {
        MOVE_DIRECTIONS = new MoveDirection[] { MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT };
        WHITE = new Bishop(true);
        BLACK = new Bishop(false);
    }
    
    private Bishop(final boolean b) {
        super(b);
    }
    
    public static Bishop instanceForColor(final boolean b) {
        if (b) {
            return Bishop.WHITE;
        }
        return Bishop.BLACK;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o;
    }
    
    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'B';
        }
        return 'b';
    }
    
    @Override
    public MoveDirection[] getMoveDirections(final Square square) {
        return Bishop.MOVE_DIRECTIONS;
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
        return "B";
    }
    
    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2657";
        }
        return "\u265d";
    }
    
    @Override
    public MoveDirection[] getTakeDirections(final Square square) {
        return Bishop.MOVE_DIRECTIONS;
    }
    
    @Override
    public int getTakeNumberOfSquares(final Square square) {
        return 7;
    }
}
