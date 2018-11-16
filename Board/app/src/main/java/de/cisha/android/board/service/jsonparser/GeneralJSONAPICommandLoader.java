// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import java.net.MalformedURLException;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.chess.model.CishaUUID;
import org.json.JSONException;
import de.cisha.android.board.util.HTTPHelperAsyn;
import java.util.Locale;
import de.cisha.chess.util.Logger;
import java.util.TreeMap;
import de.cisha.chess.util.HTTPHelper;
import org.json.JSONArray;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
import java.util.Map;
import java.net.URL;
import java.util.LinkedList;
import de.cisha.android.board.service.ServiceProvider;
import java.util.Collection;

public class GeneralJSONAPICommandLoader<E>
{
    private static final String JSON_KEY_DATA_OBJECT = "data";
    private static final String JSON_KEY_ERRORS = "errors";
    private static final String JSON_KEY_STATUSCODE = "status";
    private static final String JSON_REQUEST_LANGUEAGE_KEY = "lang";
    private static final String JSON_REQUEST_VERSION_KEY = "v";
    private static final String LOG_TAG = "GeneralJSONAPICommandLoader";
    public final boolean DEBUG;
    private String _baseURL;
    private Collection<JSONOutputListener<E>> _listeners;
    
    public GeneralJSONAPICommandLoader() {
        this._baseURL = null;
        this.DEBUG = ServiceProvider.getInstance().getConfigService().isDebugMode();
        this._listeners = new LinkedList<JSONOutputListener<E>>();
    }
    
    public GeneralJSONAPICommandLoader(final String baseURL) {
        this();
        this._baseURL = baseURL;
    }
    
    private APIStatusCode APIStatusCodeForHttpCode(final int n) {
        if (n == 429) {
            return APIStatusCode.API_ERROR_TOO_MANY_REQUESTS;
        }
        if (n == 503) {
            return APIStatusCode.API_ERROR_SERVICE_UNAVAILABLE;
        }
        switch (n) {
            default: {
                return APIStatusCode.INTERNAL_UNKNOWN;
            }
            case 404: {
                return APIStatusCode.API_ERROR_NOT_FOUND;
            }
            case 403: {
                return APIStatusCode.API_ERROR_FORBIDDEN;
            }
        }
    }
    
    private List<LoadFieldError> getErrors(final JSONObject jsonObject) {
        final LinkedList<LoadFieldError> list = new LinkedList<LoadFieldError>();
        final JSONObject optJSONObject = jsonObject.optJSONObject("errors");
        if (optJSONObject != null) {
            final Iterator keys = optJSONObject.keys();
            if (keys != null) {
                while (keys.hasNext()) {
                    final String s = keys.next();
                    final JSONArray optJSONArray = optJSONObject.optJSONArray(s);
                    String s2;
                    String string = s2 = "";
                    if (optJSONArray != null) {
                        int n = 0;
                        while (true) {
                            s2 = string;
                            if (n >= optJSONArray.length()) {
                                break;
                            }
                            final StringBuilder sb = new StringBuilder();
                            sb.append(string);
                            sb.append(optJSONArray.optString(n, ""));
                            sb.append("\n");
                            string = sb.toString();
                            ++n;
                        }
                    }
                    list.add(new LoadFieldError(s, s2));
                }
            }
        }
        return list;
    }
    
