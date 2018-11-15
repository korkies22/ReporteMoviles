/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.tactics;

import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.chess.util.Logger;

class DummyTacticsExerciseService
extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ LoadCommandCallback val$callback;

    DummyTacticsExerciseService(LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
    }

    protected /* varargs */ Void doInBackground(Void ... arrvoid) {
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException interruptedException) {
            Logger.getInstance().debug(de.cisha.android.board.tactics.DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
        }
        return null;
    }

    protected void onPostExecute(Void void_) {
        this.val$callback.loadingSucceded(new ITacticsExerciseService.TacticsTrainerInfo(true, 5, 0L));
    }
}
