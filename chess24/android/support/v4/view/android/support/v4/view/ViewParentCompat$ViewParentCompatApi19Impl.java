/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewParentCompat;
import android.view.View;
import android.view.ViewParent;

@RequiresApi(value=19)
static class ViewParentCompat.ViewParentCompatApi19Impl
extends ViewParentCompat.ViewParentCompatBaseImpl {
    ViewParentCompat.ViewParentCompatApi19Impl() {
    }

    @Override
    public void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int n) {
        viewParent.notifySubtreeAccessibilityStateChanged(view, view2, n);
    }
}
