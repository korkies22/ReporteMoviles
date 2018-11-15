/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.view.menu;

import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;

public static interface MenuPresenter.Callback {
    public void onCloseMenu(MenuBuilder var1, boolean var2);

    public boolean onOpenSubMenu(MenuBuilder var1);
}
