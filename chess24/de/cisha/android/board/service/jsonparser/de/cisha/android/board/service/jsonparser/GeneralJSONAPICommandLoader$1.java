/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.HTTPHelper;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

class GeneralJSONAPICommandLoader
extends LoadCommandCallbackWithTimeout<String> {
    final /* synthetic */ boolean val$addAuthToken;
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ String val$command;
    final /* synthetic */ boolean val$get;
    final /* synthetic */ Map val$optionalFiles;
    final /* synthetic */ Map val$optionalParams;
    final /* synthetic */ JSONAPIResultParser val$parser;

    GeneralJSONAPICommandLoader(String string, LoadCommandCallback loadCommandCallback, Map map, JSONAPIResultParser jSONAPIResultParser, boolean bl, boolean bl2, Map map2) {
        this.val$command = string;
        this.val$callback = loadCommandCallback;
        this.val$optionalParams = map;
        this.val$parser = jSONAPIResultParser;
        this.val$addAuthToken = bl;
        this.val$get = bl2;
        this.val$optionalFiles = map2;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callback.loadingFailed(aPIStatusCode, string, list, null);
    }

    @Override
    protected void succeded(String object) {
        if (object != null) {
            CharSequence charSequence = object;
            if (!object.endsWith("/")) {
                charSequence = new StringBuilder();
                charSequence.append((String)object);
                charSequence.append("/");
                charSequence = charSequence.toString();
            }
            try {
                object = new StringBuilder();
                object.append((String)charSequence);
                object.append(this.val$command);
                object = new URL(object.toString());
                GeneralJSONAPICommandLoader.this.loadAPICommand((URL)object, this.val$callback, this.val$optionalParams, this.val$parser, this.val$addAuthToken, this.val$get, this.val$optionalFiles);
                return;
            }
            catch (MalformedURLException malformedURLException) {
                Logger.getInstance().debug(PlayzoneService.class.getName(), MalformedURLException.class.getName(), malformedURLException);
                this.val$callback.loadingFailed(APIStatusCode.INTERNAL_MALFORMED_API_URL, malformedURLException.getMessage(), new LinkedList<LoadFieldError>(), null);
                return;
            }
        }
        this.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "Error getting", null, null);
    }
}
