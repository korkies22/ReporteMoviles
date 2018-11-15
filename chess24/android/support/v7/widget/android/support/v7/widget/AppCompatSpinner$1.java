/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.ForwardingListener;
import android.view.View;

class AppCompatSpinner
extends ForwardingListener {
    final /* synthetic */ AppCompatSpinner.DropdownPopup val$popup;

    AppCompatSpinner(View view, AppCompatSpinner.DropdownPopup dropdownPopup) {
        this.val$popup = dropdownPopup;
        super(view);
    }

    @Override
    public ShowableListMenu getPopup() {
        return this.val$popup;
    }

    @Override
    public boolean onForwardingStarted() {
        if (!AppCompatSpinner.this.mPopup.isShowing()) {
            AppCompatSpinner.this.mPopup.show();
        }
        return true;
    }
}
