/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.tactics;

import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.util.Logger;

class DummyTacticsExerciseService
extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ TacticsExerciseUserSolution val$result;

    DummyTacticsExerciseService(LoadCommandCallback loadCommandCallback, TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this.val$callback = loadCommandCallback;
        this.val$result = tacticsExerciseUserSolution;
    }

    protected /* varargs */ Void doInBackground(Void ... arrvoid) {
        try {
            Thread.sleep(2500L);
        }
        catch (InterruptedException interruptedException) {
            Logger.getInstance().debug(de.cisha.android.board.tactics.DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
        }
        return null;
    }

    protected void onPostExecute(Void void_) {
        this.val$callback.loadingSucceded(this.val$result);
    }
}
