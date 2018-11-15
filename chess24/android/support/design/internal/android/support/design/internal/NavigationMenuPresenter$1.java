/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.design.internal;

import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.view.MenuItem;
import android.view.View;

class NavigationMenuPresenter
implements View.OnClickListener {
    NavigationMenuPresenter() {
    }

    public void onClick(View object) {
        object = (NavigationMenuItemView)object;
        NavigationMenuPresenter.this.setUpdateSuspended(true);
        object = object.getItemData();
        boolean bl = NavigationMenuPresenter.this.mMenu.performItemAction((MenuItem)object, NavigationMenuPresenter.this, 0);
        if (object != null && object.isCheckable() && bl) {
            NavigationMenuPresenter.this.mAdapter.setCheckedItem((MenuItemImpl)object);
        }
        NavigationMenuPresenter.this.setUpdateSuspended(false);
        NavigationMenuPresenter.this.updateMenuView(false);
    }
}
