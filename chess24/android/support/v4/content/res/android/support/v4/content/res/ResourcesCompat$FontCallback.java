/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 *  android.os.Handler
 *  android.os.Looper
 */
package android.support.v4.content.res;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.ResourcesCompat;

public static abstract class ResourcesCompat.FontCallback {
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public final void callbackFailAsync(final int n, @Nullable Handler handler) {
        Handler handler2 = handler;
        if (handler == null) {
            handler2 = new Handler(Looper.getMainLooper());
        }
        handler2.post(new Runnable(){

            @Override
            public void run() {
                FontCallback.this.onFontRetrievalFailed(n);
            }
        });
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public final void callbackSuccessAsync(final Typeface typeface, @Nullable Handler handler) {
        Handler handler2 = handler;
        if (handler == null) {
            handler2 = new Handler(Looper.getMainLooper());
        }
        handler2.post(new Runnable(){

            @Override
            public void run() {
                FontCallback.this.onFontRetrieved(typeface);
            }
        });
    }

    public abstract void onFontRetrievalFailed(int var1);

    public abstract void onFontRetrieved(@NonNull Typeface var1);

}
