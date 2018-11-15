/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 */
package android.support.v7.widget;

import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

class Toolbar
implements ActionMenuView.OnMenuItemClickListener {
    Toolbar() {
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (Toolbar.this.mOnMenuItemClickListener != null) {
            return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
        }
        return false;
    }
}
