/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 *  android.os.Handler
 */
package android.support.v4.provider;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.provider.SelfDestructiveThread;

static final class FontsContractCompat
implements SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult> {
    final /* synthetic */ ResourcesCompat.FontCallback val$fontCallback;
    final /* synthetic */ Handler val$handler;

    FontsContractCompat(ResourcesCompat.FontCallback fontCallback, Handler handler) {
        this.val$fontCallback = fontCallback;
        this.val$handler = handler;
    }

    @Override
    public void onReply(FontsContractCompat.TypefaceResult typefaceResult) {
        if (typefaceResult == null) {
            this.val$fontCallback.callbackFailAsync(1, this.val$handler);
            return;
        }
        if (typefaceResult.mResult == 0) {
            this.val$fontCallback.callbackSuccessAsync(typefaceResult.mTypeface, this.val$handler);
            return;
        }
        this.val$fontCallback.callbackFailAsync(typefaceResult.mResult, this.val$handler);
    }
}
