// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.android.board.analyze.AutoPlayDelegate;

public class AutoPlayNavigationBarItem extends AbstractNavigationBarItem
{
    private AutoPlayDelegate _delegate;
    private boolean _isPlaying;
    private MenuBarItem _menuBarItem;
    
    public AutoPlayNavigationBarItem(final AutoPlayDelegate delegate) {
        this._delegate = delegate;
    }
    
    @Override
    public MenuBarItem getMenuItem(final Context context) {
        if (this._menuBarItem == null) {
            (this._menuBarItem = new MenuBarItem(context, context.getResources().getString(2131689523), 2131231397, 2131231399, 2131231398)).setSelectable(false);
        }
        return this._menuBarItem;
    }
    
    @Override
    public void handleClick() {
        if (this._isPlaying) {
            this._delegate.stopAutoReplay();
            return;
        }
        this._delegate.autoReplayGame();
    }
    
    public void setPlaying(final boolean isPlaying) {
        this._isPlaying = isPlaying;
        if (this._isPlaying) {
            this._menuBarItem.setIconDrawablesForStates(this._menuBarItem.getResources().getDrawable(2131231394), this._menuBarItem.getResources().getDrawable(2131231396), this._menuBarItem.getResources().getDrawable(2131231395));
            this._menuBarItem.setTitle(this._menuBarItem.getResources().getString(2131689522));
            return;
        }
        this._menuBarItem.setIconDrawablesForStates(this._menuBarItem.getResources().getDrawable(2131231397), this._menuBarItem.getResources().getDrawable(2131231399), this._menuBarItem.getResources().getDrawable(2131231398));
        this._menuBarItem.setTitle(this._menuBarItem.getResources().getString(2131689523));
    }
}
