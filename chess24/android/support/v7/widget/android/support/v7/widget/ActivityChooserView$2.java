/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package android.support.v7.widget;

import android.support.v4.view.ActionProvider;
import android.support.v7.widget.ListPopupWindow;
import android.view.ViewTreeObserver;

class ActivityChooserView
implements ViewTreeObserver.OnGlobalLayoutListener {
    ActivityChooserView() {
    }

    public void onGlobalLayout() {
        if (ActivityChooserView.this.isShowingPopup()) {
            if (!ActivityChooserView.this.isShown()) {
                ActivityChooserView.this.getListPopupWindow().dismiss();
                return;
            }
            ActivityChooserView.this.getListPopupWindow().show();
            if (ActivityChooserView.this.mProvider != null) {
                ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
            }
        }
    }
}
