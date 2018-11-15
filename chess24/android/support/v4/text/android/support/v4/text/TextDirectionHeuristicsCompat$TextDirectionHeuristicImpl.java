/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import java.nio.CharBuffer;

private static abstract class TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
implements TextDirectionHeuristicCompat {
    private final TextDirectionHeuristicsCompat.TextDirectionAlgorithm mAlgorithm;

    TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl(TextDirectionHeuristicsCompat.TextDirectionAlgorithm textDirectionAlgorithm) {
        this.mAlgorithm = textDirectionAlgorithm;
    }

    private boolean doCheck(CharSequence charSequence, int n, int n2) {
        switch (this.mAlgorithm.checkRtl(charSequence, n, n2)) {
            default: {
                return this.defaultIsRtl();
            }
            case 1: {
                return false;
            }
            case 0: 
        }
        return true;
    }

    protected abstract boolean defaultIsRtl();

    @Override
    public boolean isRtl(CharSequence charSequence, int n, int n2) {
        if (charSequence != null && n >= 0 && n2 >= 0 && charSequence.length() - n2 >= n) {
            if (this.mAlgorithm == null) {
                return this.defaultIsRtl();
            }
            return this.doCheck(charSequence, n, n2);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isRtl(char[] arrc, int n, int n2) {
        return this.isRtl(CharBuffer.wrap(arrc), n, n2);
    }
}
