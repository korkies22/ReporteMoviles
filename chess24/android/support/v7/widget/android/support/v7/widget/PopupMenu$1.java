/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 */
package android.support.v7.widget;

import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;

class PopupMenu
implements MenuBuilder.Callback {
    PopupMenu() {
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        if (PopupMenu.this.mMenuItemClickListener != null) {
            return PopupMenu.this.mMenuItemClickListener.onMenuItemClick(menuItem);
        }
        return false;
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
    }
}
