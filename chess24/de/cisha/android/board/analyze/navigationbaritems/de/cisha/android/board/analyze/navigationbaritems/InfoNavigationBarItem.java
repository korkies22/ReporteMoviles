/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.navigationbaritems.AbstractNavigationBarItem;
import de.cisha.android.board.analyze.view.GameInfoDialogFragment;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.MoveContainer;

public class InfoNavigationBarItem
extends AbstractNavigationBarItem {
    IContentPresenter _contentPresenter;
    GameInfoDialogFragment.GameInfoEditDelegate _delegate;
    GameHolder _gameHolder;
    private MenuBarItem _menuBarItem;

    public InfoNavigationBarItem(GameHolder gameHolder, GameInfoDialogFragment.GameInfoEditDelegate gameInfoEditDelegate, IContentPresenter iContentPresenter) {
        this._gameHolder = gameHolder;
        this._delegate = gameInfoEditDelegate;
        this._contentPresenter = iContentPresenter;
    }

    @Override
    public String getEventTrackingName() {
        return "Show Info";
    }

    @Override
    public MenuBarItem getMenuItem(Context context) {
        if (this._menuBarItem == null) {
            this._menuBarItem = new MenuBarItem(context, context.getString(2131689525), 2131230836, 2131230837, -1);
            this._menuBarItem.setSelectable(false);
        }
        return this._menuBarItem;
    }

    @Override
    public void handleClick() {
        MoveContainer moveContainer = this._gameHolder.getRootMoveContainer();
        if (moveContainer != null && (moveContainer = moveContainer.getGame()) != null) {
            GameInfoDialogFragment gameInfoDialogFragment = new GameInfoDialogFragment();
            gameInfoDialogFragment.setGame((Game)moveContainer);
            gameInfoDialogFragment.setEditDelegate(this._delegate);
            this._contentPresenter.showDialog(gameInfoDialogFragment);
        }
    }
}
