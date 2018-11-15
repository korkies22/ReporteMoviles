/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 */
package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import de.cisha.android.board.analyze.AutoPlayDelegate;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

public class AutoPlayNavigationBarItem
extends AbstractNavigationBarItem {
    private AutoPlayDelegate _delegate;
    private boolean _isPlaying;
    private MenuBarItem _menuBarItem;

    public AutoPlayNavigationBarItem(AutoPlayDelegate autoPlayDelegate) {
        this._delegate = autoPlayDelegate;
    }

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuBarItem == null) {
            this._menuBarItem = new MenuBarItem(context, context.getResources().getString(2131689523), 2131231397, 2131231399, 2131231398);
            this._menuBarItem.setSelectable(false);
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

    public void setPlaying(boolean bl) {
        this._isPlaying = bl;
        if (this._isPlaying) {
            Drawable drawable = this._menuBarItem.getResources().getDrawable(2131231394);
            Drawable drawable2 = this._menuBarItem.getResources().getDrawable(2131231396);
            Drawable drawable3 = this._menuBarItem.getResources().getDrawable(2131231395);
            this._menuBarItem.setIconDrawablesForStates(drawable, drawable2, drawable3);
            this._menuBarItem.setTitle(this._menuBarItem.getResources().getString(2131689522));
            return;
        }
        Drawable drawable = this._menuBarItem.getResources().getDrawable(2131231397);
        Drawable drawable4 = this._menuBarItem.getResources().getDrawable(2131231399);
        Drawable drawable5 = this._menuBarItem.getResources().getDrawable(2131231398);
        this._menuBarItem.setIconDrawablesForStates(drawable, drawable4, drawable5);
        this._menuBarItem.setTitle(this._menuBarItem.getResources().getString(2131689523));
    }
}
