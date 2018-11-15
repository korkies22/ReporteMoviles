/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.view.menu.MenuPopup;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ForwardingListener;
import android.view.View;

class PopupMenu
extends ForwardingListener {
    PopupMenu(View view) {
        super(view);
    }

    @Override
    public ShowableListMenu getPopup() {
        return PopupMenu.this.mPopup.getPopup();
    }

    @Override
    protected boolean onForwardingStarted() {
        PopupMenu.this.show();
        return true;
    }

    @Override
    protected boolean onForwardingStopped() {
        PopupMenu.this.dismiss();
        return true;
    }
}
