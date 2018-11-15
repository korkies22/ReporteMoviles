/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.util.HTTPHelperAsyn;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class GeneralJSONAPICommandLoader
implements HTTPHelperAsyn.HTTPHelperDelegate {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ JSONAPIResultParser val$parser;

    GeneralJSONAPICommandLoader(JSONAPIResultParser jSONAPIResultParser, LoadCommandCallback loadCommandCallback) {
        this.val$parser = jSONAPIResultParser;
        this.val$callback = loadCommandCallback;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void loadingFailed(int n, String string) {
        Logger logger;
        Object object;
        if (GeneralJSONAPICommandLoader.this.DEBUG) {
            logger = Logger.getInstance();
            object = new StringBuilder();
            object.append("loading failed with code:");
            object.append(n);
            object.append(" \nbody: ");
            object.append(string);
            logger.debug(de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader.LOG_TAG, object.toString());
        }
        try {
            logger = Logger.getInstance();
            object = new StringBuilder();
            object.append("Code ");
            object.append(n);
            object.append(": ");
            object.append(string);
            logger.debug("httperror", object.toString());
            logger = new JSONObject(string);
            object = GeneralJSONAPICommandLoader.this.getErrors((JSONObject)logger);
            if (string != null && !string.trim().equals("")) {
                int n2 = logger.getInt(de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader.JSON_KEY_STATUSCODE);
                this.val$callback.loadingFailed(APIStatusCode.statusCodeFor(n2), string, (List<LoadFieldError>)object, null);
                return;
            }
            this.val$callback.loadingFailed(GeneralJSONAPICommandLoader.this.APIStatusCodeForHttpCode(n), string, null, null);
            return;
        }
        catch (JSONException jSONException) {
            this.val$callback.loadingFailed(GeneralJSONAPICommandLoader.this.APIStatusCodeForHttpCode(n), string, null, null);
            Logger.getInstance().debug(de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader.LOG_TAG, "invalid JSON from api:", (Throwable)jSONException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void urlLoaded(String object) {
        Logger logger;
        try {
            Object object2;
            if (GeneralJSONAPICommandLoader.this.DEBUG) {
                logger = Logger.getInstance();
                object2 = new StringBuilder();
                object2.append("got result:");
                object2.append((String)object);
                logger.debug(de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader.LOG_TAG, object2.toString());
            }
            object = new JSONObject((String)object);
            int n = object.getInt(de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader.JSON_KEY_STATUSCODE);
            logger = object.optJSONObject(de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader.JSON_KEY_DATA_OBJECT);
            if (n != 0) {
                object = GeneralJSONAPICommandLoader.this.getErrors((JSONObject)object);
                object2 = this.val$callback;
                APIStatusCode aPIStatusCode = APIStatusCode.statusCodeFor(n);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("error ");
                stringBuilder.append(n);
                object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), (List<LoadFieldError>)object, (JSONObject)logger);
                return;
            }
            if (this.val$parser == null) {
                this.val$callback.loadingSucceded(null);
                return;
            }
            if (logger == null) {
                object2 = this.val$callback;
                APIStatusCode aPIStatusCode = APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON_NO_DATA_OBJECT;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid JSON: ");
                stringBuilder.append(object);
                object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), new LinkedList<LoadFieldError>(), (JSONObject)logger);
                return;
            }
            try {
                object = this.val$parser.parseResult((JSONObject)logger);
            }
            catch (Exception exception) {
                object2 = this.val$callback;
                APIStatusCode aPIStatusCode = APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid JSON: ");
                stringBuilder.append(exception.getMessage());
                object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), new LinkedList<LoadFieldError>(), null);
                Logger.getInstance().error(this.getClass().getName(), "Unknown Error parsing:", exception);
                return;
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                object2 = this.val$callback;
                APIStatusCode aPIStatusCode = APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid JSON: ");
                stringBuilder.append(invalidJsonForObjectException.getMessage());
                object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), new LinkedList<LoadFieldError>(), (JSONObject)logger);
                Logger.getInstance().debug(this.getClass().getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                return;
            }
        }
        catch (JSONException jSONException) {
            LoadCommandCallback loadCommandCallback = this.val$callback;
            APIStatusCode aPIStatusCode = APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("JSONException: ");
            stringBuilder.append(jSONException.getMessage());
            loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), new LinkedList<LoadFieldError>(), null);
            return;
        }
        if (object == null) return;
        this.val$callback.loadingSucceded(object);
        GeneralJSONAPICommandLoader.this.notifiyListenerNewOutput(object, (JSONObject)logger);
    }
}
