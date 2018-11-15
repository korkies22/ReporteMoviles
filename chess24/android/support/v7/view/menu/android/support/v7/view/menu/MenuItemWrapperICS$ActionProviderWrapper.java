/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ActionProvider
 *  android.view.SubMenu
 *  android.view.View
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.view.SubMenu;
import android.view.View;

class MenuItemWrapperICS.ActionProviderWrapper
extends ActionProvider {
    final android.view.ActionProvider mInner;

    public MenuItemWrapperICS.ActionProviderWrapper(Context context, android.view.ActionProvider actionProvider) {
        super(context);
        this.mInner = actionProvider;
    }

    @Override
    public boolean hasSubMenu() {
        return this.mInner.hasSubMenu();
    }

    @Override
    public View onCreateActionView() {
        return this.mInner.onCreateActionView();
    }

    @Override
    public boolean onPerformDefaultAction() {
        return this.mInner.onPerformDefaultAction();
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        this.mInner.onPrepareSubMenu(MenuItemWrapperICS.this.getSubMenuWrapper(subMenu));
    }
}
