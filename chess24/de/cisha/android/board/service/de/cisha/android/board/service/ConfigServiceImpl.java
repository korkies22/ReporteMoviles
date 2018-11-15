/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.ServerAPIStatus;
import de.cisha.android.board.service.ServerApiCheckStatusResult;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONGetNodeServerParser;
import de.cisha.android.board.service.jsonparser.JSONServerAPIStatusParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

public class ConfigServiceImpl
implements IConfigService {
    private static final String BASE_URL_PREFERENCES = "baseURLPreferences";
    private static final boolean BASIC_AUTH = true;
    private static final String BASIC_AUTH_PASSWORT = "FqtMEKHMm2H?c;VvB37f";
    private static final String BASIC_AUTH_USERNAME = "mobilebeta";
    private static final String LAST_BASE_URL_LOOKUP_TIME_PREFERNECES_KEY = "LastBaseUrlLookupTime";
    private static final String SERVER_BASE_URL_PREFERENCE_KEY = "ServerBaseUrl";
    private static IConfigService _instance;
    private Context _context;
    private Date _lastCheckAPIRequest;
    private NodeServerAddress _pairingServerAdress;
    private String _serverBaseUrl;
    private String _versionNumber;

    protected ConfigServiceImpl(Context context) {
        this._context = context;
        if (context != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this.getVersionName(context));
            this._versionNumber = stringBuilder.toString();
            this.loadBaseURL();
        }
    }

    protected static IConfigService getInstance(Context object) {
        synchronized (ConfigServiceImpl.class) {
            if (_instance == null) {
                _instance = new ConfigServiceImpl((Context)object);
            }
            object = _instance;
            return object;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo((String)context.getPackageName(), (int)0).versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return 0;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getVersionName(Context object) {
        try {
            return object.getPackageManager().getPackageInfo((String)object.getPackageName(), (int)0).versionName;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return "";
        }
    }

    private boolean hasServerBaseUrl() {
        if (this._serverBaseUrl != null) {
            return true;
        }
        return false;
    }

    private void loadBaseURL() {
        SharedPreferences sharedPreferences = this._context.getSharedPreferences(BASE_URL_PREFERENCES, 0);
        long l = sharedPreferences.getLong(LAST_BASE_URL_LOOKUP_TIME_PREFERNECES_KEY, -1L);
        Date date = l == -1L ? null : new Date(l);
        this._lastCheckAPIRequest = date;
        this._serverBaseUrl = sharedPreferences.getString(SERVER_BASE_URL_PREFERENCE_KEY, null);
    }

    private void loadPairingServer(final LoadCommandCallback<NodeServerAddress> loadCommandCallback) {
        synchronized (this) {
            if (this._pairingServerAdress == null) {
                new GeneralJSONAPICommandLoader().loadApiCommandGet(new LoadCommandCallbackWithTimeout<NodeServerAddress>(){

                    @Override
                    public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                        if (loadCommandCallback != null) {
                            loadCommandCallback.loadingFailed(aPIStatusCode, string, null, null);
                        }
                    }

                    @Override
                    public void succeded(NodeServerAddress nodeServerAddress) {
                        ConfigServiceImpl.this._pairingServerAdress = nodeServerAddress;
                        if (loadCommandCallback != null) {
                            loadCommandCallback.loadingSucceded(nodeServerAddress);
                        }
                    }
                }, "mobileAPI/GetPairingServer", null, new JSONGetNodeServerParser(), true);
            } else if (loadCommandCallback != null) {
                loadCommandCallback.loadingSucceded(this._pairingServerAdress);
            }
            return;
        }
    }

    private void setServerBaseAPIUrl(String string) {
        if (string != null) {
            this._serverBaseUrl = string;
            this._lastCheckAPIRequest = new Date();
            this.storeBaseURL();
        }
    }

    private void storeBaseURL() {
        SharedPreferences.Editor editor = this._context.getSharedPreferences(BASE_URL_PREFERENCES, 0).edit();
        editor.putString(SERVER_BASE_URL_PREFERENCE_KEY, this._serverBaseUrl);
        editor.putLong(LAST_BASE_URL_LOOKUP_TIME_PREFERNECES_KEY, this._lastCheckAPIRequest.getTime());
        editor.commit();
    }

    @Override
    public void checkServerBaseUrl(final LoadCommandCallback<ServerAPIStatus> loadCommandCallback) {
        if (this._lastCheckAPIRequest != null && this._serverBaseUrl != null && new Date().getTime() - this._lastCheckAPIRequest.getTime() < 86400000L) {
            loadCommandCallback.loadingSucceded(new ServerApiCheckStatusResult(true, false, null, this._serverBaseUrl));
            return;
        }
        Object object = this.getServerBaseURL();
        object.endsWith("/");
        Serializable serializable = new StringBuilder();
        serializable.append((String)object);
        serializable.append("/");
        object = serializable.toString();
        serializable = new StringBuilder();
        serializable.append((String)object);
        serializable.append("api/mobile/");
        object = new GeneralJSONAPICommandLoader(serializable.toString());
        serializable = new TreeMap();
        try {
            serializable.put("applicationVersion", this._context.getPackageManager().getPackageInfo((String)this._context.getPackageName(), (int)0).versionName);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Logger.getInstance().error(ConfigServiceImpl.class.getName(), PackageManager.NameNotFoundException.class.getName(), (Throwable)nameNotFoundException);
        }
        serializable.put("platform", "Android");
        object.loadApiCommandGet(new LoadCommandCallbackWithTimeout<ServerApiCheckStatusResult>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                if (aPIStatusCode == APIStatusCode.API_ERROR_API_NOT_VALID_PLEASE_UPDATE) {
                    loadCommandCallback.loadingSucceded(new ServerApiCheckStatusResult(false, false, null, null));
                    return;
                }
                loadCommandCallback.loadingFailed(aPIStatusCode, string, list, null);
            }

            @Override
            protected void succeded(ServerApiCheckStatusResult serverApiCheckStatusResult) {
                ConfigServiceImpl.this.setServerBaseAPIUrl(serverApiCheckStatusResult.getServerBaseURL());
                loadCommandCallback.loadingSucceded(serverApiCheckStatusResult);
            }
        }, "statusAPI/check", (Map<String, String>)((Object)serializable), new JSONServerAPIStatusParser(), false);
    }

    @Override
    public String getAPIVersion() {
        return "1";
    }

    @Override
    public String getBasicAuthPasswort() {
        return BASIC_AUTH_PASSWORT;
    }

    @Override
    public String getBasicAuthUsername() {
        return BASIC_AUTH_USERNAME;
    }

    @Override
    public void getMobileAPIURL(final LoadCommandCallback<String> loadCommandCallback) {
        if (this.hasServerBaseUrl()) {
            loadCommandCallback.loadingSucceded(this._serverBaseUrl);
            return;
        }
        String string = this.getServerBaseURL();
        string.endsWith("/");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("/");
        string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("api/mobile/");
        new GeneralJSONAPICommandLoader(stringBuilder.toString()).loadApiCommandGet(new LoadCommandCallbackWithTimeout<ServerApiCheckStatusResult>(){

            private void handleResult(LoadCommandCallback<String> loadCommandCallback2, ServerApiCheckStatusResult serverApiCheckStatusResult) {
                if (serverApiCheckStatusResult != null && serverApiCheckStatusResult.isValid()) {
                    ConfigServiceImpl.this.setServerBaseAPIUrl(serverApiCheckStatusResult.getServerBaseURL());
                    loadCommandCallback2.loadingSucceded(ConfigServiceImpl.this._serverBaseUrl);
                    return;
                }
                loadCommandCallback2.loadingFailed(APIStatusCode.API_ERROR_API_NOT_VALID_PLEASE_UPDATE, "API not valid anymore", null, null);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                if (aPIStatusCode != APIStatusCode.API_ERROR_SERVICE_UNAVAILABLE) {
                    loadCommandCallback.loadingFailed(aPIStatusCode, string, list, jSONObject);
                    return;
                }
                if (jSONObject == null) {
                    loadCommandCallback.loadingFailed(aPIStatusCode, string, list, jSONObject);
                    return;
                }
                try {
                    ServerApiCheckStatusResult serverApiCheckStatusResult = new JSONServerAPIStatusParser().parseResult(jSONObject);
                    this.handleResult(loadCommandCallback, serverApiCheckStatusResult);
                    return;
                }
                catch (InvalidJsonForObjectException invalidJsonForObjectException) {}
                loadCommandCallback.loadingFailed(aPIStatusCode, string, list, jSONObject);
            }

            @Override
            protected void succeded(ServerApiCheckStatusResult serverApiCheckStatusResult) {
                this.handleResult(loadCommandCallback, serverApiCheckStatusResult);
            }
        }, "statusAPI/check", null, new JSONServerAPIStatusParser(), false);
    }

    @Override
    public void getPairingServerURL(LoadCommandCallback<NodeServerAddress> loadCommandCallback) {
        if (this._pairingServerAdress == null) {
            this.loadPairingServer(loadCommandCallback);
            return;
        }
        loadCommandCallback.loadingSucceded(this._pairingServerAdress);
    }

    protected String getServerBaseURL() {
        return this._context.getResources().getString(2131689655);
    }

    @Override
    public String getVersionString() {
        return this._versionNumber;
    }

    @Override
    public boolean isDebugMode() {
        return this._context.getResources().getBoolean(2131034116);
    }

    @Override
    public boolean useBasicAuth() {
        return true;
    }

}
