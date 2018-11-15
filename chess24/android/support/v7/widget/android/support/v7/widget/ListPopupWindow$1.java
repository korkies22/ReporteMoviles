/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ForwardingListener;
import android.view.View;

class ListPopupWindow
extends ForwardingListener {
    ListPopupWindow(View view) {
        super(view);
    }

    @Override
    public android.support.v7.widget.ListPopupWindow getPopup() {
        return ListPopupWindow.this;
    }
}
