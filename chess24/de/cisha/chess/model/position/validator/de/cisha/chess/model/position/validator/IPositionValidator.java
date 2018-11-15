/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.chess.model.position.validator;

import android.content.Context;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.validator.IPositionRuleValidator;
import java.util.List;

public interface IPositionValidator {
    public List<String> getErrorMessages();

    public /* varargs */ void setRuleSet(Class<? extends IPositionRuleValidator> ... var1);

    public boolean verify(Position var1, Context var2);
}
