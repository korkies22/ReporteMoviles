/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicsCompat;

private static class TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal
extends TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl {
    private final boolean mDefaultIsRtl;

    TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal(TextDirectionHeuristicsCompat.TextDirectionAlgorithm textDirectionAlgorithm, boolean bl) {
        super(textDirectionAlgorithm);
        this.mDefaultIsRtl = bl;
    }

    @Override
    protected boolean defaultIsRtl() {
        return this.mDefaultIsRtl;
    }
}
