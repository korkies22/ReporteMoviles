// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import android.os.Handler;
import android.os.Looper;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Map;
import android.os.Build;
import java.util.TreeMap;
import de.cisha.android.board.service.jsonparser.JSONDeviceUUIDParser;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import android.util.Log;
import java.util.UUID;
import de.cisha.chess.model.CishaUUID;
import android.content.Context;

public class DUUIDService implements IDUUIDService
{
    private static final String PREF_DUUID_STRING = "uui_string";
    private static final String PREF_DUUID_VERIFIED = "uui_verified";
    private static final String PREF_NAME = "uuid";
    private static IDUUIDService _instance;
    private Context _context;
    private CishaUUID _duuid;
    private boolean _initialized;
    private boolean _isInitializing;
    
    private DUUIDService(final Context context) {
        this._initialized = false;
        this._isInitializing = true;
        this._context = context;
        this.initUuidAsync();
    }
    
    private String generateTmpDuuid() {
        return UUID.randomUUID().toString();
    }
    
    public static IDUUIDService getInstance(final Context context) {
        if (DUUIDService._instance == null) {
            DUUIDService._instance = new DUUIDService(context);
        }
        return DUUIDService._instance;
    }
    
    private void initUuidAsync() {
        this.loadUuid();
        this.storeUuid();
        final StringBuilder sb = new StringBuilder();
        sb.append("verified uuid exists on device : ");
        sb.append(this._duuid.isVerified());
        Log.d("uuid", sb.toString());
        if (!this._duuid.isVerified()) {
            this.loadUUIDFromServerAsyn();
            return;
        }
        this._initialized = true;
    }
    
    private void loadUUIDFromServerAsyn() {
        this._isInitializing = true;
        this.loadUUIDFromServerAsyn(new LoadCommandCallbackWithTimeout<CishaUUID>() {
            public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                DUUIDService.this._isInitializing = false;
                final Logger instance = Logger.getInstance();
                final StringBuilder sb = new StringBuilder();
                sb.append("Error getting Device UUID: ");
                sb.append(s);
                instance.debug(sb.toString());
            }
            
            public void succeded(final CishaUUID cishaUUID) {
                DUUIDService.this._duuid = cishaUUID;
                DUUIDService.this._initialized = true;
                Logger.getInstance().debug("Loading duuid succeded");
                DUUIDService.this.storeUuid();
                DUUIDService.this._isInitializing = false;
            }
        });
    }
    
    private void loadUUIDFromServerAsyn(final LoadCommandCallback<CishaUUID> loadCommandCallback) {
        final GeneralJSONAPICommandLoader<CishaUUID> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<CishaUUID>();
        final JSONDeviceUUIDParser jsonDeviceUUIDParser = new JSONDeviceUUIDParser();
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        final StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append(" ");
        sb.append(Build.MODEL);
        treeMap.put("name", sb.toString());
        generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallback, "mobileAPI/RegisterApp", treeMap, jsonDeviceUUIDParser, false);
    }
    
    private void loadUuid() {
        final SharedPreferences sharedPreferences = this._context.getSharedPreferences("uuid", 0);
        this._duuid = new CishaUUID(sharedPreferences.getString("uui_string", this.generateTmpDuuid()), sharedPreferences.getBoolean("uui_verified", false));
        final StringBuilder sb = new StringBuilder();
        sb.append("uuid loaded from pref: ");
        sb.append(this._duuid);
        Log.d("uuid", sb.toString());
    }
    
    private void storeUuid() {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences("uuid", 0).edit();
        edit.putString("uui_string", this._duuid.getUuid());
        edit.putBoolean("uui_verified", this._duuid.isVerified());
        edit.commit();
        final StringBuilder sb = new StringBuilder();
        sb.append("uuid stored: ");
        sb.append(this._duuid);
        Log.d("uuid", sb.toString());
    }
    
    @Override
    public void getDuuid(final LoadCommandCallback<CishaUUID> loadCommandCallback) {
        if (this._initialized && this._duuid != null) {
            loadCommandCallback.loadingSucceded(this._duuid);
            return;
        }
        if (!this._isInitializing) {
            this.loadUUIDFromServerAsyn();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        do {
                            Thread.sleep(50L);
                        } while (DUUIDService.this._isInitializing);
                        final Handler handler = new Handler(Looper.getMainLooper());
                        if (DUUIDService.this._initialized && DUUIDService.this._duuid != null) {
                            handler.post((Runnable)new Runnable() {
                                @Override
                                public void run() {
                                    loadCommandCallback.loadingSucceded(DUUIDService.this._duuid);
                                }
                            });
                            return;
                        }
                        handler.post((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "Not possible to get DUUID", null, null);
                            }
                        });
                    }
                    catch (InterruptedException ex) {
                        continue;
                    }
                    break;
                }
            }
        }).start();
    }
    
    @Override
    public void renewDuuid() {
        this.loadUUIDFromServerAsyn();
    }
}
