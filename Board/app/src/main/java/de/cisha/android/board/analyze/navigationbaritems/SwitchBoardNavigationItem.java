// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.android.board.analyze.IAnalyzeDelegate;

public class SwitchBoardNavigationItem extends AbstractNavigationBarItem
{
    private IAnalyzeDelegate _delegate;
    private MenuBarItem _menuBarItem;
    
    public SwitchBoardNavigationItem(final IAnalyzeDelegate delegate) {
        this._delegate = delegate;
    }
    
    @Override
    public MenuBarItem getMenuItem(final Context context) {
        if (this._menuBarItem == null) {
            (this._menuBarItem = new MenuBarItem(context, context.getString(2131689529), 2131230855, 2131230856, -1)).setSelectable(false);
        }
        return this._menuBarItem;
    }
    
    @Override
    public void handleClick() {
        this._delegate.switchBoard();
    }
}
