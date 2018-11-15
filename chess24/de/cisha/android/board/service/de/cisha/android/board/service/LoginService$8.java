/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

class LoginService
extends LoadCommandCallbackWithTimeout<CishaUUID> {
    final /* synthetic */ LoadCommandCallbackWrapper val$callbackWrapper;
    final /* synthetic */ GeneralJSONAPICommandLoader val$loader;
    final /* synthetic */ Map val$params;

    LoginService(Map map, GeneralJSONAPICommandLoader generalJSONAPICommandLoader, LoadCommandCallbackWrapper loadCommandCallbackWrapper) {
        this.val$params = map;
        this.val$loader = generalJSONAPICommandLoader;
        this.val$callbackWrapper = loadCommandCallbackWrapper;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callbackWrapper.loadingFailed(aPIStatusCode, string, list, null);
    }

    @Override
    protected void succeded(CishaUUID cishaUUID) {
        this.val$params.put("duuid", cishaUUID.getUuid());
        this.val$loader.loadApiCommandPost(this.val$callbackWrapper, "mobileAPI/register", this.val$params, null, false);
    }
}
