/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PairingSetupFragment;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class PairingSetupFragment
extends LoadCommandCallbackWithTimeout<List<TimeControl>> {
    PairingSetupFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
    }

    @Override
    protected void succeded(final List<TimeControl> list) {
        PairingSetupFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                PairingSetupFragment.this._availableClocks = list;
                PairingSetupFragment.this._recyclerView.setVisibility(0);
                PairingSetupFragment.this._timeControlLoadingView.setVisibility(8);
                PairingSetupFragment.this._timeControlsAdapter.notifyDataSetChanged();
            }
        });
    }

}
