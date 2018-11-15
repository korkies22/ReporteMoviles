/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONObject
 */
package de.cisha.android.board.widget;

import android.content.Context;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.widget.TournamentWidgetService;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

class TournamentWidgetService.TournamentRemoteViewsFactory
implements LoadCommandCallback<List<TournamentInfo>> {
    TournamentWidgetService.TournamentRemoteViewsFactory() {
    }

    @Override
    public void loadingCancelled() {
        TournamentRemoteViewsFactory.this._updatePending = false;
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        TournamentRemoteViewsFactory.this._updatePending = false;
    }

    @Override
    public void loadingSucceded(List<TournamentInfo> list) {
        List<TournamentInfo> list2 = list;
        if (list == null) {
            list2 = new LinkedList<TournamentInfo>();
        }
        TournamentRemoteViewsFactory.this.this$0._tournamentInfos = list2;
        TournamentRemoteViewsFactory.this._lastUpdateTime = System.currentTimeMillis();
        Logger.getInstance().debug(TournamentWidgetService.TOURNAMENT_WIDGET_PROVIDER_LOG_TAG, "tournaments loaded - updateing widgets ");
        TournamentRemoteViewsFactory.this.this$0.refreshTournamentsList(TournamentRemoteViewsFactory.this._context);
        TournamentRemoteViewsFactory.this._updatePending = false;
    }
}
