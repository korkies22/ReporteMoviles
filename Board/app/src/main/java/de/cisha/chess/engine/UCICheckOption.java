// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

public class UCICheckOption extends UCIValuedOption<Boolean>
{
    public UCICheckOption(final String s, final UCIOptionListener uciOptionListener, final boolean b) {
        super(s, uciOptionListener, b);
    }
    
    @Override
    public UCIOptionType getType() {
        return UCIOptionType.UCIOPTION_TYPE_CHECK;
    }
    
    @Override
    public boolean isValidValue(final Boolean b) {
        return true;
    }
}
