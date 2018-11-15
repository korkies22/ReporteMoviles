/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.PackageManager
 *  android.net.wifi.ScanResult
 *  android.net.wifi.SupplicantState
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.SystemClock
 *  android.text.TextUtils
 */
package com.facebook.places.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.facebook.internal.Validate;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.ScannerException;
import com.facebook.places.internal.WifiScanResult;
import com.facebook.places.internal.WifiScanner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class WifiScannerImpl
implements WifiScanner {
    private static final String SSID_NOMAP = "_nomap";
    private static final String SSID_OPTOUT = "_optout";
    private ScanResultBroadcastReceiver broadcastReceiver;
    private Context context;
    private final LocationPackageRequestParams params;
    private final Object scanLock = new Object();
    private WifiManager wifiManager;

    WifiScannerImpl(Context context, LocationPackageRequestParams locationPackageRequestParams) {
        this.context = context;
        this.params = locationPackageRequestParams;
    }

    private static void filterResults(List<ScanResult> list, int n) {
        if (list.size() > n) {
            Collections.sort(list, new Comparator<ScanResult>(){

                @Override
                public int compare(ScanResult scanResult, ScanResult scanResult2) {
                    return scanResult2.level - scanResult.level;
                }
            });
            list.subList(n, list.size()).clear();
        }
    }

    private static List<ScanResult> filterWifiScanResultsByMaxAge(List<ScanResult> object, long l) {
        ArrayList<ScanResult> arrayList = new ArrayList<ScanResult>();
        if (object != null) {
            if (Build.VERSION.SDK_INT < 17) {
                arrayList.addAll((Collection<ScanResult>)object);
                return arrayList;
            }
            long l2 = SystemClock.elapsedRealtime();
            object = object.iterator();
            while (object.hasNext()) {
                long l3;
                ScanResult scanResult = (ScanResult)object.next();
                long l4 = l3 = l2 - scanResult.timestamp / 1000L;
                if (l3 < 0L) {
                    l4 = System.currentTimeMillis() - scanResult.timestamp;
                }
                if (l4 >= l) continue;
                arrayList.add(scanResult);
            }
        }
        return arrayList;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private List<WifiScanResult> getActiveScanResults() throws ScannerException {
        List<WifiScanResult> list;
        block9 : {
            block10 : {
                List<WifiScanResult> list2;
                list = list2 = null;
                try {
                    if (!Validate.hasChangeWifiStatePermission(this.context)) break block9;
                    this.registerBroadcastReceiver();
                    boolean bl = this.wifiManager.startScan();
                    list = list2;
                    if (!bl) break block9;
                    list = this.scanLock;
                    // MONITORENTER : list
                }
                catch (Throwable throwable) {
                    this.unregisterBroadcastReceiver();
                    throw throwable;
                }
                catch (Exception exception) {
                    list = list2;
                    break block9;
                }
                this.scanLock.wait(this.params.getWifiScanTimeoutMs());
                // MONITOREXIT : list
                break block10;
                catch (InterruptedException interruptedException) {}
            }
            list = this.getCachedScanResults();
        }
        this.unregisterBroadcastReceiver();
        return list;
    }

    private List<WifiScanResult> getCachedScanResults() throws ScannerException {
        try {
            Object object = WifiScannerImpl.filterWifiScanResultsByMaxAge(this.wifiManager.getScanResults(), this.params.getWifiScanMaxAgeMs());
            WifiScannerImpl.filterResults(object, this.params.getWifiMaxScanResults());
            ArrayList<WifiScanResult> arrayList = new ArrayList<WifiScanResult>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                ScanResult scanResult = (ScanResult)object.next();
                if (WifiScannerImpl.isWifiSsidBlacklisted(scanResult.SSID)) continue;
                WifiScanResult wifiScanResult = new WifiScanResult();
                wifiScanResult.bssid = scanResult.BSSID;
                wifiScanResult.ssid = scanResult.SSID;
                wifiScanResult.rssi = scanResult.level;
                wifiScanResult.frequency = scanResult.frequency;
                arrayList.add(wifiScanResult);
            }
            return arrayList;
        }
        catch (Exception exception) {
            throw new ScannerException(ScannerException.Type.UNKNOWN_ERROR, exception);
        }
    }

    private boolean isWifiScanningAlwaysOn() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.wifiManager.isScanAlwaysAvailable();
        }
        return false;
    }

    private static boolean isWifiSsidBlacklisted(String string) {
        if (string != null && (string.endsWith(SSID_NOMAP) || string.contains(SSID_OPTOUT))) {
            return true;
        }
        return false;
    }

    private void registerBroadcastReceiver() {
        if (this.broadcastReceiver != null) {
            this.unregisterBroadcastReceiver();
        }
        this.broadcastReceiver = new ScanResultBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        this.context.registerReceiver((BroadcastReceiver)this.broadcastReceiver, intentFilter);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void unregisterBroadcastReceiver() {
        if (this.broadcastReceiver != null) {
            try {
                this.context.unregisterReceiver((BroadcastReceiver)this.broadcastReceiver);
            }
            catch (Exception exception) {}
            this.broadcastReceiver = null;
        }
    }

    @Override
    public WifiScanResult getConnectedWifi() throws ScannerException {
        block4 : {
            WifiInfo wifiInfo;
            try {
                wifiInfo = this.wifiManager.getConnectionInfo();
                if (wifiInfo == null) break block4;
            }
            catch (Exception exception) {
                throw new ScannerException(ScannerException.Type.UNKNOWN_ERROR, exception);
            }
            if (TextUtils.isEmpty((CharSequence)wifiInfo.getBSSID()) || wifiInfo.getSupplicantState() != SupplicantState.COMPLETED || WifiScannerImpl.isWifiSsidBlacklisted(wifiInfo.getSSID())) break block4;
            WifiScanResult wifiScanResult = new WifiScanResult();
            wifiScanResult.bssid = wifiInfo.getBSSID();
            wifiScanResult.ssid = wifiInfo.getSSID();
            wifiScanResult.rssi = wifiInfo.getRssi();
            if (Build.VERSION.SDK_INT >= 21) {
                wifiScanResult.frequency = wifiInfo.getFrequency();
            }
            return wifiScanResult;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<WifiScanResult> getWifiScans() throws ScannerException {
        synchronized (this) {
            List<WifiScanResult> list = null;
            if (!this.params.isWifiActiveScanForced()) {
                list = this.getCachedScanResults();
            }
            boolean bl = list == null || list.isEmpty();
            if (this.params.isWifiActiveScanForced()) return this.getActiveScanResults();
            List<WifiScanResult> list2 = list;
            if (!this.params.isWifiActiveScanAllowed()) return list2;
            list2 = list;
            if (!bl) return list2;
            return this.getActiveScanResults();
        }
    }

    @Override
    public void initAndCheckEligibility() throws ScannerException {
        if (!this.context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
            throw new ScannerException(ScannerException.Type.NOT_SUPPORTED);
        }
        if (!Validate.hasWiFiPermission(this.context)) {
            throw new ScannerException(ScannerException.Type.PERMISSION_DENIED);
        }
        if (this.wifiManager == null) {
            this.wifiManager = (WifiManager)this.context.getSystemService("wifi");
        }
        if (!this.isWifiScanningAlwaysOn() && !this.wifiManager.isWifiEnabled()) {
            throw new ScannerException(ScannerException.Type.DISABLED);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isWifiScanningEnabled() {
        block3 : {
            try {
                this.initAndCheckEligibility();
                boolean bl = Validate.hasLocationPermission(this.context);
                if (!bl) break block3;
                return true;
            }
            catch (ScannerException scannerException) {
                return false;
            }
        }
        do {
            return false;
            break;
        } while (true);
    }

    private class ScanResultBroadcastReceiver
    extends BroadcastReceiver {
        private ScanResultBroadcastReceiver() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onReceive(Context object, Intent intent) {
            if (intent != null && "android.net.wifi.SCAN_RESULTS".equals(intent.getAction())) {
                object = WifiScannerImpl.this.scanLock;
                synchronized (object) {
                    WifiScannerImpl.this.scanLock.notify();
                }
                WifiScannerImpl.this.unregisterBroadcastReceiver();
                return;
            }
        }
    }

}
