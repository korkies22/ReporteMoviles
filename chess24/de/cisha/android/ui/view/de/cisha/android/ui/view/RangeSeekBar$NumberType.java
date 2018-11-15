/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.view;

import de.cisha.android.ui.view.RangeSeekBar;
import java.math.BigDecimal;

private static enum RangeSeekBar.NumberType {
    LONG,
    DOUBLE,
    INTEGER,
    FLOAT,
    SHORT,
    BYTE,
    BIG_DECIMAL;
    

    private RangeSeekBar.NumberType() {
    }

    public static <E extends Number> RangeSeekBar.NumberType fromNumber(E e) throws IllegalArgumentException {
        if (e instanceof Long) {
            return LONG;
        }
        if (e instanceof Double) {
            return DOUBLE;
        }
        if (e instanceof Integer) {
            return INTEGER;
        }
        if (e instanceof Float) {
            return FLOAT;
        }
        if (e instanceof Short) {
            return SHORT;
        }
        if (e instanceof Byte) {
            return BYTE;
        }
        if (e instanceof BigDecimal) {
            return BIG_DECIMAL;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number class '");
        stringBuilder.append(e.getClass().getName());
        stringBuilder.append("' is not supported");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Number toNumber(double d) {
        switch (RangeSeekBar.$SwitchMap$de$cisha$android$ui$view$RangeSeekBar$NumberType[this.ordinal()]) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("can't convert ");
                stringBuilder.append((Object)this);
                stringBuilder.append(" to a Number object");
                throw new InstantiationError(stringBuilder.toString());
            }
            case 7: {
                return new BigDecimal(d);
            }
            case 6: {
                return new Byte((byte)d);
            }
            case 5: {
                return new Short((short)d);
            }
            case 4: {
                return new Float(d);
            }
            case 3: {
                return new Integer((int)d);
            }
            case 2: {
                return d;
            }
            case 1: 
        }
        return new Long((long)d);
    }
}
