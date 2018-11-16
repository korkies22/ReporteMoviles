// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import android.net.NetworkInfo;
import java.util.Iterator;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;
import java.util.WeakHashMap;
import android.net.ConnectivityManager;

public class InternetAvailabiltyService implements IInternetAvailabilityService
{
    private static ConnectivityManager _connectivityManager;
    private static IInternetAvailabilityService _instance;
    private WeakHashMap<NetworkListener, NetworkListener> _listeners;
    boolean _previousNetworkAvailability;
    
    private InternetAvailabiltyService(final Context context) {
        this._previousNetworkAvailability = true;
        this._listeners = new WeakHashMap<NetworkListener, NetworkListener>();
        InternetAvailabiltyService._connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(final Context context, final Intent intent) {
                InternetAvailabiltyService.this.networkChanged(context);
            }
        };
        this.networkChanged(context);
        context.registerReceiver((BroadcastReceiver)broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
    
    public static IInternetAvailabilityService getInstance(Context context) {
        synchronized (InternetAvailabiltyService.class) {
            if (InternetAvailabiltyService._instance == null) {
                final Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    context = applicationContext;
                }
                InternetAvailabiltyService._instance = new InternetAvailabiltyService(context);
            }
            return InternetAvailabiltyService._instance;
        }
    }
    
    private void informListeners() {
        if (this._listeners != null) {
            final boolean networkAvailable = this.isNetworkAvailable();
            final Iterator<NetworkListener> iterator = this._listeners.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().networkStateChanged(networkAvailable);
            }
        }
    }
    
    private void networkChanged(final Context context) {
        final boolean networkAvailable = this.isNetworkAvailable();
        if (networkAvailable != this._previousNetworkAvailability) {
            this.informListeners();
            this._previousNetworkAvailability = networkAvailable;
        }
    }
    
    @Override
    public void addNetworkListener(final NetworkListener networkListener) {
        this._listeners.put(networkListener, networkListener);
    }
    
    @Override
    public boolean isNetworkAvailable() {
        final NetworkInfo activeNetworkInfo = InternetAvailabiltyService._connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }
    
    @Override
    public void removeNetworkListener(final NetworkListener networkListener) {
        this._listeners.remove(networkListener);
    }
    
    public interface NetworkListener
    {
        void networkStateChanged(final boolean p0);
    }
}
