/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.ServerApiCheckStatusResult;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONServerAPIStatusParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class ConfigServiceImpl
extends LoadCommandCallbackWithTimeout<ServerApiCheckStatusResult> {
    final /* synthetic */ LoadCommandCallback val$callback;

    ConfigServiceImpl(LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
    }

    private void handleResult(LoadCommandCallback<String> loadCommandCallback, ServerApiCheckStatusResult serverApiCheckStatusResult) {
        if (serverApiCheckStatusResult != null && serverApiCheckStatusResult.isValid()) {
            ConfigServiceImpl.this.setServerBaseAPIUrl(serverApiCheckStatusResult.getServerBaseURL());
            loadCommandCallback.loadingSucceded(ConfigServiceImpl.this._serverBaseUrl);
            return;
        }
        loadCommandCallback.loadingFailed(APIStatusCode.API_ERROR_API_NOT_VALID_PLEASE_UPDATE, "API not valid anymore", null, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        if (aPIStatusCode != APIStatusCode.API_ERROR_SERVICE_UNAVAILABLE) {
            this.val$callback.loadingFailed(aPIStatusCode, string, list, jSONObject);
            return;
        }
        if (jSONObject == null) {
            this.val$callback.loadingFailed(aPIStatusCode, string, list, jSONObject);
            return;
        }
        try {
            ServerApiCheckStatusResult serverApiCheckStatusResult = new JSONServerAPIStatusParser().parseResult(jSONObject);
            this.handleResult(this.val$callback, serverApiCheckStatusResult);
            return;
        }
        catch (InvalidJsonForObjectException invalidJsonForObjectException) {}
        this.val$callback.loadingFailed(aPIStatusCode, string, list, jSONObject);
    }

    @Override
    protected void succeded(ServerApiCheckStatusResult serverApiCheckStatusResult) {
        this.handleResult(this.val$callback, serverApiCheckStatusResult);
    }
}
