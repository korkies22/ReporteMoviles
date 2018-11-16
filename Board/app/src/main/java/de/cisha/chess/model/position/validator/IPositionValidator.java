// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position.validator;

import android.content.Context;
import de.cisha.chess.model.position.Position;
import java.util.List;

public interface IPositionValidator
{
    List<String> getErrorMessages();
    
    void setRuleSet(final Class<? extends IPositionRuleValidator>... p0);
    
    boolean verify(final Position p0, final Context p1);
}
