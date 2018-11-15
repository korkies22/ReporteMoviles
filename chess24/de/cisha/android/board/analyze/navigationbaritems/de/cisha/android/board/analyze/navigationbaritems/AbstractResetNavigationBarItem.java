/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

public abstract class AbstractResetNavigationBarItem
extends AbstractNavigationBarItem {
    private MenuBarItem _menuBarItem;

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuBarItem == null) {
            this._menuBarItem = new MenuBarItem(context, context.getString(2131689527), 2131230848, 2131230849, -1);
            this._menuBarItem.setSelectable(false);
        }
        return this._menuBarItem;
    }
}
