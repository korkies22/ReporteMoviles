/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import android.os.Handler;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import java.util.List;
import org.json.JSONObject;

class TacticsStopFragment
extends LoadCommandCallbackWithTimeout<TacticsExerciseSession> {
    TacticsStopFragment() {
    }

    @Override
    protected void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        TacticsStopFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TacticsStopFragment.this.hideDialogWaiting();
                final IContentPresenter iContentPresenter = TacticsStopFragment.this.getContentPresenter();
                IErrorPresenter iErrorPresenter = TacticsStopFragment.this.getErrorHandler();
                if (aPIStatusCode == APIStatusCode.API_ERROR_NOT_SET && iContentPresenter != null) {
                    new Handler().post(new Runnable(){

                        @Override
                        public void run() {
                            iContentPresenter.popCurrentFragment();
                        }
                    });
                    return;
                }
                if (iErrorPresenter != null) {
                    iErrorPresenter.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                        @Override
                        public void performReload() {
                            TacticsStopFragment.this.loadSession();
                        }
                    }, true);
                }
            }

        });
    }

    @Override
    protected void succeded(final TacticsExerciseSession tacticsExerciseSession) {
        TacticsStopFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TacticsStopFragment.this.sessionLoaded(tacticsExerciseSession);
                TacticsStopFragment.this.hideDialogWaiting();
            }
        });
    }

}
