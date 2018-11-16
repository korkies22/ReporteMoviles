// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public abstract class ChessVariant
{
    public static final ChessVariant CLASSIC_CHESS;
    
    static {
        CLASSIC_CHESS = new ClassicChess();
    }
    
    public abstract MoveDirection[] getAllMoveDirections();
    
    public abstract Piece[] getAllPieceTypes();
    
    public abstract Square[] getAllSquares();
    
    public abstract MoveDirection[] getAllTakeDirections();
    
    public abstract int getMaxMovesForMoveDirection(final MoveDirection p0);
    
    public abstract int getMaxMovesForTakeDirection(final MoveDirection p0);
    
    public abstract int getNumberForPieceType(final Piece p0);
}
