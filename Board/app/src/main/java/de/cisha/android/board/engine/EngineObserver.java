// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.engine;

import java.util.List;
import de.cisha.chess.model.Move;

public interface EngineObserver
{
    void onEngineStarted();
    
    void onEngineStopped(final Move p0);
    
    void onVariationsChanged(final List<EvaluationInfo> p0);
}
