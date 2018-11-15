/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIOption;
import de.cisha.chess.engine.UCIOptionListener;
import de.cisha.chess.engine.UCIValuedOption;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UCIComboOption
extends UCIValuedOption<String> {
    public Set<String> _validValues;

    public UCIComboOption(String string, UCIOptionListener uCIOptionListener, String string2, Collection<String> collection) {
        super(string, uCIOptionListener, string2);
        this._validValues = new HashSet<String>(collection);
    }

    public List<String> getPossibleValues() {
        return new LinkedList<String>(this._validValues);
    }

    @Override
    public UCIOption.UCIOptionType getType() {
        return UCIOption.UCIOptionType.UCIOPTION_TYPE_COMBO;
    }

    @Override
    public boolean isValidValue(String string) {
        if (string != null && this._validValues.contains(string)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String string = super.toString();
        for (String string2 : this._validValues) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" var ");
            stringBuilder.append(string2);
            string = stringBuilder.toString();
        }
        return string;
    }
}
