/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class ConfigServiceImpl
extends LoadCommandCallbackWithTimeout<NodeServerAddress> {
    final /* synthetic */ LoadCommandCallback val$optionalCallback;

    ConfigServiceImpl(LoadCommandCallback loadCommandCallback) {
        this.val$optionalCallback = loadCommandCallback;
    }

    @Override
    public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        if (this.val$optionalCallback != null) {
            this.val$optionalCallback.loadingFailed(aPIStatusCode, string, null, null);
        }
    }

    @Override
    public void succeded(NodeServerAddress nodeServerAddress) {
        ConfigServiceImpl.this._pairingServerAdress = nodeServerAddress;
        if (this.val$optionalCallback != null) {
            this.val$optionalCallback.loadingSucceded(nodeServerAddress);
        }
    }
}
