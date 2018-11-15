/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.model.exercise.TacticsGameHolder;
import java.util.List;
import org.json.JSONObject;

class TacticsExerciseFragment
extends LoadCommandCallbackWithTimeout<TacticsExerciseUserSolution> {
    TacticsExerciseFragment() {
    }

    @Override
    public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TacticsExerciseFragment.this.hideWaitingTimer();
                TacticsExerciseFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        TacticsExerciseFragment.this.loadFirstExercise();
                    }
                }, true);
            }

        });
    }

    @Override
    public void succeded(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TacticsExerciseFragment.this.hideWaitingTimer();
                TacticsExerciseFragment.this._gameHolder = new TacticsGameHolder(tacticsExerciseUserSolution);
                TacticsExerciseFragment.this.initGameHolderObservers();
                TacticsExerciseFragment.this.adjustBoardForCurrentExercise();
                TacticsExerciseFragment.this.startExerciseTimer();
            }
        });
    }

}
