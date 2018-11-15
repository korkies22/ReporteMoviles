/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 */
package android.support.v7.view.menu;

import android.support.annotation.RestrictTo;
import android.support.v7.view.menu.MenuBuilder;
import android.view.MenuItem;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public static interface MenuBuilder.Callback {
    public boolean onMenuItemSelected(MenuBuilder var1, MenuItem var2);

    public void onMenuModeChange(MenuBuilder var1);
}
