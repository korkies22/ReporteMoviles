/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.view.menu;

import android.support.annotation.RestrictTo;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public static interface MenuBuilder.ItemInvoker {
    public boolean invokeItem(MenuItemImpl var1);
}
