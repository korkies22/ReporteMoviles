/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import de.cisha.android.board.analyze.IAnalyzeDelegate;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

public class SwitchBoardNavigationItem
extends AbstractNavigationBarItem {
    private IAnalyzeDelegate _delegate;
    private MenuBarItem _menuBarItem;

    public SwitchBoardNavigationItem(IAnalyzeDelegate iAnalyzeDelegate) {
        this._delegate = iAnalyzeDelegate;
    }

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuBarItem == null) {
            this._menuBarItem = new MenuBarItem(context, context.getString(2131689529), 2131230855, 2131230856, -1);
            this._menuBarItem.setSelectable(false);
        }
        return this._menuBarItem;
    }

    @Override
    public void handleClick() {
        this._delegate.switchBoard();
    }
}
