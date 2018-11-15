/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.chess.model.position.validator;

import android.content.Context;
import de.cisha.chess.model.position.Position;

public interface IPositionRuleValidator {
    public String getErrorMessage(Context var1);

    public boolean verify(Position var1);
}
