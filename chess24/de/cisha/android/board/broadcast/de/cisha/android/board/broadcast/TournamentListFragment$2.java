/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
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
        TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentListFragment.this._tournamentList.updateFinished();
                IErrorPresenter iErrorPresenter = TournamentListFragment.this.getErrorHandler();
                if (iErrorPresenter != null) {
                    iErrorPresenter.showViewForErrorCode(aPIStatusCode, null);
                }
            }
        });
    }

    @Override
    public void succeded(final List<TournamentInfo> list) {
        TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentListFragment.this._tournamentList.updateFinished();
                if (list == null || list.size() == 0) {
                    TournamentListFragment.this._tournamentList.disableFooter();
                }
                TournamentListFragment.this.addAllTournamentInfos(list, false);
            }
        });
    }

}
