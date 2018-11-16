// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public interface MovesObservable
{
    void addMovesObserver(final MovesObserver p0);
    
    Move getCurrentMove();
    
    MoveContainer getRootMoveContainer();
    
    void removeMovesObserver(final MovesObserver p0);
}
