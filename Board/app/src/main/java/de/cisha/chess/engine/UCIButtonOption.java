// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

public class UCIButtonOption extends UCIOption
{
    public UCIButtonOption(final String s, final UCIOptionListener uciOptionListener) {
        super(s, uciOptionListener);
    }
    
    @Override
    public UCIOptionType getType() {
        return UCIOptionType.UCIOPTION_TYPE_BUTTON;
    }
    
    @Override
    public String getUCICommand() {
        final StringBuilder sb = new StringBuilder();
        sb.append("setoption name ");
        sb.append(this.getName());
        return sb.toString();
    }
    
    public void pushButton() {
        this.valueChanged();
    }
}
