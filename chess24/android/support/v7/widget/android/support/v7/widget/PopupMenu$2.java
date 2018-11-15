/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 */
package android.support.v7.widget;

import android.support.v7.widget.PopupMenu;
import android.widget.PopupWindow;

class PopupMenu
implements PopupWindow.OnDismissListener {
    PopupMenu() {
    }

    public void onDismiss() {
        if (PopupMenu.this.mOnDismissListener != null) {
            PopupMenu.this.mOnDismissListener.onDismiss(PopupMenu.this);
        }
    }
}
