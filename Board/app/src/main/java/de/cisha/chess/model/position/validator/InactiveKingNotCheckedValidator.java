// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position.validator;

import de.cisha.chess.model.position.Position;
import de.cisha.chess.R;
import android.content.Context;

public class InactiveKingNotCheckedValidator implements IPositionRuleValidator
{
    @Override
    public String getErrorMessage(final Context context) {
        return context.getResources().getString(R.string.position_validation_rule_wrong_number_of_kings);
    }
    
    @Override
    public boolean verify(final Position position) {
        return position.isCheck(position.getActiveColor() ^ true) ^ true;
    }
}
