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
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.util.PGNReader;
import java.util.Map;

class PGNListFragment.CopyGameListOption
extends AsyncTask<Void, Void, Void> {
    PGNListFragment.CopyGameListOption() {
    }

    protected /* varargs */ Void doInBackground(Void ... object) {
        object = new PGNReader();
        GameHolder gameHolder = new GameHolder(new Game());
        object.readSingleGame((String)CopyGameListOption.this.this$0._gameInfosToPGNStrings.get(CopyGameListOption.this._info), gameHolder, null);
        object = gameHolder.getRootMoveContainer().getGame();
        ServiceProvider.getInstance().getGameService().saveAnalysis((Game)object);
        return null;
    }

    protected void onPostExecute(Void void_) {
        CopyGameListOption.this.this$0.hideDialogWaiting();
        CopyGameListOption.this.this$0.showAnalysisSavedToast();
    }

    protected void onPreExecute() {
        CopyGameListOption.this.this$0.showDialogWaiting(false, false, "", null);
    }
}
