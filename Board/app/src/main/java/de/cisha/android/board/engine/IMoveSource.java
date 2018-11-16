// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.engine;

import java.util.List;
import de.cisha.chess.model.position.Position;

public interface IMoveSource
{
    void destroy();
    
    List<EvaluationInfo> getMovesWithMaximumTime(final Position p0, final long p1);
}
