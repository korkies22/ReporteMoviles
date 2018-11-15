/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package android.support.v7.view.menu;

import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;

public static interface MenuView.ItemView {
    public MenuItemImpl getItemData();

    public void initialize(MenuItemImpl var1, int var2);

    public boolean prefersCondensedTitle();

    public void setCheckable(boolean var1);

    public void setChecked(boolean var1);

    public void setEnabled(boolean var1);

    public void setIcon(Drawable var1);

    public void setShortcut(boolean var1, char var2);

    public void setTitle(CharSequence var1);

    public boolean showsIcon();
}
