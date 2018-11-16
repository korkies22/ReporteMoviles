// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position.validator;

import de.cisha.chess.model.position.Position;
import android.content.Context;

public interface IPositionRuleValidator
{
    String getErrorMessage(final Context p0);
    
    boolean verify(final Position p0);
}
