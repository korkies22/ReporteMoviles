/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package android.support.v7.view.menu;

import android.support.v7.widget.MenuPopupWindow;
import android.view.View;
import android.view.ViewTreeObserver;

class StandardMenuPopup
implements ViewTreeObserver.OnGlobalLayoutListener {
    StandardMenuPopup() {
    }

    public void onGlobalLayout() {
        if (StandardMenuPopup.this.isShowing() && !StandardMenuPopup.this.mPopup.isModal()) {
            View view = StandardMenuPopup.this.mShownAnchorView;
            if (view != null && view.isShown()) {
                StandardMenuPopup.this.mPopup.show();
                return;
            }
            StandardMenuPopup.this.dismiss();
        }
    }
}
