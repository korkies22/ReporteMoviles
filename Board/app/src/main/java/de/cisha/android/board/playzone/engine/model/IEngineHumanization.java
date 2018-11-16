// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.model;

import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Move;
import de.cisha.android.board.engine.EvaluationInfo;
import java.util.List;

public interface IEngineHumanization
{
    boolean acceptDrawOffer();
    
    Move chooseMove(final List<EvaluationInfo> p0);
    
    long getMaximumThinkingTimeForNextMove();
    
    Rating getRating();
    
    boolean resign();
}
