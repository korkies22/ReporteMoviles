/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.mainmenu.view;

import de.cisha.android.board.mainmenu.view.MenuItem;

public static interface MenuItem.MenuItemListener {
    public void highlight(boolean var1);

    public void menuTitleChanged();

    public void notificationUpdate(String var1);

    public void show(boolean var1);
}
