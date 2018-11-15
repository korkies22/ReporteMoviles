/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.list.UpdatingList;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;

class TournamentListFragment
implements Runnable {
    TournamentListFragment() {
    }

    @Override
    public void run() {
        TournamentListFragment.this._initialized = true;
        TournamentListFragment.this.showDialogWaiting(false, true, "", null);
        TournamentListFragment.this._tournamentList.enableFooter();
        TournamentListFragment.this._tournamentList.updateStarted();
        ITournamentListService iTournamentListService = ServiceProvider.getInstance().getTournamentListService();
        int n = TournamentListFragment.this._listAdapter != null && TournamentListFragment.this._listAdapter.getCountOfValues() > 3 ? TournamentListFragment.this._listAdapter.getCountOfValues() : 20;
        iTournamentListService.getTournaments(0, n, (LoadCommandCallback<List<TournamentInfo>>)new LoadCommandCallbackWithTimeout<List<TournamentInfo>>(){

            @Override
            public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TournamentListFragment.this.hideDialogWaiting();
                        TournamentListFragment.this._tournamentList.updateFinished();
                        IErrorPresenter iErrorPresenter = TournamentListFragment.this.getErrorHandler();
                        if (iErrorPresenter != null) {
                            iErrorPresenter.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                                @Override
                                public void performReload() {
                                    TournamentListFragment.this.loadTournaments();
                                }
                            }, true);
                        }
                    }

                });
            }

            @Override
            public void succeded(final List<TournamentInfo> list) {
                TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TournamentListFragment.this.hideDialogWaiting();
                        TournamentListFragment.this.addAllTournamentInfos(list, true);
                        TournamentListFragment.this._tournamentList.updateFinished();
                        TournamentListFragment.this._listAdapter.notifyDataSetChanged();
                    }
                });
            }

        });
    }

}
