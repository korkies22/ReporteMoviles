/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.ListView
 */
package android.support.v7.view.menu;

import android.support.annotation.NonNull;
import android.support.v7.view.menu.CascadingMenuPopup;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.MenuPopupWindow;
import android.widget.ListView;

private static class CascadingMenuPopup.CascadingMenuInfo {
    public final MenuBuilder menu;
    public final int position;
    public final MenuPopupWindow window;

    public CascadingMenuPopup.CascadingMenuInfo(@NonNull MenuPopupWindow menuPopupWindow, @NonNull MenuBuilder menuBuilder, int n) {
        this.window = menuPopupWindow;
        this.menu = menuBuilder;
        this.position = n;
    }

    public ListView getListView() {
        return this.window.getListView();
    }
}
