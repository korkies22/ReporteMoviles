/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.le.BluetoothLeScanner
 *  android.bluetooth.le.ScanCallback
 *  android.bluetooth.le.ScanRecord
 *  android.bluetooth.le.ScanResult
 *  android.bluetooth.le.ScanSettings
 *  android.bluetooth.le.ScanSettings$Builder
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package com.facebook.places.internal;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.internal.Validate;
import com.facebook.places.internal.BleScanner;
import com.facebook.places.internal.BluetoothScanResult;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.ScannerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@TargetApi(value=21)
public class BleScannerImpl
implements BleScanner {
    private static final byte[] EDDYSTONE_PREFIX;
    private static final byte[] GRAVITY_PREFIX;
    private static final byte[] IBEACON_PREFIX;
    private static final String TAG = "BleScannerImpl";
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private Context context;
    private int errorCode;
    private boolean isScanInProgress;
    private LocationPackageRequestParams params;
    private ScanCallBackImpl scanCallBack;
    private final List<BluetoothScanResult> scanResults = new ArrayList<BluetoothScanResult>();

    static {
        IBEACON_PREFIX = BleScannerImpl.fromHexString("ff4c000215");
        EDDYSTONE_PREFIX = BleScannerImpl.fromHexString("16aafe");
        GRAVITY_PREFIX = BleScannerImpl.fromHexString("17ffab01");
    }

    BleScannerImpl(Context context, LocationPackageRequestParams locationPackageRequestParams) {
        this.context = context;
        this.params = locationPackageRequestParams;
    }

    private static String formatPayload(byte[] arrby) {
        if (arrby != null && arrby.length != 0) {
            return BleScannerImpl.toHexString(arrby, BleScannerImpl.getPayloadLength(arrby));
        }
        return null;
    }

    private static byte[] fromHexString(String string) {
        int n = string.length();
        byte[] arrby = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            arrby[i / 2] = (byte)((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i + 1), 16));
        }
        return arrby;
    }

    private static int getPayloadLength(byte[] arrby) {
        byte by;
        for (int i = 0; i < arrby.length; i += 1 + by) {
            by = arrby[i];
            if (by == 0) {
                return i;
            }
            if (by >= 0) continue;
            return arrby.length;
        }
        return arrby.length;
    }

    private static boolean isAdvPacketBeacon(byte[] arrby, int n) {
        int n2 = n + 1;
        if (BleScannerImpl.isArrayContained(arrby, n2, IBEACON_PREFIX)) {
            return true;
        }
        if (BleScannerImpl.isArrayContained(arrby, n2, EDDYSTONE_PREFIX)) {
            return true;
        }
        if (BleScannerImpl.isArrayContained(arrby, n, GRAVITY_PREFIX)) {
            return true;
        }
        if (BleScannerImpl.isAltBeacon(arrby, n)) {
            return true;
        }
        return false;
    }

    private static boolean isAltBeacon(byte[] arrby, int n) {
        int n2 = n + 5;
        boolean bl = false;
        if (n2 < arrby.length) {
            byte by = arrby[n];
            byte by2 = arrby[n + 1];
            n = arrby[n + 4];
            n2 = arrby[n2];
            boolean bl2 = bl;
            if (by == 27) {
                bl2 = bl;
                if (by2 == -1) {
                    bl2 = bl;
                    if (n == -66) {
                        bl2 = bl;
                        if (n2 == -84) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    private static boolean isArrayContained(byte[] arrby, int n, byte[] arrby2) {
        int n2 = arrby2.length;
        if (n + n2 > arrby.length) {
            return false;
        }
        for (int i = 0; i < n2; ++i) {
            if (arrby[n + i] == arrby2[i]) continue;
            return false;
        }
        return true;
    }

    private static boolean isBeacon(byte[] arrby) {
        if (arrby == null) {
            return false;
        }
        int n = arrby.length;
        int n2 = 0;
        while (n2 < n) {
            int n3 = arrby[n2];
            if (n3 <= 0) {
                return false;
            }
            if ((n3 = n3 + 1 + n2) > n) {
                return false;
            }
            if (BleScannerImpl.isAdvPacketBeacon(arrby, n2)) {
                return true;
            }
            n2 = n3;
        }
        return false;
    }

    private static void logException(String string, Exception exception) {
        if (FacebookSdk.isDebugEnabled()) {
            Log.e((String)TAG, (String)string, (Throwable)exception);
        }
    }

    private static BluetoothScanResult newBluetoothScanResult(ScanResult scanResult) {
        ScanRecord scanRecord = scanResult.getScanRecord();
        if (BleScannerImpl.isBeacon(scanRecord.getBytes())) {
            return new BluetoothScanResult(BleScannerImpl.formatPayload(scanRecord.getBytes()), scanResult.getRssi());
        }
        return null;
    }

    private static String toHexString(byte[] arrby, int n) {
        int n2;
        StringBuffer stringBuffer;
        block4 : {
            block3 : {
                stringBuffer = new StringBuffer();
                if (n < 0) break block3;
                n2 = n;
                if (n <= arrby.length) break block4;
            }
            n2 = arrby.length;
        }
        for (n = 0; n < n2; ++n) {
            stringBuffer.append(String.format("%02x", arrby[n]));
        }
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void waitForMainLooper(long l) {
        Object object;
        try {
            object = new Object();
            // MONITORENTER : object
        }
        catch (Exception exception) {
            BleScannerImpl.logException("Exception waiting for main looper", exception);
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                try {
                    Object object2 = object;
                    synchronized (object2) {
                        object.notify();
                    }
                }
                catch (Exception exception) {
                    BleScannerImpl.logException("Exception waiting for main looper", exception);
                    return;
                }
            }
        });
        object.wait(l);
        // MONITOREXIT : object
    }

    @Override
    public int getErrorCode() {
        synchronized (this) {
            int n = this.errorCode;
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<BluetoothScanResult> getScanResults() {
        synchronized (this) {
            List<BluetoothScanResult> list = this.scanResults;
            synchronized (list) {
                ArrayList<BluetoothScanResult> arrayList;
                int n = this.params.getBluetoothMaxScanResults();
                if (this.scanResults.size() > n) {
                    arrayList = new ArrayList<BluetoothScanResult>(n);
                    Comparator<BluetoothScanResult> comparator = new Comparator<BluetoothScanResult>(){

                        @Override
                        public int compare(BluetoothScanResult bluetoothScanResult, BluetoothScanResult bluetoothScanResult2) {
                            return bluetoothScanResult2.rssi - bluetoothScanResult.rssi;
                        }
                    };
                    Collections.sort(this.scanResults, comparator);
                    arrayList.addAll(this.scanResults.subList(0, n));
                } else {
                    arrayList = new ArrayList(this.scanResults.size());
                    arrayList.addAll(this.scanResults);
                }
                return arrayList;
            }
        }
    }

    @Override
    public void initAndCheckEligibility() throws ScannerException {
        synchronized (this) {
            if (Build.VERSION.SDK_INT < 21) {
                throw new ScannerException(ScannerException.Type.NOT_SUPPORTED);
            }
            if (!Validate.hasBluetoothPermission(this.context)) {
                throw new ScannerException(ScannerException.Type.PERMISSION_DENIED);
            }
            if (!Validate.hasLocationPermission(this.context)) {
                throw new ScannerException(ScannerException.Type.PERMISSION_DENIED);
            }
            this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (this.bluetoothAdapter != null && this.bluetoothAdapter.isEnabled()) {
                this.bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
                if (this.bluetoothLeScanner == null) {
                    throw new ScannerException(ScannerException.Type.UNKNOWN_ERROR);
                }
                return;
            }
            throw new ScannerException(ScannerException.Type.DISABLED);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void startScanning() throws ScannerException {
        // MONITORENTER : this
        if (this.isScanInProgress) {
            throw new ScannerException(ScannerException.Type.SCAN_ALREADY_IN_PROGRESS);
        }
        this.scanCallBack = new ScanCallBackImpl();
        this.isScanInProgress = true;
        this.errorCode = 0;
        ScanSettings.Builder builder = this.scanResults;
        // MONITORENTER : builder
        this.scanResults.clear();
        // MONITOREXIT : builder
        if (this.bluetoothLeScanner == null) {
            throw new ScannerException(ScannerException.Type.UNKNOWN_ERROR);
        }
        try {
            builder = new ScanSettings.Builder();
            builder.setScanMode(2);
            builder.setReportDelay(0L);
            this.bluetoothLeScanner.startScan(null, builder.build(), (ScanCallback)this.scanCallBack);
            this.isScanInProgress = true;
            // MONITOREXIT : this
            return;
        }
        catch (Exception exception) {
            throw new ScannerException(ScannerException.Type.UNKNOWN_ERROR);
        }
    }

    @Override
    public void stopScanning() {
        synchronized (this) {
            this.bluetoothLeScanner.flushPendingScanResults((ScanCallback)this.scanCallBack);
            this.bluetoothLeScanner.stopScan((ScanCallback)this.scanCallBack);
            this.waitForMainLooper(this.params.getBluetoothFlushResultsTimeoutMs());
            this.isScanInProgress = false;
            return;
        }
    }

    private class ScanCallBackImpl
    extends ScanCallback {
        private ScanCallBackImpl() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void onBatchScanResults(List<ScanResult> iterator) {
            super.onBatchScanResults(iterator);
            try {
                List list = BleScannerImpl.this.scanResults;
                // MONITORENTER : list
                iterator = iterator.iterator();
            }
            catch (Exception exception) {
                BleScannerImpl.logException("Exception in ble scan callback", exception);
                return;
            }
            do {
                if (!iterator.hasNext()) {
                    // MONITOREXIT : list
                    return;
                }
                BluetoothScanResult bluetoothScanResult = BleScannerImpl.newBluetoothScanResult((ScanResult)iterator.next());
                if (bluetoothScanResult == null) continue;
                BleScannerImpl.this.scanResults.add(bluetoothScanResult);
            } while (true);
        }

        public void onScanFailed(int n) {
            super.onScanFailed(n);
            BleScannerImpl.this.errorCode = n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void onScanResult(int n, ScanResult object) {
            super.onScanResult(n, object);
            try {
                List list = BleScannerImpl.this.scanResults;
                // MONITORENTER : list
            }
            catch (Exception exception) {
                BleScannerImpl.logException("Exception in ble scan callback", exception);
                return;
            }
            object = BleScannerImpl.newBluetoothScanResult(object);
            if (object != null) {
                BleScannerImpl.this.scanResults.add(object);
            }
            // MONITOREXIT : list
        }
    }

}
