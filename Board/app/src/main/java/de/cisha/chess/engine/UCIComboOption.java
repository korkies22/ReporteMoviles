// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Collection;
import java.util.Set;

public class UCIComboOption extends UCIValuedOption<String>
{
    public Set<String> _validValues;
    
    public UCIComboOption(final String s, final UCIOptionListener uciOptionListener, final String s2, final Collection<String> collection) {
        super(s, uciOptionListener, s2);
        this._validValues = new HashSet<String>(collection);
    }
    
    public List<String> getPossibleValues() {
        return new LinkedList<String>(this._validValues);
    }
    
    @Override
    public UCIOptionType getType() {
        return UCIOptionType.UCIOPTION_TYPE_COMBO;
    }
    
    @Override
    public boolean isValidValue(final String s) {
        return s != null && this._validValues.contains(s);
    }
    
    @Override
    public String toString() {
        String s = super.toString();
        for (final String s2 : this._validValues) {
            final StringBuilder sb = new StringBuilder();
            sb.append(s);
            sb.append(" var ");
            sb.append(s2);
            s = sb.toString();
        }
        return s;
    }
}
