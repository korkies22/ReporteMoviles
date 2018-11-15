/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  org.json.JSONException
 */
package de.cisha.android.board.analyze;

import android.os.AsyncTask;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameID;
import de.cisha.chess.util.Logger;
import java.io.Serializable;
import org.json.JSONException;

class AnalyzeFragment
extends AsyncTask<Void, Void, Game> {
    final /* synthetic */ Serializable val$gameIdSerialized;

    AnalyzeFragment(Serializable serializable) {
        this.val$gameIdSerialized = serializable;
    }

    protected /* varargs */ Game doInBackground(Void ... object) {
        try {
            object = ServiceProvider.getInstance().getGameService().getGame((GameID)this.val$gameIdSerialized);
            return object;
        }
        catch (ClassCastException classCastException) {
            Logger.getInstance().error(de.cisha.android.board.analyze.AnalyzeFragment.class.getName(), JSONException.class.getName(), classCastException);
            return null;
        }
    }

    protected void onPostExecute(Game game) {
        if (game != null) {
            AnalyzeFragment.this.setGame(game);
        }
        AnalyzeFragment.this.hideDialogWaiting();
    }

    protected void onPreExecute() {
        AnalyzeFragment.this.showDialogWaiting(false, true, "", null);
    }
}
