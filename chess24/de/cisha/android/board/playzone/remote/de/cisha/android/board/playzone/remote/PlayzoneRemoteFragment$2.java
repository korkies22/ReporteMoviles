/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class PlayzoneRemoteFragment
implements LoadCommandCallback<NodeServerAddress> {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void loadingCancelled() {
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        PlayzoneRemoteFragment.this.reactOnLoadingFailure(aPIStatusCode, string, "pairingServerAdress");
    }

    @Override
    public void loadingSucceded(NodeServerAddress nodeServerAddress) {
        PlayzoneRemoteFragment.this._pairingServerAddress = nodeServerAddress;
        PlayzoneRemoteFragment.this.loadingFinished();
    }
}
