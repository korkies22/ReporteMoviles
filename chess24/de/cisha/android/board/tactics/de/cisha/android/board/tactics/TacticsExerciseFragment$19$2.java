/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.tactics.TacticsExerciseFragment;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    TacticsExerciseFragment(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        19.this.this$0.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                19.this.this$0.showExerciseResult(19.this.val$solution.isCorrect());
                19.this.this$0.loadExerciseSolved(19.this.val$solution);
            }
        }, new IErrorPresenter.ICancelAction(){

            @Override
            public void cancelPressed() {
                19.this.this$0.actionStop();
            }
        });
    }

}
