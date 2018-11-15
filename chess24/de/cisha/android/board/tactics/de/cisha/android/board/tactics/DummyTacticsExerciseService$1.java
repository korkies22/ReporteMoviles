/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class DummyTacticsExerciseService
extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ LoadCommandCallback val$callback;

    DummyTacticsExerciseService(LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
    }

    protected /* varargs */ Void doInBackground(Void ... arrvoid) {
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException interruptedException) {
            Logger.getInstance().debug(de.cisha.android.board.tactics.DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
        }
        return null;
    }

    protected void onPostExecute(Void void_) {
        if (DummyTacticsExerciseService.this._session != null) {
            this.val$callback.loadingSucceded(DummyTacticsExerciseService.this._session);
            return;
        }
        this.val$callback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "always call startNewSession before getCurrentSesion", null, null);
    }
}
