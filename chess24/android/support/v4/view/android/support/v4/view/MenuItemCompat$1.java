/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 */
package android.support.v4.view;

import android.support.v4.view.MenuItemCompat;
import android.view.MenuItem;

static final class MenuItemCompat
implements MenuItem.OnActionExpandListener {
    final /* synthetic */ MenuItemCompat.OnActionExpandListener val$listener;

    MenuItemCompat(MenuItemCompat.OnActionExpandListener onActionExpandListener) {
        this.val$listener = onActionExpandListener;
    }

    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return this.val$listener.onMenuItemActionCollapse(menuItem);
    }

    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return this.val$listener.onMenuItemActionExpand(menuItem);
    }
}
