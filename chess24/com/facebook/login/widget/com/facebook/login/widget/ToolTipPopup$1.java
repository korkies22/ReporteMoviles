/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnScrollChangedListener
 *  android.widget.PopupWindow
 */
package com.facebook.login.widget;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import com.facebook.login.widget.ToolTipPopup;
import java.lang.ref.WeakReference;

class ToolTipPopup
implements ViewTreeObserver.OnScrollChangedListener {
    ToolTipPopup() {
    }

    public void onScrollChanged() {
        if (ToolTipPopup.this.mAnchorViewRef.get() != null && ToolTipPopup.this.mPopupWindow != null && ToolTipPopup.this.mPopupWindow.isShowing()) {
            if (ToolTipPopup.this.mPopupWindow.isAboveAnchor()) {
                ToolTipPopup.this.mPopupContent.showBottomArrow();
                return;
            }
            ToolTipPopup.this.mPopupContent.showTopArrow();
        }
    }
}
