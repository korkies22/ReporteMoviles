/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package de.cisha.android.board.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import de.cisha.android.board.service.IInternetAvailabilityService;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class InternetAvailabiltyService
implements IInternetAvailabilityService {
    private static ConnectivityManager _connectivityManager;
    private static IInternetAvailabilityService _instance;
    private WeakHashMap<NetworkListener, NetworkListener> _listeners = new WeakHashMap();
    boolean _previousNetworkAvailability = true;

    private InternetAvailabiltyService(Context context) {
        _connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                InternetAvailabiltyService.this.networkChanged(context);
            }
        };
        this.networkChanged(context);
        context.registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static IInternetAvailabilityService getInstance(Context object) {
        synchronized (InternetAvailabiltyService.class) {
            void var0_2;
            if (_instance != null) return _instance;
            Context context = object.getApplicationContext();
            if (context != null) {
                Context context2 = context;
            }
            _instance = new InternetAvailabiltyService((Context)var0_2);
            return _instance;
        }
    }

    private void informListeners() {
        if (this._listeners != null) {
            boolean bl = this.isNetworkAvailable();
            Iterator<NetworkListener> iterator = this._listeners.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().networkStateChanged(bl);
            }
        }
    }

    private void networkChanged(Context context) {
        boolean bl = this.isNetworkAvailable();
        if (bl != this._previousNetworkAvailability) {
            this.informListeners();
            this._previousNetworkAvailability = bl;
        }
    }

    @Override
    public void addNetworkListener(NetworkListener networkListener) {
        this._listeners.put(networkListener, networkListener);
    }

    @Override
    public boolean isNetworkAvailable() {
        NetworkInfo networkInfo = _connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    @Override
    public void removeNetworkListener(NetworkListener networkListener) {
        this._listeners.remove(networkListener);
    }

    public static interface NetworkListener {
        public void networkStateChanged(boolean var1);
    }

}
