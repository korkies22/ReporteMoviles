/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;
import java.lang.ref.WeakReference;

class AppCompatTextHelper
extends ResourcesCompat.FontCallback {
    final /* synthetic */ WeakReference val$textViewWeak;

    AppCompatTextHelper(WeakReference weakReference) {
        this.val$textViewWeak = weakReference;
    }

    @Override
    public void onFontRetrievalFailed(int n) {
    }

    @Override
    public void onFontRetrieved(@NonNull Typeface typeface) {
        AppCompatTextHelper.this.onAsyncTypefaceReceived(this.val$textViewWeak, typeface);
    }
}
