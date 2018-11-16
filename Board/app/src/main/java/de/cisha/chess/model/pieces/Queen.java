// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.pieces;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.MoveDirection;
import java.io.Serializable;
import de.cisha.chess.model.Piece;

public class Queen extends Piece implements Serializable
{
    public static final Queen BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final Queen WHITE;
    private static final long serialVersionUID = 3250496508108306143L;
    
    static {
        MOVE_DIRECTIONS = new MoveDirection[] { MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT };
        WHITE = new Queen(true);
        BLACK = new Queen(false);
    }
    
    private Queen(final boolean b) {
        super(b);
    }
    
    public static Queen instanceForColor(final boolean b) {
        if (b) {
            return Queen.WHITE;
        }
        return Queen.BLACK;
    }
    
    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'Q';
        }
        return 'q';
    }
    
    @Override
    public MoveDirection[] getMoveDirections(final Square square) {
        return Queen.MOVE_DIRECTIONS;
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
        return "Q";
    }
    
    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2655";
        }
        return "\u265b";
    }
    
    @Override
    public MoveDirection[] getTakeDirections(final Square square) {
        return Queen.MOVE_DIRECTIONS;
    }
    
    @Override
    public int getTakeNumberOfSquares(final Square square) {
        return 7;
    }
}
