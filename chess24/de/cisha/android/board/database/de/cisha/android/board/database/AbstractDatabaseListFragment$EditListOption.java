/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.database;

import android.content.Context;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.EditGameInformationFragment;
import de.cisha.android.board.database.AbstractDatabaseListFragment;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameInfo;

protected class AbstractDatabaseListFragment.EditListOption
implements RookieMoreDialogFragment.ListOption {
    private GameInfo _info;
    private IContentPresenter _presenter;
    private String _string;

    public AbstractDatabaseListFragment.EditListOption(Context context, GameInfo gameInfo, IContentPresenter iContentPresenter) {
        this._presenter = iContentPresenter;
        this._string = context.getString(2131690083);
        this._info = gameInfo;
    }

    @Override
    public void executeAction() {
        EditGameInformationFragment editGameInformationFragment = new EditGameInformationFragment();
        editGameInformationFragment.setGame(ServiceProvider.getInstance().getGameService().getGame(this._info.getGameID()));
        this._presenter.showFragment(editGameInformationFragment, false, true);
    }

    @Override
    public String getString() {
        return this._string;
    }
}
