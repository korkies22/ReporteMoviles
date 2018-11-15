/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.view.menu;

import android.support.v4.view.ActionProvider;
import android.support.v7.view.menu.MenuBuilder;

class MenuItemImpl
implements ActionProvider.VisibilityListener {
    MenuItemImpl() {
    }

    @Override
    public void onActionProviderVisibilityChanged(boolean bl) {
        MenuItemImpl.this.mMenu.onItemVisibleChanged(MenuItemImpl.this);
    }
}
