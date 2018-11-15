/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Typeface
 */
package android.support.v4.provider;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.LruCache;
import java.util.concurrent.Callable;

static final class FontsContractCompat
implements Callable<FontsContractCompat.TypefaceResult> {
    final /* synthetic */ Context val$context;
    final /* synthetic */ String val$id;
    final /* synthetic */ FontRequest val$request;
    final /* synthetic */ int val$style;

    FontsContractCompat(Context context, FontRequest fontRequest, int n, String string) {
        this.val$context = context;
        this.val$request = fontRequest;
        this.val$style = n;
        this.val$id = string;
    }

    @Override
    public FontsContractCompat.TypefaceResult call() throws Exception {
        FontsContractCompat.TypefaceResult typefaceResult = android.support.v4.provider.FontsContractCompat.getFontInternal(this.val$context, this.val$request, this.val$style);
        if (typefaceResult.mTypeface != null) {
            sTypefaceCache.put(this.val$id, typefaceResult.mTypeface);
        }
        return typefaceResult;
    }
}
