/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package android.support.v4.provider;

import android.graphics.Typeface;
import android.support.v4.provider.FontsContractCompat;

class FontsContractCompat
implements Runnable {
    final /* synthetic */ Typeface val$typeface;

    FontsContractCompat(Typeface typeface) {
        this.val$typeface = typeface;
    }

    @Override
    public void run() {
        4.this.val$callback.onTypefaceRetrieved(this.val$typeface);
    }
}
