// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position;

import de.cisha.chess.model.FEN;

public interface PositionObservable
{
    void addPositionObserver(final PositionObserver p0);
    
    FEN getFEN();
    
    Position getPosition();
    
    void removePositionObserver(final PositionObserver p0);
}
