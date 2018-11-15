/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.ServerApiCheckStatusResult;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

class ConfigServiceImpl
extends LoadCommandCallbackWithTimeout<ServerApiCheckStatusResult> {
    final /* synthetic */ LoadCommandCallback val$callback;

    ConfigServiceImpl(LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        if (aPIStatusCode == APIStatusCode.API_ERROR_API_NOT_VALID_PLEASE_UPDATE) {
            this.val$callback.loadingSucceded(new ServerApiCheckStatusResult(false, false, null, null));
            return;
        }
        this.val$callback.loadingFailed(aPIStatusCode, string, list, null);
    }

    @Override
    protected void succeded(ServerApiCheckStatusResult serverApiCheckStatusResult) {
        ConfigServiceImpl.this.setServerBaseAPIUrl(serverApiCheckStatusResult.getServerBaseURL());
        this.val$callback.loadingSucceded(serverApiCheckStatusResult);
    }
}
