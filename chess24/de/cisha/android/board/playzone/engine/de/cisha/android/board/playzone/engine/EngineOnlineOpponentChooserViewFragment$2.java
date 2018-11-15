/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.engine;

import android.support.v4.widget.SwipeRefreshLayout;
import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class EngineOnlineOpponentChooserViewFragment
extends LoadCommandCallbackWithTimeout<List<OnlineEngineOpponent>> {
    EngineOnlineOpponentChooserViewFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                EngineOnlineOpponentChooserViewFragment.this._refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void succeded(final List<OnlineEngineOpponent> list) {
        EngineOnlineOpponentChooserViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                EngineOnlineOpponentChooserViewFragment.this._engineOnlineOpponentView.setOnlineOpponents(list);
                EngineOnlineOpponentChooserViewFragment.this._refreshLayout.setRefreshing(false);
            }
        });
    }

}
