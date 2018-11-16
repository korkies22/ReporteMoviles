// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

public class ShareAnalyzeNavigationBarItem extends AbstractNavigationBarItem
{
    private MenuBarItem _menuBarItem;
    private Runnable _shareAction;
    
    public ShareAnalyzeNavigationBarItem(final Runnable shareAction) {
        this._shareAction = shareAction;
    }
    
    @Override
    public MenuBarItem getMenuItem(final Context context) {
        if (this._menuBarItem == null) {
            (this._menuBarItem = new MenuBarItem(context, context.getString(2131690072), 2131231800, 2131231801, 2131231802)).setSelectable(false);
        }
        return this._menuBarItem;
    }
    
    @Override
    public void handleClick() {
        this._shareAction.run();
    }
}
