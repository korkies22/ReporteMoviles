/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class PlayzoneRemoteFragment
implements LoadCommandCallback<List<TimeControl>> {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void loadingCancelled() {
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        PlayzoneRemoteFragment.this.reactOnLoadingFailure(aPIStatusCode, string, "times");
    }

    @Override
    public void loadingSucceded(List<TimeControl> list) {
        if (!PlayzoneRemoteFragment.this._cancelLoadingPlayzoneInfo) {
            PlayzoneRemoteFragment.this._times = list;
            PlayzoneRemoteFragment.this.loadingFinished();
        }
    }
}
