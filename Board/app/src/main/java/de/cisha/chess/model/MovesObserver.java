// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public interface MovesObserver
{
    void allMovesChanged(final MoveContainer p0);
    
    boolean canSkipMoves();
    
    void newMove(final Move p0);
    
    void selectedMoveChanged(final Move p0);
}
