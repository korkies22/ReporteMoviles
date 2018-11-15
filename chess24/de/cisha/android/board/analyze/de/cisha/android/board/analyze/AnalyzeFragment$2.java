/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.analyze;

import android.os.AsyncTask;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.chess.model.Game;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

class AnalyzeFragment
extends AsyncTask<Void, Void, Game> {
    final /* synthetic */ String val$jsonGameString;

    AnalyzeFragment(String string) {
        this.val$jsonGameString = string;
    }

    protected /* varargs */ Game doInBackground(Void ... object) {
        try {
            object = new JSONGameParser().parseResult(new JSONObject(this.val$jsonGameString));
            return object;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(this.getClass().getName(), JSONException.class.getName(), (Throwable)jSONException);
        }
        catch (InvalidJsonForObjectException invalidJsonForObjectException) {
            Logger.getInstance().debug(this.getClass().getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
        }
        return null;
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
