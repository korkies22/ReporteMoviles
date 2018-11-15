/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.profile.TournamentsWidgetController;
import de.cisha.android.board.profile.view.TournamentsWidgetView;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class TournamentsWidgetController
extends LoadCommandCallbackWithTimeout<List<TournamentInfo>> {
    TournamentsWidgetController() {
    }

    private void notifyLoadingCompleted() {
        TournamentsWidgetController.this._loadingHelper.loadingCompleted(TournamentsWidgetController.this);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        TournamentsWidgetController.this.setState(TournamentsWidgetController.TournamentWidgetState.LOADING_FAILED);
        this.notifyLoadingCompleted();
    }

    @Override
    protected void succeded(final List<TournamentInfo> list) {
        TournamentsWidgetController.this._view.post(new Runnable(){

            @Override
            public void run() {
                TournamentsWidgetController.this.updateTournaments(list);
            }
        });
        this.notifyLoadingCompleted();
    }

}
