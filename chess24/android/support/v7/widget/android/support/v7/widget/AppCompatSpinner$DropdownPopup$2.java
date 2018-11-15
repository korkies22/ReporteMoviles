/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package android.support.v7.widget;

import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.ViewTreeObserver;

class AppCompatSpinner.DropdownPopup
implements ViewTreeObserver.OnGlobalLayoutListener {
    AppCompatSpinner.DropdownPopup() {
    }

    public void onGlobalLayout() {
        if (!DropdownPopup.this.isVisibleToUser((View)DropdownPopup.this.this$0)) {
            DropdownPopup.this.dismiss();
            return;
        }
        DropdownPopup.this.computeContentWidth();
        AppCompatSpinner.DropdownPopup.super.show();
    }
}
