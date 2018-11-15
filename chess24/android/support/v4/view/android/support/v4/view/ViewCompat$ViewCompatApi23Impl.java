/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;

@RequiresApi(value=23)
static class ViewCompat.ViewCompatApi23Impl
extends ViewCompat.ViewCompatApi21Impl {
    ViewCompat.ViewCompatApi23Impl() {
    }

    @Override
    public int getScrollIndicators(View view) {
        return view.getScrollIndicators();
    }

    @Override
    public void offsetLeftAndRight(View view, int n) {
        view.offsetLeftAndRight(n);
    }

    @Override
    public void offsetTopAndBottom(View view, int n) {
        view.offsetTopAndBottom(n);
    }

    @Override
    public void setScrollIndicators(View view, int n) {
        view.setScrollIndicators(n);
    }

    @Override
    public void setScrollIndicators(View view, int n, int n2) {
        view.setScrollIndicators(n, n2);
    }
}
