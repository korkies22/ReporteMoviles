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
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import java.util.List;
import org.json.JSONObject;

class TacticsExerciseFragment
extends LoadCommandCallbackWithTimeout<Rating> {
    final /* synthetic */ TacticsExerciseUserSolution val$solution;

    TacticsExerciseFragment(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this.val$solution = tacticsExerciseUserSolution;
    }

    @Override
    public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        TacticsExerciseFragment.this.hideResultToast();
        TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TacticsExerciseFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        TacticsExerciseFragment.this.showExerciseResult(19.this.val$solution.isCorrect());
                        TacticsExerciseFragment.this.loadExerciseSolved(19.this.val$solution);
                    }
                }, new IErrorPresenter.ICancelAction(){

                    @Override
                    public void cancelPressed() {
                        TacticsExerciseFragment.this.actionStop();
                    }
                });
            }

        });
    }

    @Override
    public void succeded(final Rating rating) {
        TacticsExerciseFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TacticsExerciseFragment.this.finishedLoadingNewRating(rating);
                TacticsExerciseFragment.this.loadNextExercise();
            }
        });
    }

}
