/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 */
package android.support.design.widget;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.view.menu.MenuBuilder;
import android.view.MenuItem;

class BottomNavigationView
implements MenuBuilder.Callback {
    BottomNavigationView() {
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        if (BottomNavigationView.this.mReselectedListener != null && menuItem.getItemId() == BottomNavigationView.this.getSelectedItemId()) {
            BottomNavigationView.this.mReselectedListener.onNavigationItemReselected(menuItem);
            return true;
        }
        if (BottomNavigationView.this.mSelectedListener != null && !BottomNavigationView.this.mSelectedListener.onNavigationItemSelected(menuItem)) {
            return true;
        }
        return false;
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
    }
}
