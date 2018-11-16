// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import de.cisha.chess.model.Game;
import de.cisha.chess.model.MoveContainer;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import android.content.Context;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.GameHolder;
import de.cisha.android.board.analyze.view.GameInfoDialogFragment;
import de.cisha.android.board.IContentPresenter;

public class InfoNavigationBarItem extends AbstractNavigationBarItem
{
    IContentPresenter _contentPresenter;
    GameInfoDialogFragment.GameInfoEditDelegate _delegate;
    GameHolder _gameHolder;
    private MenuBarItem _menuBarItem;
    
    public InfoNavigationBarItem(final GameHolder gameHolder, final GameInfoDialogFragment.GameInfoEditDelegate delegate, final IContentPresenter contentPresenter) {
        this._gameHolder = gameHolder;
        this._delegate = delegate;
        this._contentPresenter = contentPresenter;
    }
    
    @Override
    public String getEventTrackingName() {
        return "Show Info";
    }
    
    @Override
    public MenuBarItem getMenuItem(final Context context) {
        if (this._menuBarItem == null) {
            (this._menuBarItem = new MenuBarItem(context, context.getString(2131689525), 2131230836, 2131230837, -1)).setSelectable(false);
        }
        return this._menuBarItem;
    }
    
    @Override
    public void handleClick() {
        final MoveContainer rootMoveContainer = this._gameHolder.getRootMoveContainer();
        if (rootMoveContainer != null) {
            final Game game = rootMoveContainer.getGame();
            if (game != null) {
                final GameInfoDialogFragment gameInfoDialogFragment = new GameInfoDialogFragment();
                gameInfoDialogFragment.setGame(game);
                gameInfoDialogFragment.setEditDelegate(this._delegate);
                this._contentPresenter.showDialog(gameInfoDialogFragment);
            }
        }
    }
}
