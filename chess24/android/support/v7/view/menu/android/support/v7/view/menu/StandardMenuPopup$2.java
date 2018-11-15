/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package android.support.v7.view.menu;

import android.view.View;
import android.view.ViewTreeObserver;

class StandardMenuPopup
implements View.OnAttachStateChangeListener {
    StandardMenuPopup() {
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        if (StandardMenuPopup.this.mTreeObserver != null) {
            if (!StandardMenuPopup.this.mTreeObserver.isAlive()) {
                StandardMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
            }
            StandardMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(StandardMenuPopup.this.mGlobalLayoutListener);
        }
        view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
    }
}
