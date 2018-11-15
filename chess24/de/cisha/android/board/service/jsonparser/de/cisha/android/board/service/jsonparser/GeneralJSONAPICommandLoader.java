/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.util.HTTPHelperAsyn;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.HTTPHelper;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeneralJSONAPICommandLoader<E> {
    private static final String JSON_KEY_DATA_OBJECT = "data";
    private static final String JSON_KEY_ERRORS = "errors";
    private static final String JSON_KEY_STATUSCODE = "status";
    private static final String JSON_REQUEST_LANGUEAGE_KEY = "lang";
    private static final String JSON_REQUEST_VERSION_KEY = "v";
    private static final String LOG_TAG = "GeneralJSONAPICommandLoader";
    public final boolean DEBUG = ServiceProvider.getInstance().getConfigService().isDebugMode();
    private String _baseURL = null;
    private Collection<JSONOutputListener<E>> _listeners = new LinkedList<JSONOutputListener<E>>();

    public GeneralJSONAPICommandLoader() {
    }

    public GeneralJSONAPICommandLoader(String string) {
        this();
        this._baseURL = string;
    }

    private APIStatusCode APIStatusCodeForHttpCode(int n) {
        if (n != 429) {
            if (n != 503) {
                switch (n) {
                    default: {
                        return APIStatusCode.INTERNAL_UNKNOWN;
                    }
                    case 404: {
                        return APIStatusCode.API_ERROR_NOT_FOUND;
                    }
                    case 403: 
                }
                return APIStatusCode.API_ERROR_FORBIDDEN;
            }
            return APIStatusCode.API_ERROR_SERVICE_UNAVAILABLE;
        }
        return APIStatusCode.API_ERROR_TOO_MANY_REQUESTS;
    }

    private List<LoadFieldError> getErrors(JSONObject object) {
        Iterator iterator;
        LinkedList<LoadFieldError> linkedList = new LinkedList<LoadFieldError>();
        JSONObject jSONObject = object.optJSONObject(JSON_KEY_ERRORS);
        if (jSONObject != null && (iterator = jSONObject.keys()) != null) {
            while (iterator.hasNext()) {
                String string = (String)iterator.next();
                JSONArray jSONArray = jSONObject.optJSONArray(string);
                object = "";
                Object object2 = object;
                if (jSONArray != null) {
                    int n = 0;
                    do {
                        object2 = object;
                        if (n >= jSONArray.length()) break;
                        object2 = new StringBuilder();
                        object2.append((String)object);
                        object2.append(jSONArray.optString(n, ""));
                        object2.append("\n");
                        object = object2.toString();
                        ++n;
                    } while (true);
                }
                linkedList.add(new LoadFieldError(string, (String)object2));
            }
        }
        return linkedList;
    }

    private void loadAPICommand(URL uRL, LoadCommandCallback<E> object, Map<String, String> object2, final JSONAPIResultParser<E> jSONAPIResultParser, boolean bl, boolean bl2, Map<String, HTTPHelper.FileUploadInformation> map) {
        Object object3;
        TreeMap<String, String> map2 = object2;
        if (object2 == null) {
            map2 = new TreeMap<String, String>();
        }
        if (!map2.containsKey(JSON_REQUEST_VERSION_KEY)) {
            map2.put(JSON_REQUEST_VERSION_KEY, ServiceProvider.getInstance().getConfigService().getAPIVersion());
        } else {
            object2 = Logger.getInstance();
            object3 = new StringBuilder();
            object3.append("Version parameter already contained: v=");
            object3.append((String)map2.get(JSON_REQUEST_VERSION_KEY));
            object2.error(LOG_TAG, object3.toString());
        }
        if (!map2.containsKey(JSON_REQUEST_LANGUEAGE_KEY)) {
            map2.put(JSON_REQUEST_LANGUEAGE_KEY, Locale.getDefault().getLanguage());
        }
        if (bl) {
            object2 = ServiceProvider.getInstance().getLoginService().getAuthToken();
            if (object2 != null && object2.getUuid() != null && object2.isVerified()) {
                map2.put("authToken", object2.getUuid());
            } else {
                object.loadingFailed(APIStatusCode.INTERNAL_NOT_LOGGED_IN, "not authorized", new LinkedList<LoadFieldError>(), null);
                return;
            }
        }
        if (this.DEBUG) {
            object2 = "";
            for (Map.Entry object42 : map2.entrySet()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object2);
                stringBuilder.append("");
                stringBuilder.append((String)object42.getKey());
                stringBuilder.append("=");
                stringBuilder.append((String)object42.getValue());
                stringBuilder.append("\n");
                object2 = stringBuilder.toString();
            }
            object3 = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("parameters: \n");
            stringBuilder.append((String)object2);
            object3.debug(LOG_TAG, stringBuilder.toString());
        }
        object = new HTTPHelperAsyn.HTTPHelperDelegate((LoadCommandCallback)object){
            final /* synthetic */ LoadCommandCallback val$callback;
            {
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
                    logger.debug(GeneralJSONAPICommandLoader.LOG_TAG, object.toString());
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
                        int n2 = logger.getInt(GeneralJSONAPICommandLoader.JSON_KEY_STATUSCODE);
                        this.val$callback.loadingFailed(APIStatusCode.statusCodeFor(n2), string, (List<LoadFieldError>)object, null);
                        return;
                    }
                    this.val$callback.loadingFailed(GeneralJSONAPICommandLoader.this.APIStatusCodeForHttpCode(n), string, null, null);
                    return;
                }
                catch (JSONException jSONException) {
                    this.val$callback.loadingFailed(GeneralJSONAPICommandLoader.this.APIStatusCodeForHttpCode(n), string, null, null);
                    Logger.getInstance().debug(GeneralJSONAPICommandLoader.LOG_TAG, "invalid JSON from api:", (Throwable)jSONException);
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
                        logger.debug(GeneralJSONAPICommandLoader.LOG_TAG, object2.toString());
                    }
                    object = new JSONObject((String)object);
                    int n = object.getInt(GeneralJSONAPICommandLoader.JSON_KEY_STATUSCODE);
                    logger = object.optJSONObject(GeneralJSONAPICommandLoader.JSON_KEY_DATA_OBJECT);
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
                    if (jSONAPIResultParser == null) {
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
                        object = jSONAPIResultParser.parseResult((JSONObject)logger);
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
        };
        if (bl2) {
            HTTPHelperAsyn.loadUrlGet(uRL, map2, (HTTPHelperAsyn.HTTPHelperDelegate)object);
            return;
        }
        if (map != null && map.size() != 0) {
            HTTPHelperAsyn.uploadFileUrlPost(uRL, map2, map, (HTTPHelperAsyn.HTTPHelperDelegate)object);
            return;
        }
        HTTPHelperAsyn.loadUrlPost(uRL, map2, (HTTPHelperAsyn.HTTPHelperDelegate)object);
    }

    private void loadApiCommand(final LoadCommandCallback<E> loadCommandCallback, final String string, final Map<String, String> map, final JSONAPIResultParser<E> jSONAPIResultParser, final boolean bl, final boolean bl2, final Map<String, HTTPHelper.FileUploadInformation> map2) {
        Object object;
        if (this.DEBUG) {
            object = Logger.getInstance();
            Object object2 = new StringBuilder();
            object2.append("tryingtoSend API Command: ");
            object2.append(string);
            object.debug(LOG_TAG, object2.toString());
            object2 = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Method: ");
            object = bl2 ? "GET" : "POST";
            stringBuilder.append((String)object);
            object2.debug(LOG_TAG, stringBuilder.toString());
        }
        if (loadCommandCallback == null) {
            throw new IllegalArgumentException("parameter callback may not null");
        }
        if (this._baseURL == null) {
            ServiceProvider.getInstance().getConfigService().getMobileAPIURL((LoadCommandCallback<String>)new LoadCommandCallbackWithTimeout<String>(){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string2, List<LoadFieldError> list, JSONObject jSONObject) {
                    loadCommandCallback.loadingFailed(aPIStatusCode, string2, list, null);
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
                            object.append(string);
                            object = new URL(object.toString());
                            GeneralJSONAPICommandLoader.this.loadAPICommand((URL)object, loadCommandCallback, map, jSONAPIResultParser, bl, bl2, map2);
                            return;
                        }
                        catch (MalformedURLException malformedURLException) {
                            Logger.getInstance().debug(PlayzoneService.class.getName(), MalformedURLException.class.getName(), malformedURLException);
                            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_MALFORMED_API_URL, malformedURLException.getMessage(), new LinkedList<LoadFieldError>(), null);
                            return;
                        }
                    }
                    this.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "Error getting", null, null);
                }
            });
            return;
        }
        if (!this._baseURL.endsWith("/")) {
            object = new StringBuilder();
            object.append(this._baseURL);
            object.append("/");
            this._baseURL = object.toString();
        }
        try {
            object = new StringBuilder();
            object.append(this._baseURL);
            object.append(string);
            this.loadAPICommand(new URL(object.toString()), loadCommandCallback, map, jSONAPIResultParser, bl, bl2, map2);
            return;
        }
        catch (MalformedURLException malformedURLException) {
            Logger.getInstance().debug(PlayzoneService.class.getName(), MalformedURLException.class.getName(), malformedURLException);
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_MALFORMED_API_URL, malformedURLException.getMessage(), new LinkedList<LoadFieldError>(), null);
            return;
        }
    }

    private void notifiyListenerNewOutput(E e, JSONObject jSONObject) {
        Iterator<JSONOutputListener<E>> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().output(e, jSONObject);
        }
    }

    public void addOutputListener(JSONOutputListener<E> jSONOutputListener) {
        this._listeners.add(jSONOutputListener);
    }

    public void loadApiCommandGet(LoadCommandCallback<E> loadCommandCallback, String string, Map<String, String> map, JSONAPIResultParser<E> jSONAPIResultParser, boolean bl) {
        this.loadApiCommand(loadCommandCallback, string, map, jSONAPIResultParser, bl, true, null);
    }

    public void loadApiCommandPost(LoadCommandCallback<E> loadCommandCallback, String string, Map<String, String> map, JSONAPIResultParser<E> jSONAPIResultParser, boolean bl) {
        this.loadApiCommand(loadCommandCallback, string, map, jSONAPIResultParser, bl, false, null);
    }

    public void uploadFileToAPICommand(LoadCommandCallback<E> loadCommandCallback, String string, Map<String, String> map, Map<String, HTTPHelper.FileUploadInformation> map2, JSONAPIResultParser<E> jSONAPIResultParser, boolean bl) {
        this.loadApiCommand(loadCommandCallback, string, map, jSONAPIResultParser, bl, false, map2);
    }

    public static interface JSONOutputListener<E> {
        public void output(E var1, JSONObject var2);
    }

}
