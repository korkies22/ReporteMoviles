/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;

class ActivityChooserView
extends ForwardingListener {
    ActivityChooserView(View view) {
        super(view);
    }

    @Override
    public ShowableListMenu getPopup() {
        return ActivityChooserView.this.getListPopupWindow();
    }

    @Override
    protected boolean onForwardingStarted() {
        ActivityChooserView.this.showPopup();
        return true;
    }

    @Override
    protected boolean onForwardingStopped() {
        ActivityChooserView.this.dismissPopup();
        return true;
    }
}
