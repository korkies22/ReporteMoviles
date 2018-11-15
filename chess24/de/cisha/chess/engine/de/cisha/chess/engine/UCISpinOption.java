/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIOption;
import de.cisha.chess.engine.UCIOptionListener;
import de.cisha.chess.engine.UCIValuedOption;

public class UCISpinOption
extends UCIValuedOption<Integer> {
    private int _maxValue;
    private int _minValue;

    public UCISpinOption(String string, UCIOptionListener uCIOptionListener, int n, int n2, int n3) {
        super(string, uCIOptionListener, n3);
        this._minValue = n;
        this._maxValue = n2;
    }

    public int getMaxValue() {
        return this._maxValue;
    }

    public int getMinValue() {
        return this._minValue;
    }

    @Override
    public UCIOption.UCIOptionType getType() {
        return UCIOption.UCIOptionType.UCIOPTION_TYPE_SPIN;
    }

    @Override
    public boolean isValidValue(Integer n) {
        if (n <= this._maxValue && n >= this._minValue) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" min ");
        stringBuilder.append(this._minValue);
        stringBuilder.append(" max ");
        stringBuilder.append(this._maxValue);
        return stringBuilder.toString();
    }
}
