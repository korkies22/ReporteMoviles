/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.design.internal;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.view.MenuItem;
import android.view.View;

class BottomNavigationMenuView
implements View.OnClickListener {
    BottomNavigationMenuView() {
    }

    public void onClick(View object) {
        object = ((BottomNavigationItemView)object).getItemData();
        if (!BottomNavigationMenuView.this.mMenu.performItemAction((MenuItem)object, BottomNavigationMenuView.this.mPresenter, 0)) {
            object.setChecked(true);
        }
    }
}
