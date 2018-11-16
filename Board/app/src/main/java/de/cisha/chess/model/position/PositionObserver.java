// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position;

import de.cisha.chess.model.Move;

public interface PositionObserver
{
    void positionChanged(final Position p0, final Move p1);
}
