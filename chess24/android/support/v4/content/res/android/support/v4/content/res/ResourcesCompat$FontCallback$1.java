/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package android.support.v4.content.res;

import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

class ResourcesCompat.FontCallback
implements Runnable {
    final /* synthetic */ Typeface val$typeface;

    ResourcesCompat.FontCallback(Typeface typeface) {
        this.val$typeface = typeface;
    }

    @Override
    public void run() {
        FontCallback.this.onFontRetrieved(this.val$typeface);
    }
}
