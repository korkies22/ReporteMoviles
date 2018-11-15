/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.database;

import android.content.Context;
import de.cisha.android.board.database.AbstractDatabaseListFragment;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameInfo;

protected static class AbstractDatabaseListFragment.DeleteListOption
implements RookieMoreDialogFragment.ListOption {
    private GameInfo _info;
    private String _string;

    public AbstractDatabaseListFragment.DeleteListOption(Context context, GameInfo gameInfo) {
        this._string = context.getString(2131690082);
        this._info = gameInfo;
    }

    @Override
    public void executeAction() {
        ServiceProvider.getInstance().getGameService().deleteGameWithId(this._info.getGameID());
    }

    @Override
    public String getString() {
        return this._string;
    }
}
