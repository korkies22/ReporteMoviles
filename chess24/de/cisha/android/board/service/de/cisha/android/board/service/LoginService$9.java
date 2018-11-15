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
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

class LoginService
extends LoadCommandCallbackWithTimeout<CishaUUID> {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ String val$email;

    LoginService(String string, LoadCommandCallback loadCommandCallback) {
        this.val$email = string;
        this.val$callback = loadCommandCallback;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callback.loadingFailed(aPIStatusCode, string, list, null);
    }

    @Override
    protected void succeded(CishaUUID cishaUUID) {
        GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("duuid", cishaUUID.getUuid());
        treeMap.put(de.cisha.android.board.service.LoginService.FACEBOOK_PERMISSION_EMAIL, this.val$email);
        generalJSONAPICommandLoader.loadApiCommandPost(this.val$callback, "mobileAPI/lostPassword", treeMap, null, false);
    }
}
