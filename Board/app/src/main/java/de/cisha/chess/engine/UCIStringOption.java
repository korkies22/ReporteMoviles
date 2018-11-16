// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

public class UCIStringOption extends UCIValuedOption<String>
{
    public UCIStringOption(final String s, final UCIOptionListener uciOptionListener, final String s2) {
        super(s, uciOptionListener, s2);
    }
    
    @Override
    public UCIOptionType getType() {
        return UCIOptionType.UCIOPTION_TYPE_STRING;
    }
    
    @Override
    public boolean isValidValue(final String s) {
        return true;
    }
}
