/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicsCompat;

private static class TextDirectionHeuristicsCompat.FirstStrong
implements TextDirectionHeuristicsCompat.TextDirectionAlgorithm {
    static final TextDirectionHeuristicsCompat.FirstStrong INSTANCE = new TextDirectionHeuristicsCompat.FirstStrong();

    private TextDirectionHeuristicsCompat.FirstStrong() {
    }

    @Override
    public int checkRtl(CharSequence charSequence, int n, int n2) {
        int n3 = 2;
        for (int i = n; i < n2 + n && n3 == 2; ++i) {
            n3 = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(charSequence.charAt(i)));
        }
        return n3;
    }
}
