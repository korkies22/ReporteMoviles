/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 */
package android.support.v7.widget;

import android.support.v7.widget.AppCompatSpinner;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

class AppCompatSpinner.DropdownPopup
implements PopupWindow.OnDismissListener {
    final /* synthetic */ ViewTreeObserver.OnGlobalLayoutListener val$layoutListener;

    AppCompatSpinner.DropdownPopup(ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        this.val$layoutListener = onGlobalLayoutListener;
    }

    public void onDismiss() {
        ViewTreeObserver viewTreeObserver = DropdownPopup.this.this$0.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.val$layoutListener);
        }
    }
}
