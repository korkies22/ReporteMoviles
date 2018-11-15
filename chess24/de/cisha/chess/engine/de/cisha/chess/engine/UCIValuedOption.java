/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIOption;
import de.cisha.chess.engine.UCIOptionListener;

public abstract class UCIValuedOption<V>
extends UCIOption {
    private V _currentValue;
    private V _defaultValue;

    public UCIValuedOption(String string, UCIOptionListener uCIOptionListener, V v) {
        super(string, uCIOptionListener);
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setoption name ");
        stringBuilder.append(this.getName());
        stringBuilder.append(" value ");
        stringBuilder.append(this._currentValue);
        return stringBuilder.toString();
    }

    public abstract boolean isValidValue(V var1);

    public void setValue(V v) {
        if (this.isValidValue(v)) {
            this._currentValue = v;
            this.valueChanged();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" default ");
        stringBuilder.append(this._defaultValue);
        stringBuilder.append(" current ");
        stringBuilder.append(this._currentValue);
        return stringBuilder.toString();
    }
}
