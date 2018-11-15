/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.broadcast.model;

import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.tactics.DummyTacticsExerciseService;
import de.cisha.chess.util.Logger;
import java.util.List;

class DummyTournamentListService
extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ List val$result;

    DummyTournamentListService(LoadCommandCallback loadCommandCallback, List list) {
        this.val$callback = loadCommandCallback;
        this.val$result = list;
    }

    protected /* varargs */ Void doInBackground(Void ... arrvoid) {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException interruptedException) {
            Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
        }
        return null;
    }

    protected void onPostExecute(Void void_) {
        this.val$callback.loadingSucceded(this.val$result);
    }
}
