/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 */
package android.support.design.widget;

import android.support.design.widget.NavigationView;
import android.support.v7.view.menu.MenuBuilder;
import android.view.MenuItem;

class NavigationView
implements MenuBuilder.Callback {
    NavigationView() {
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        if (NavigationView.this.mListener != null && NavigationView.this.mListener.onNavigationItemSelected(menuItem)) {
            return true;
        }
        return false;
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
    }
}
