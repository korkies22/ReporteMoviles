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

@RequiresApi(value=19)
static class ViewCompat.ViewCompatApi19Impl
extends ViewCompat.ViewCompatApi18Impl {
    ViewCompat.ViewCompatApi19Impl() {
    }

    @Override
    public int getAccessibilityLiveRegion(View view) {
        return view.getAccessibilityLiveRegion();
    }

    @Override
    public boolean isAttachedToWindow(View view) {
        return view.isAttachedToWindow();
    }

    @Override
    public boolean isLaidOut(View view) {
        return view.isLaidOut();
    }

    @Override
    public boolean isLayoutDirectionResolved(View view) {
        return view.isLayoutDirectionResolved();
    }

    @Override
    public void setAccessibilityLiveRegion(View view, int n) {
        view.setAccessibilityLiveRegion(n);
    }

    @Override
    public void setImportantForAccessibility(View view, int n) {
        view.setImportantForAccessibility(n);
    }
}
