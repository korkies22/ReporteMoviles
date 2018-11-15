/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.list.UpdatingList;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;

class TournamentListFragment
extends LoadCommandCallbackWithTimeout<List<TournamentInfo>> {
    TournamentListFragment() {
    }

    @Override
    public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        1.this.this$0.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                1.this.this$0.hideDialogWaiting();
                1.this.this$0._tournamentList.updateFinished();
                IErrorPresenter iErrorPresenter = 1.this.this$0.getErrorHandler();
                if (iErrorPresenter != null) {
                    iErrorPresenter.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                        @Override
                        public void performReload() {
                            1.this.this$0.loadTournaments();
                        }
                    }, true);
                }
            }

        });
    }

    @Override
    public void succeded(final List<TournamentInfo> list) {
        1.this.this$0.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                1.this.this$0.hideDialogWaiting();
                1.this.this$0.addAllTournamentInfos(list, true);
                1.this.this$0._tournamentList.updateFinished();
                1.this.this$0._listAdapter.notifyDataSetChanged();
            }
        });
    }

}
