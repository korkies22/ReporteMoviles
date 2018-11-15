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

class CascadingMenuPopup
implements View.OnAttachStateChangeListener {
    CascadingMenuPopup() {
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        if (CascadingMenuPopup.this.mTreeObserver != null) {
            if (!CascadingMenuPopup.this.mTreeObserver.isAlive()) {
                CascadingMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
            }
            CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(CascadingMenuPopup.this.mGlobalLayoutListener);
        }
        view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
    }
}
