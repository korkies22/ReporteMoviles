/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 */
package android.support.v7.view.menu;

import android.support.v7.view.menu.BaseWrapper;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.view.MenuItem;

private class MenuItemWrapperICS.OnMenuItemClickListenerWrapper
extends BaseWrapper<MenuItem.OnMenuItemClickListener>
implements MenuItem.OnMenuItemClickListener {
    MenuItemWrapperICS.OnMenuItemClickListenerWrapper(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        super(onMenuItemClickListener);
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        return ((MenuItem.OnMenuItemClickListener)this.mWrappedObject).onMenuItemClick(MenuItemWrapperICS.this.getMenuItemWrapper(menuItem));
    }
}
