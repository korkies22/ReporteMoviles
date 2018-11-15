/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.annotation.RestrictTo;
import android.support.design.widget.BaseTransientBottomBar;
import android.view.View;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
static interface BaseTransientBottomBar.OnAttachStateChangeListener {
    public void onViewAttachedToWindow(View var1);

    public void onViewDetachedFromWindow(View var1);
}
