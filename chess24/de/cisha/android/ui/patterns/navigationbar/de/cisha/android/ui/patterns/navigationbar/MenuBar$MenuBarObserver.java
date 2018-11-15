/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.patterns.navigationbar;

import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

public static interface MenuBar.MenuBarObserver {
    public void menuItemClicked(MenuBarItem var1);

    public void menuItemLongClicked(MenuBarItem var1);
}