    private void loadAPICommand(final URL url, final LoadCommandCallback<E> loadCommandCallback, final Map<String, String> map, final JSONAPIResultParser<E> jsonapiResultParser, final boolean b, final boolean b2, final Map<String, HTTPHelper.FileUploadInformation> map2) {
        Map<String, String> map3 = map;
        if (map == null) {
            map3 = new TreeMap<String, String>();
        }
        if (!map3.containsKey("v")) {
            map3.put("v", ServiceProvider.getInstance().getConfigService().getAPIVersion());
        }
        else {
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("Version parameter already contained: v=");
            sb.append(map3.get("v"));
            instance.error("GeneralJSONAPICommandLoader", sb.toString());
        }
        if (!map3.containsKey("lang")) {
            map3.put("lang", Locale.getDefault().getLanguage());
        }
        if (b) {
            final CishaUUID authToken = ServiceProvider.getInstance().getLoginService().getAuthToken();
            if (authToken == null || authToken.getUuid() == null || !authToken.isVerified()) {
                loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_NOT_LOGGED_IN, "not authorized", new LinkedList<LoadFieldError>(), null);
                return;
            }
            map3.put("authToken", authToken.getUuid());
        }
        if (this.DEBUG) {
            String string = "";
            for (final Map.Entry<String, String> entry : map3.entrySet()) {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(string);
                sb2.append("");
                sb2.append(entry.getKey());
                sb2.append("=");
                sb2.append(entry.getValue());
                sb2.append("\n");
                string = sb2.toString();
            }
            final Logger instance2 = Logger.getInstance();
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("parameters: \n");
            sb3.append(string);
            instance2.debug("GeneralJSONAPICommandLoader", sb3.toString());
        }
        final HTTPHelperAsyn.HTTPHelperDelegate httpHelperDelegate = new HTTPHelperAsyn.HTTPHelperDelegate() {
            @Override
            public void loadingFailed(final int n, final String s) {
                if (GeneralJSONAPICommandLoader.this.DEBUG) {
                    final Logger instance = Logger.getInstance();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("loading failed with code:");
                    sb.append(n);
                    sb.append(" \nbody: ");
                    sb.append(s);
                    instance.debug("GeneralJSONAPICommandLoader", sb.toString());
                }
                try {
                    final Logger instance2 = Logger.getInstance();
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append("Code ");
                    sb2.append(n);
                    sb2.append(": ");
                    sb2.append(s);
                    instance2.debug("httperror", sb2.toString());
                    final JSONObject jsonObject = new JSONObject(s);
                    final List access.200 = GeneralJSONAPICommandLoader.this.getErrors(jsonObject);
                    if (s != null && !s.trim().equals("")) {
                        loadCommandCallback.loadingFailed(APIStatusCode.statusCodeFor(jsonObject.getInt("status")), s, access.200, null);
                        return;
                    }
                    loadCommandCallback.loadingFailed(GeneralJSONAPICommandLoader.this.APIStatusCodeForHttpCode(n), s, null, null);
                }
                catch (JSONException ex) {
                    loadCommandCallback.loadingFailed(GeneralJSONAPICommandLoader.this.APIStatusCodeForHttpCode(n), s, null, null);
                    Logger.getInstance().debug("GeneralJSONAPICommandLoader", "invalid JSON from api:", (Throwable)ex);
                }
            }
            
            @Override
            public void urlLoaded(final String s) {
                while (true) {
                    while (true) {
                        Label_0477: {
                            try {
                                if (GeneralJSONAPICommandLoader.this.DEBUG) {
                                    final Logger instance = Logger.getInstance();
                                    final StringBuilder sb = new StringBuilder();
                                    sb.append("got result:");
                                    sb.append(s);
                                    instance.debug("GeneralJSONAPICommandLoader", sb.toString());
                                }
                                final JSONObject jsonObject = new JSONObject(s);
                                final int int1 = jsonObject.getInt("status");
                                final JSONObject optJSONObject = jsonObject.optJSONObject("data");
                                if (int1 != 0) {
                                    final List access.200 = GeneralJSONAPICommandLoader.this.getErrors(jsonObject);
                                    final LoadCommandCallback val.callback = loadCommandCallback;
                                    final APIStatusCode statusCode = APIStatusCode.statusCodeFor(int1);
                                    final StringBuilder sb2 = new StringBuilder();
                                    sb2.append("error ");
                                    sb2.append(int1);
                                    val.callback.loadingFailed(statusCode, sb2.toString(), access.200, optJSONObject);
                                    return;
                                }
                                if (jsonapiResultParser == null) {
                                    loadCommandCallback.loadingSucceded(null);
                                    return;
                                }
                                if (optJSONObject == null) {
                                    final LoadCommandCallback val.callback2 = loadCommandCallback;
                                    final APIStatusCode internal_INCORRECT_SERVER_JSON_NO_DATA_OBJECT = APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON_NO_DATA_OBJECT;
                                    final StringBuilder sb3 = new StringBuilder();
                                    sb3.append("invalid JSON: ");
                                    sb3.append(jsonObject);
                                    val.callback2.loadingFailed(internal_INCORRECT_SERVER_JSON_NO_DATA_OBJECT, sb3.toString(), new LinkedList(), optJSONObject);
                                    return;
                                }
                                Object result;
                                try {
                                    result = jsonapiResultParser.parseResult(optJSONObject);
                                }
                                catch (Exception ex) {
                                    final LoadCommandCallback val.callback3 = loadCommandCallback;
                                    final APIStatusCode internal_INCORRECT_SERVER_JSON = APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON;
                                    final StringBuilder sb4 = new StringBuilder();
                                    sb4.append("invalid JSON: ");
                                    sb4.append(ex.getMessage());
                                    val.callback3.loadingFailed(internal_INCORRECT_SERVER_JSON, sb4.toString(), new LinkedList(), null);
                                    Logger.getInstance().error(this.getClass().getName(), "Unknown Error parsing:", ex);
                                    break Label_0477;
                                }
                                catch (InvalidJsonForObjectException ex2) {
                                    final LoadCommandCallback val.callback4 = loadCommandCallback;
                                    final APIStatusCode internal_INCORRECT_SERVER_JSON2 = APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON;
                                    final StringBuilder sb5 = new StringBuilder();
                                    sb5.append("invalid JSON: ");
                                    sb5.append(ex2.getMessage());
                                    val.callback4.loadingFailed(internal_INCORRECT_SERVER_JSON2, sb5.toString(), new LinkedList(), optJSONObject);
                                    Logger.getInstance().debug(this.getClass().getName(), InvalidJsonForObjectException.class.getName(), ex2);
                                    break Label_0477;
                                }
                                if (result != null) {
                                    loadCommandCallback.loadingSucceded(result);
                                    GeneralJSONAPICommandLoader.this.notifiyListenerNewOutput(result, optJSONObject);
                                    return;
                                }
                                break;
                            }
                            catch (JSONException ex3) {
                                final LoadCommandCallback val.callback5 = loadCommandCallback;
                                final APIStatusCode internal_INCORRECT_SERVER_JSON3 = APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON;
                                final StringBuilder sb6 = new StringBuilder();
                                sb6.append("JSONException: ");
                                sb6.append(ex3.getMessage());
                                val.callback5.loadingFailed(internal_INCORRECT_SERVER_JSON3, sb6.toString(), new LinkedList(), null);
                                return;
                            }
                        }
                        Object result = null;
                        continue;
                    }
                }
            }
        };
        if (b2) {
            HTTPHelperAsyn.loadUrlGet(url, map3, (HTTPHelperAsyn.HTTPHelperDelegate)httpHelperDelegate);
            return;
        }
        if (map2 != null && map2.size() != 0) {
            HTTPHelperAsyn.uploadFileUrlPost(url, map3, map2, (HTTPHelperAsyn.HTTPHelperDelegate)httpHelperDelegate);
            return;
        }
        HTTPHelperAsyn.loadUrlPost(url, map3, (HTTPHelperAsyn.HTTPHelperDelegate)httpHelperDelegate);
    }
    
    private void loadApiCommand(final LoadCommandCallback<E> loadCommandCallback, final String s, final Map<String, String> map, final JSONAPIResultParser<E> jsonapiResultParser, final boolean b, final boolean b2, final Map<String, HTTPHelper.FileUploadInformation> map2) {
        if (this.DEBUG) {
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("tryingtoSend API Command: ");
            sb.append(s);
            instance.debug("GeneralJSONAPICommandLoader", sb.toString());
            final Logger instance2 = Logger.getInstance();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("Method: ");
            String s2;
            if (b2) {
                s2 = "GET";
            }
            else {
                s2 = "POST";
            }
            sb2.append(s2);
            instance2.debug("GeneralJSONAPICommandLoader", sb2.toString());
        }
        if (loadCommandCallback == null) {
            throw new IllegalArgumentException("parameter callback may not null");
        }
        if (this._baseURL == null) {
            ServiceProvider.getInstance().getConfigService().getMobileAPIURL(new LoadCommandCallbackWithTimeout<String>() {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    loadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
                }
                
                @Override
                protected void succeded(final String s) {
                    if (s != null) {
                        String string = s;
                        if (!s.endsWith("/")) {
                            final StringBuilder sb = new StringBuilder();
                            sb.append(s);
                            sb.append("/");
                            string = sb.toString();
                        }
                        try {
                            final StringBuilder sb2 = new StringBuilder();
                            sb2.append(string);
                            sb2.append(s);
                            GeneralJSONAPICommandLoader.this.loadAPICommand(new URL(sb2.toString()), loadCommandCallback, map, jsonapiResultParser, b, b2, map2);
                            return;
                        }
                        catch (MalformedURLException ex) {
                            Logger.getInstance().debug(PlayzoneService.class.getName(), MalformedURLException.class.getName(), ex);
                            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_MALFORMED_API_URL, ex.getMessage(), new LinkedList(), null);
                            return;
                        }
                    }
                    this.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "Error getting", null, null);
                }
            });
            return;
        }
        if (!this._baseURL.endsWith("/")) {
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(this._baseURL);
            sb3.append("/");
            this._baseURL = sb3.toString();
        }
        try {
            final StringBuilder sb4 = new StringBuilder();
            sb4.append(this._baseURL);
            sb4.append(s);
            this.loadAPICommand(new URL(sb4.toString()), loadCommandCallback, map, jsonapiResultParser, b, b2, map2);
        }
        catch (MalformedURLException ex) {
            Logger.getInstance().debug(PlayzoneService.class.getName(), MalformedURLException.class.getName(), ex);
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_MALFORMED_API_URL, ex.getMessage(), new LinkedList<LoadFieldError>(), null);
        }
    }
    
    private void notifiyListenerNewOutput(final E e, final JSONObject jsonObject) {
        final Iterator<JSONOutputListener<E>> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().output(e, jsonObject);
        }
    }
    
    public void addOutputListener(final JSONOutputListener<E> jsonOutputListener) {
        this._listeners.add(jsonOutputListener);
    }
    
    public void loadApiCommandGet(final LoadCommandCallback<E> loadCommandCallback, final String s, final Map<String, String> map, final JSONAPIResultParser<E> jsonapiResultParser, final boolean b) {
        this.loadApiCommand(loadCommandCallback, s, map, jsonapiResultParser, b, true, null);
    }
    
    public void loadApiCommandPost(final LoadCommandCallback<E> loadCommandCallback, final String s, final Map<String, String> map, final JSONAPIResultParser<E> jsonapiResultParser, final boolean b) {
        this.loadApiCommand(loadCommandCallback, s, map, jsonapiResultParser, b, false, null);
    }
    
    public void uploadFileToAPICommand(final LoadCommandCallback<E> loadCommandCallback, final String s, final Map<String, String> map, final Map<String, HTTPHelper.FileUploadInformation> map2, final JSONAPIResultParser<E> jsonapiResultParser, final boolean b) {
        this.loadApiCommand(loadCommandCallback, s, map, jsonapiResultParser, b, false, map2);
    }
    
    public interface JSONOutputListener<E>
    {
        void output(final E p0, final JSONObject p1);
    }
}
