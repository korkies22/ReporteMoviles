/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package android.support.v4.provider;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.provider.FontsContractCompat;

private static final class FontsContractCompat.TypefaceResult {
    final int mResult;
    final Typeface mTypeface;

    FontsContractCompat.TypefaceResult(@Nullable Typeface typeface, int n) {
        this.mTypeface = typeface;
        this.mResult = n;
    }
}
