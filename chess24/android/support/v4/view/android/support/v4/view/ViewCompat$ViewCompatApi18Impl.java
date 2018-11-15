/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 */
package android.support.v4.view;

import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;

@RequiresApi(value=18)
static class ViewCompat.ViewCompatApi18Impl
extends ViewCompat.ViewCompatApi17Impl {
    ViewCompat.ViewCompatApi18Impl() {
    }

    @Override
    public Rect getClipBounds(View view) {
        return view.getClipBounds();
    }

    @Override
    public boolean isInLayout(View view) {
        return view.isInLayout();
    }

    @Override
    public void setClipBounds(View view, Rect rect) {
        view.setClipBounds(rect);
    }
}
