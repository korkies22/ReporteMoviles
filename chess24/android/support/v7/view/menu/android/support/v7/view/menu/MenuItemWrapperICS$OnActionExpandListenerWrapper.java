/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 */
package android.support.v7.view.menu;

import android.support.v7.view.menu.BaseWrapper;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.view.MenuItem;

private class MenuItemWrapperICS.OnActionExpandListenerWrapper
extends BaseWrapper<MenuItem.OnActionExpandListener>
implements MenuItem.OnActionExpandListener {
    MenuItemWrapperICS.OnActionExpandListenerWrapper(MenuItem.OnActionExpandListener onActionExpandListener) {
        super(onActionExpandListener);
    }

    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return ((MenuItem.OnActionExpandListener)this.mWrappedObject).onMenuItemActionCollapse(MenuItemWrapperICS.this.getMenuItemWrapper(menuItem));
    }

    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return ((MenuItem.OnActionExpandListener)this.mWrappedObject).onMenuItemActionExpand(MenuItemWrapperICS.this.getMenuItemWrapper(menuItem));
    }
}
