// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

public abstract class UCIValuedOption<V> extends UCIOption
{
    private V _currentValue;
    private V _defaultValue;
    
    public UCIValuedOption(final String s, final UCIOptionListener uciOptionListener, final V v) {
        super(s, uciOptionListener);
        this._defaultValue = v;
        this._currentValue = v;
    }
    
    public V getCurrentValue() {
        return this._currentValue;
    }
    
    public V getDefaultValue() {
        return this._defaultValue;
    }
    
    @Override
    public String getUCICommand() {
        final StringBuilder sb = new StringBuilder();
        sb.append("setoption name ");
        sb.append(this.getName());
        sb.append(" value ");
        sb.append(this._currentValue);
        return sb.toString();
    }
    
    public abstract boolean isValidValue(final V p0);
    
    public void setValue(final V currentValue) {
        if (this.isValidValue(currentValue)) {
            this._currentValue = currentValue;
            this.valueChanged();
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" default ");
        sb.append(this._defaultValue);
        sb.append(" current ");
        sb.append(this._currentValue);
        return sb.toString();
    }
}
