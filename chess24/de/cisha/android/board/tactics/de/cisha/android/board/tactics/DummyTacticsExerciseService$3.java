/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.tactics;

import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.util.Logger;

class DummyTacticsExerciseService
extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ LoadCommandCallback val$newRatingCallback;
    final /* synthetic */ TacticsExerciseUserSolution val$solution;

    DummyTacticsExerciseService(TacticsExerciseUserSolution tacticsExerciseUserSolution, LoadCommandCallback loadCommandCallback) {
        this.val$solution = tacticsExerciseUserSolution;
        this.val$newRatingCallback = loadCommandCallback;
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
        if (this.val$solution.isCorrect()) {
            DummyTacticsExerciseService.this._rating = new Rating(DummyTacticsExerciseService.this._rating.getRating() + 23);
        } else {
            DummyTacticsExerciseService.this._rating = new Rating(DummyTacticsExerciseService.this._rating.getRating() - 23);
        }
        this.val$newRatingCallback.loadingSucceded(DummyTacticsExerciseService.this._rating);
    }
}
