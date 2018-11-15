/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.pgn;

import android.os.AsyncTask;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.pgn.PGNListFragment;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.util.PGNReader;
import java.util.Map;

protected class PGNListFragment.CopyGameListOption
implements RookieMoreDialogFragment.ListOption {
    private GameInfo _info;
    private String _string;

    public PGNListFragment.CopyGameListOption(GameInfo gameInfo) {
        this._string = PGNListFragment.this.getString(2131690105);
        this._info = gameInfo;
    }

    @Override
    public void executeAction() {
        new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... object) {
                object = new PGNReader();
                GameHolder gameHolder = new GameHolder(new Game());
                object.readSingleGame((String)PGNListFragment.this._gameInfosToPGNStrings.get(CopyGameListOption.this._info), gameHolder, null);
                object = gameHolder.getRootMoveContainer().getGame();
                ServiceProvider.getInstance().getGameService().saveAnalysis((Game)object);
                return null;
            }

            protected void onPostExecute(Void void_) {
                PGNListFragment.this.hideDialogWaiting();
                PGNListFragment.this.showAnalysisSavedToast();
            }

            protected void onPreExecute() {
                PGNListFragment.this.showDialogWaiting(false, false, "", null);
            }
        }.execute((Object[])new Void[]{null});
    }

    @Override
    public String getString() {
        return this._string;
    }

}
