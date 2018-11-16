// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position.validator;

import java.util.Iterator;
import android.content.Context;
import de.cisha.chess.model.position.Position;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public class PositionValidator implements IPositionValidator
{
    private LinkedList<String> _errorMessages;
    private LinkedList<IPositionRuleValidator> _validators;
    
    public PositionValidator() {
        this._validators = new LinkedList<IPositionRuleValidator>();
        this._errorMessages = new LinkedList<String>();
    }
    
    @Override
    public List<String> getErrorMessages() {
        return new LinkedList<String>(this._errorMessages);
    }
    
    @Override
    public void setRuleSet(final Class<? extends IPositionRuleValidator>... array) {
        int n = 0;
        final int length = array.length;
    Label_0028_Outer:
        while (true) {
            if (n >= length) {
                return;
            }
            final Class<? extends IPositionRuleValidator> clazz = array[n];
            while (true) {
                try {
                    this._validators.add((IPositionRuleValidator)clazz.newInstance());
                    ++n;
                    continue Label_0028_Outer;
                }
                catch (InstantiationException | IllegalAccessException ex) {
                    continue;
                }
                break;
            }
        }
    }
    
    @Override
    public boolean verify(final Position position, final Context context) {
        this._errorMessages.clear();
        final Iterator<IPositionRuleValidator> iterator = (Iterator<IPositionRuleValidator>)this._validators.iterator();
        boolean b = true;
        while (iterator.hasNext()) {
            final IPositionRuleValidator positionRuleValidator = iterator.next();
            if (!positionRuleValidator.verify(position)) {
                b = false;
                this._errorMessages.add(positionRuleValidator.getErrorMessage(context));
            }
        }
        return b;
    }
}
