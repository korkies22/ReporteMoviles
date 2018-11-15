/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.database;

import de.cisha.android.board.database.AbstractDatabaseListFragment;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.GameType;

protected class AbstractDatabaseListFragment.CopyGameListOption
implements RookieMoreDialogFragment.ListOption {
    private GameInfo _info;
    private String _string;

    public AbstractDatabaseListFragment.CopyGameListOption(GameInfo gameInfo, String string) {
        this._string = string;
        this._info = gameInfo;
    }

    @Override
    public void executeAction() {
        AbstractMoveContainer abstractMoveContainer = ServiceProvider.getInstance().getGameService().getGame(this._info.getGameID()).copy();
        abstractMoveContainer.setOriginalGameId(this._info.getGameID());
        abstractMoveContainer.setGameId(null);
        abstractMoveContainer.setType(GameType.ANALYZED_GAME);
        ServiceProvider.getInstance().getGameService().saveAnalysis((Game)abstractMoveContainer);
        AbstractDatabaseListFragment.this.showAnalysisSavedToast();
    }

    @Override
    public String getString() {
        return this._string;
    }
}
