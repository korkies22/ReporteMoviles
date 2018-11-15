/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.text.TextUtilsCompat;
import java.util.Locale;

private static class TextDirectionHeuristicsCompat.TextDirectionHeuristicLocale
extends TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl {
    static final TextDirectionHeuristicsCompat.TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicsCompat.TextDirectionHeuristicLocale();

    TextDirectionHeuristicsCompat.TextDirectionHeuristicLocale() {
        super(null);
    }

    @Override
    protected boolean defaultIsRtl() {
        if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
            return true;
        }
        return false;
    }
}
