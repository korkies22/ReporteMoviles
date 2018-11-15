/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.internal;

import android.support.design.internal.NavigationMenuPresenter;
import android.support.v7.view.menu.MenuItemImpl;

private static class NavigationMenuPresenter.NavigationMenuTextItem
implements NavigationMenuPresenter.NavigationMenuItem {
    private final MenuItemImpl mMenuItem;
    boolean needsEmptyIcon;

    NavigationMenuPresenter.NavigationMenuTextItem(MenuItemImpl menuItemImpl) {
        this.mMenuItem = menuItemImpl;
    }

    public MenuItemImpl getMenuItem() {
        return this.mMenuItem;
    }
}
