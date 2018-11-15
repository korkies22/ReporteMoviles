/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package de.cisha.chess.model.position.validator;

import android.content.Context;
import android.content.res.Resources;
import de.cisha.chess.R;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.validator.IPositionRuleValidator;

public class InactiveKingNotCheckedValidator
implements IPositionRuleValidator {
    @Override
    public String getErrorMessage(Context context) {
        return context.getResources().getString(R.string.position_validation_rule_wrong_number_of_kings);
    }

    @Override
    public boolean verify(Position position) {
        return position.isCheck(position.getActiveColor() ^ true) ^ true;
    }
}
