// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONServerAPIStatusParser;
import de.cisha.chess.util.Logger;
import java.util.TreeMap;
import android.content.SharedPreferences.Editor;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Map;
import de.cisha.android.board.service.jsonparser.JSONGetNodeServerParser;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.Date;
import android.content.Context;

public class ConfigServiceImpl implements IConfigService
{
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
    
    protected ConfigServiceImpl(final Context context) {
        this._context = context;
        if (context != null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this.getVersionName(context));
            this._versionNumber = sb.toString();
            this.loadBaseURL();
        }
    }
    
    protected static IConfigService getInstance(final Context context) {
        synchronized (ConfigServiceImpl.class) {
            if (ConfigServiceImpl._instance == null) {
                ConfigServiceImpl._instance = new ConfigServiceImpl(context);
            }
            return ConfigServiceImpl._instance;
        }
    }
    
    private int getVersion(final Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        catch (PackageManager.NameNotFoundException ex) {
            return 0;
        }
    }
    
    private String getVersionName(final Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException ex) {
            return "";
        }
    }
    
    private boolean hasServerBaseUrl() {
        return this._serverBaseUrl != null;
    }
    
    private void loadBaseURL() {
        final SharedPreferences sharedPreferences = this._context.getSharedPreferences("baseURLPreferences", 0);
        final long long1 = sharedPreferences.getLong("LastBaseUrlLookupTime", -1L);
        Date lastCheckAPIRequest;
        if (long1 == -1L) {
            lastCheckAPIRequest = null;
        }
        else {
            lastCheckAPIRequest = new Date(long1);
        }
        this._lastCheckAPIRequest = lastCheckAPIRequest;
        this._serverBaseUrl = sharedPreferences.getString("ServerBaseUrl", (String)null);
    }
    
    private void loadPairingServer(final LoadCommandCallback<NodeServerAddress> loadCommandCallback) {
        synchronized (this) {
            if (this._pairingServerAdress == null) {
                new GeneralJSONAPICommandLoader().loadApiCommandGet((LoadCommandCallback)new LoadCommandCallbackWithTimeout<NodeServerAddress>() {
                    public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        if (loadCommandCallback != null) {
                            loadCommandCallback.loadingFailed(apiStatusCode, s, null, null);
                        }
                    }
                    
                    public void succeded(final NodeServerAddress nodeServerAddress) {
                        ConfigServiceImpl.this._pairingServerAdress = nodeServerAddress;
                        if (loadCommandCallback != null) {
                            loadCommandCallback.loadingSucceded(nodeServerAddress);
                        }
                    }
                }, "mobileAPI/GetPairingServer", null, (JSONAPIResultParser)new JSONGetNodeServerParser(), true);
            }
            else if (loadCommandCallback != null) {
                loadCommandCallback.loadingSucceded(this._pairingServerAdress);
            }
        }
    }
    
    private void setServerBaseAPIUrl(final String serverBaseUrl) {
        if (serverBaseUrl != null) {
            this._serverBaseUrl = serverBaseUrl;
            this._lastCheckAPIRequest = new Date();
            this.storeBaseURL();
        }
    }
    
    private void storeBaseURL() {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences("baseURLPreferences", 0).edit();
        edit.putString("ServerBaseUrl", this._serverBaseUrl);
        edit.putLong("LastBaseUrlLookupTime", this._lastCheckAPIRequest.getTime());
        edit.commit();
    }
    
    @Override
    public void checkServerBaseUrl(final LoadCommandCallback<ServerAPIStatus> loadCommandCallback) {
        if (this._lastCheckAPIRequest != null && this._serverBaseUrl != null && new Date().getTime() - this._lastCheckAPIRequest.getTime() < 86400000L) {
            loadCommandCallback.loadingSucceded(new ServerApiCheckStatusResult(true, false, null, this._serverBaseUrl));
            return;
        }
        final String serverBaseURL = this.getServerBaseURL();
        serverBaseURL.endsWith("/");
        final StringBuilder sb = new StringBuilder();
        sb.append(serverBaseURL);
        sb.append("/");
        final String string = sb.toString();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append("api/mobile/");
        final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader(sb2.toString());
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        try {
            treeMap.put("applicationVersion", this._context.getPackageManager().getPackageInfo(this._context.getPackageName(), 0).versionName);
        }
        catch (PackageManager.NameNotFoundException ex) {
            Logger.getInstance().error(ConfigServiceImpl.class.getName(), PackageManager.NameNotFoundException.class.getName(), (Throwable)ex);
        }
        treeMap.put("platform", "Android");
        generalJSONAPICommandLoader.loadApiCommandGet((LoadCommandCallback)new LoadCommandCallbackWithTimeout<ServerApiCheckStatusResult>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                if (apiStatusCode == APIStatusCode.API_ERROR_API_NOT_VALID_PLEASE_UPDATE) {
                    loadCommandCallback.loadingSucceded(new ServerApiCheckStatusResult(false, false, null, null));
                    return;
                }
                loadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
            }
            
            @Override
            protected void succeded(final ServerApiCheckStatusResult serverApiCheckStatusResult) {
                ConfigServiceImpl.this.setServerBaseAPIUrl(serverApiCheckStatusResult.getServerBaseURL());
                loadCommandCallback.loadingSucceded(serverApiCheckStatusResult);
            }
        }, "statusAPI/check", treeMap, (JSONAPIResultParser)new JSONServerAPIStatusParser(), false);
    }
    
    @Override
    public String getAPIVersion() {
        return "1";
    }
    
    @Override
    public String getBasicAuthPasswort() {
        return "FqtMEKHMm2H?c;VvB37f";
    }
    
    @Override
    public String getBasicAuthUsername() {
        return "mobilebeta";
    }
    
    @Override
    public void getMobileAPIURL(final LoadCommandCallback<String> loadCommandCallback) {
        if (this.hasServerBaseUrl()) {
            loadCommandCallback.loadingSucceded(this._serverBaseUrl);
            return;
        }
        final String serverBaseURL = this.getServerBaseURL();
        serverBaseURL.endsWith("/");
        final StringBuilder sb = new StringBuilder();
        sb.append(serverBaseURL);
        sb.append("/");
        final String string = sb.toString();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append("api/mobile/");
        new GeneralJSONAPICommandLoader(sb2.toString()).loadApiCommandGet((LoadCommandCallback)new LoadCommandCallbackWithTimeout<ServerApiCheckStatusResult>() {
            private void handleResult(final LoadCommandCallback<String> loadCommandCallback, final ServerApiCheckStatusResult serverApiCheckStatusResult) {
                if (serverApiCheckStatusResult != null && serverApiCheckStatusResult.isValid()) {
                    ConfigServiceImpl.this.setServerBaseAPIUrl(serverApiCheckStatusResult.getServerBaseURL());
                    loadCommandCallback.loadingSucceded(ConfigServiceImpl.this._serverBaseUrl);
                    return;
                }
                loadCommandCallback.loadingFailed(APIStatusCode.API_ERROR_API_NOT_VALID_PLEASE_UPDATE, "API not valid anymore", null, null);
            }
            
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                Label_0067: {
                    if (apiStatusCode != APIStatusCode.API_ERROR_SERVICE_UNAVAILABLE) {
                        break Label_0067;
                    }
                    Label_0052: {
                        if (jsonObject == null) {
                            break Label_0052;
                        }
                        while (true) {
                            while (true) {
                                try {
                                    this.handleResult(loadCommandCallback, new JSONServerAPIStatusParser().parseResult(jsonObject));
                                    return;
                                    loadCommandCallback.loadingFailed(apiStatusCode, s, list, jsonObject);
                                    return;
                                    loadCommandCallback.loadingFailed(apiStatusCode, s, list, jsonObject);
                                    return;
                                    loadCommandCallback.loadingFailed(apiStatusCode, s, list, jsonObject);
                                    return;
                                }
                                catch (InvalidJsonForObjectException ex) {}
                                continue;
                            }
                        }
                    }
                }
            }
            
            @Override
            protected void succeded(final ServerApiCheckStatusResult serverApiCheckStatusResult) {
                this.handleResult(loadCommandCallback, serverApiCheckStatusResult);
            }
        }, "statusAPI/check", null, (JSONAPIResultParser)new JSONServerAPIStatusParser(), false);
    }
    
    @Override
    public void getPairingServerURL(final LoadCommandCallback<NodeServerAddress> loadCommandCallback) {
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
