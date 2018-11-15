/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.tactics.TacticsExerciseFragment;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    TacticsExerciseFragment(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        16.this.this$0.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                16.this.this$0.loadNextExercise();
            }
        });
    }

}
