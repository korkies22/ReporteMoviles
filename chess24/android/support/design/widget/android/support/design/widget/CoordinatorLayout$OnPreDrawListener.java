/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.design.widget;

import android.support.design.widget.CoordinatorLayout;
import android.view.ViewTreeObserver;

class CoordinatorLayout.OnPreDrawListener
implements ViewTreeObserver.OnPreDrawListener {
    CoordinatorLayout.OnPreDrawListener() {
    }

    public boolean onPreDraw() {
        CoordinatorLayout.this.onChildViewsChanged(0);
        return true;
    }
}
