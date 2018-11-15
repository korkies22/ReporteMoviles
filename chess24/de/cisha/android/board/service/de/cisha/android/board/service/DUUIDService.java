/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Build
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import de.cisha.android.board.service.IDUUIDService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONDeviceUUIDParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.json.JSONObject;

public class DUUIDService
implements IDUUIDService {
    private static final String PREF_DUUID_STRING = "uui_string";
    private static final String PREF_DUUID_VERIFIED = "uui_verified";
    private static final String PREF_NAME = "uuid";
    private static IDUUIDService _instance;
    private Context _context;
    private CishaUUID _duuid;
    private boolean _initialized = false;
    private boolean _isInitializing = true;

    private DUUIDService(Context context) {
        this._context = context;
        this.initUuidAsync();
    }

    private String generateTmpDuuid() {
        return UUID.randomUUID().toString();
    }

    public static IDUUIDService getInstance(Context context) {
        if (_instance == null) {
            _instance = new DUUIDService(context);
        }
        return _instance;
    }

    private void initUuidAsync() {
        this.loadUuid();
        this.storeUuid();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("verified uuid exists on device : ");
        stringBuilder.append(this._duuid.isVerified());
        Log.d((String)PREF_NAME, (String)stringBuilder.toString());
        if (!this._duuid.isVerified()) {
            this.loadUUIDFromServerAsyn();
            return;
        }
        this._initialized = true;
    }

    private void loadUUIDFromServerAsyn() {
        this._isInitializing = true;
        this.loadUUIDFromServerAsyn((LoadCommandCallback<CishaUUID>)new LoadCommandCallbackWithTimeout<CishaUUID>(){

            @Override
            public void failed(APIStatusCode object, String string, List<LoadFieldError> object2, JSONObject jSONObject) {
                DUUIDService.this._isInitializing = false;
                object = Logger.getInstance();
                object2 = new StringBuilder();
                object2.append("Error getting Device UUID: ");
                object2.append(string);
                object.debug(object2.toString());
            }

            @Override
            public void succeded(CishaUUID cishaUUID) {
                DUUIDService.this._duuid = cishaUUID;
                DUUIDService.this._initialized = true;
                Logger.getInstance().debug("Loading duuid succeded");
                DUUIDService.this.storeUuid();
                DUUIDService.this._isInitializing = false;
            }
        });
    }

    private void loadUUIDFromServerAsyn(LoadCommandCallback<CishaUUID> loadCommandCallback) {
        GeneralJSONAPICommandLoader<CishaUUID> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<CishaUUID>();
        JSONDeviceUUIDParser jSONDeviceUUIDParser = new JSONDeviceUUIDParser();
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Build.MANUFACTURER);
        stringBuilder.append(" ");
        stringBuilder.append(Build.MODEL);
        treeMap.put("name", stringBuilder.toString());
        generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallback, "mobileAPI/RegisterApp", treeMap, jSONDeviceUUIDParser, false);
    }

    private void loadUuid() {
        Object object = this._context.getSharedPreferences(PREF_NAME, 0);
        this._duuid = new CishaUUID(object.getString(PREF_DUUID_STRING, this.generateTmpDuuid()), object.getBoolean(PREF_DUUID_VERIFIED, false));
        object = new StringBuilder();
        object.append("uuid loaded from pref: ");
        object.append(this._duuid);
        Log.d((String)PREF_NAME, (String)object.toString());
    }

    private void storeUuid() {
        Object object = this._context.getSharedPreferences(PREF_NAME, 0).edit();
        object.putString(PREF_DUUID_STRING, this._duuid.getUuid());
        object.putBoolean(PREF_DUUID_VERIFIED, this._duuid.isVerified());
        object.commit();
        object = new StringBuilder();
        object.append("uuid stored: ");
        object.append(this._duuid);
        Log.d((String)PREF_NAME, (String)object.toString());
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
        new Thread(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(50L);
                    }
                    catch (InterruptedException interruptedException) {}
                } while (DUUIDService.this._isInitializing);
                Handler handler = new Handler(Looper.getMainLooper());
                if (DUUIDService.this._initialized && DUUIDService.this._duuid != null) {
                    handler.post(new Runnable(){

                        @Override
                        public void run() {
                            loadCommandCallback.loadingSucceded(DUUIDService.this._duuid);
                        }
                    });
                    return;
                }
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "Not possible to get DUUID", null, null);
                    }
                });
            }

        }).start();
    }

    @Override
    public void renewDuuid() {
        this.loadUUIDFromServerAsyn();
    }

}
