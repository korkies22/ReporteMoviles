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
import de.cisha.chess.model.position.validator.IPositionValidator;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PositionValidator
implements IPositionValidator {
    private LinkedList<String> _errorMessages = new LinkedList();
    private LinkedList<IPositionRuleValidator> _validators = new LinkedList();

    @Override
    public List<String> getErrorMessages() {
        return new LinkedList<String>(this._errorMessages);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public /* varargs */ void setRuleSet(Class<? extends IPositionRuleValidator> ... arrclass) {
        int n = 0;
        int n2 = arrclass.length;
        do {
            if (n >= n2) {
                return;
            }
            Class<? extends IPositionRuleValidator> class_ = arrclass[n];
            try {
                this._validators.add(class_.newInstance());
            }
            catch (IllegalAccessException | InstantiationException reflectiveOperationException) {}
            ++n;
        } while (true);
    }

    @Override
    public boolean verify(Position position, Context context) {
        this._errorMessages.clear();
        Iterator iterator = this._validators.iterator();
        boolean bl = true;
        while (iterator.hasNext()) {
            IPositionRuleValidator iPositionRuleValidator = (IPositionRuleValidator)iterator.next();
            if (iPositionRuleValidator.verify(position)) continue;
            bl = false;
            this._errorMessages.add(iPositionRuleValidator.getErrorMessage(context));
        }
        return bl;
    }
}
