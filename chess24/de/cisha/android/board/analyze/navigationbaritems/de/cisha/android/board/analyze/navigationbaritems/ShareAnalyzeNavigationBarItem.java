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

public class ShareAnalyzeNavigationBarItem
extends AbstractNavigationBarItem {
    private MenuBarItem _menuBarItem;
    private Runnable _shareAction;

    public ShareAnalyzeNavigationBarItem(Runnable runnable) {
        this._shareAction = runnable;
    }

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuBarItem == null) {
            this._menuBarItem = new MenuBarItem(context, context.getString(2131690072), 2131231800, 2131231801, 2131231802);
            this._menuBarItem.setSelectable(false);
        }
        return this._menuBarItem;
    }

    @Override
    public void handleClick() {
        this._shareAction.run();
    }
}
