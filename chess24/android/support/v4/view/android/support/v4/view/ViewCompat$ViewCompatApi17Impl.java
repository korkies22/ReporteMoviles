/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.view.Display
 *  android.view.View
 */
package android.support.v4.view;

import android.graphics.Paint;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.View;

@RequiresApi(value=17)
static class ViewCompat.ViewCompatApi17Impl
extends ViewCompat.ViewCompatApi16Impl {
    ViewCompat.ViewCompatApi17Impl() {
    }

    @Override
    public int generateViewId() {
        return View.generateViewId();
    }

    @Override
    public Display getDisplay(View view) {
        return view.getDisplay();
    }

    @Override
    public int getLabelFor(View view) {
        return view.getLabelFor();
    }

    @Override
    public int getLayoutDirection(View view) {
        return view.getLayoutDirection();
    }

    @Override
    public int getPaddingEnd(View view) {
        return view.getPaddingEnd();
    }

    @Override
    public int getPaddingStart(View view) {
        return view.getPaddingStart();
    }

    @Override
    public int getWindowSystemUiVisibility(View view) {
        return view.getWindowSystemUiVisibility();
    }

    @Override
    public boolean isPaddingRelative(View view) {
        return view.isPaddingRelative();
    }

    @Override
    public void setLabelFor(View view, int n) {
        view.setLabelFor(n);
    }

    @Override
    public void setLayerPaint(View view, Paint paint) {
        view.setLayerPaint(paint);
    }

    @Override
    public void setLayoutDirection(View view, int n) {
        view.setLayoutDirection(n);
    }

    @Override
    public void setPaddingRelative(View view, int n, int n2, int n3, int n4) {
        view.setPaddingRelative(n, n2, n3, n4);
    }
}
