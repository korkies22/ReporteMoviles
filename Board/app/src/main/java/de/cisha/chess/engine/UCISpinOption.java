// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

public class UCISpinOption extends UCIValuedOption<Integer>
{
    private int _maxValue;
    private int _minValue;
    
    public UCISpinOption(final String s, final UCIOptionListener uciOptionListener, final int minValue, final int maxValue, final int n) {
        super(s, uciOptionListener, n);
        this._minValue = minValue;
        this._maxValue = maxValue;
    }
    
    public int getMaxValue() {
        return this._maxValue;
    }
    
    public int getMinValue() {
        return this._minValue;
    }
    
    @Override
    public UCIOptionType getType() {
        return UCIOptionType.UCIOPTION_TYPE_SPIN;
    }
    
    @Override
    public boolean isValidValue(final Integer n) {
        return n <= this._maxValue && n >= this._minValue;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" min ");
        sb.append(this._minValue);
        sb.append(" max ");
        sb.append(this._maxValue);
        return sb.toString();
    }
}
