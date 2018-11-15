/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.WindowInsets
 */
package android.support.v4.view;

import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;
import android.view.WindowInsets;

class ViewCompat.ViewCompatApi21Impl
implements View.OnApplyWindowInsetsListener {
    final /* synthetic */ OnApplyWindowInsetsListener val$listener;

    ViewCompat.ViewCompatApi21Impl(OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        this.val$listener = onApplyWindowInsetsListener;
    }

    public WindowInsets onApplyWindowInsets(View view, WindowInsets object) {
        object = WindowInsetsCompat.wrap(object);
        return (WindowInsets)WindowInsetsCompat.unwrap(this.val$listener.onApplyWindowInsets(view, (WindowInsetsCompat)object));
    }
}
