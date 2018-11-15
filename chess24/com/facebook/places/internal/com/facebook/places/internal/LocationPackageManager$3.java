/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.facebook.places.internal;

import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.places.internal.BleScanner;
import com.facebook.places.internal.BluetoothScanResult;
import com.facebook.places.internal.LocationPackage;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.ScannerFactory;
import java.util.List;
import java.util.concurrent.Callable;

static final class LocationPackageManager
implements Callable<LocationPackage> {
    final /* synthetic */ LocationPackageRequestParams val$requestParams;

    LocationPackageManager(LocationPackageRequestParams locationPackageRequestParams) {
        this.val$requestParams = locationPackageRequestParams;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public LocationPackage call() throws Exception {
        var2_1 = new LocationPackage();
        try {
            var3_2 = ScannerFactory.newBleScanner(FacebookSdk.getApplicationContext(), this.val$requestParams);
            var3_2.initAndCheckEligibility();
        }
lbl5: // 2 sources:
        catch (Exception var3_3) {
            com.facebook.places.internal.LocationPackageManager.access$300("Exception scanning for bluetooth beacons", var3_3);
            var2_1.isBluetoothScanningEnabled = false;
            return var2_1;
        }
        var3_2.startScanning();
        Thread.sleep(this.val$requestParams.getBluetoothScanDurationMs());
        ** GOTO lbl18
        {
            catch (Throwable var4_5) {
                var3_2.stopScanning();
                throw var4_5;
            }
            catch (Exception var4_6) {}
lbl18: // 2 sources:
            ** try [egrp 3[TRYBLOCK] [4 : 41->121)] { 
lbl19: // 1 sources:
            var3_2.stopScanning();
            var1_4 = var3_2.getErrorCode();
            if (var1_4 == 0) {
                var2_1.ambientBluetoothLe = var3_2.getScanResults();
                var2_1.isBluetoothScanningEnabled = true;
                return var2_1;
            }
            if (FacebookSdk.isDebugEnabled()) {
                Log.d((String)"LocationPackageManager", (String)String.format("Bluetooth LE scan failed with error: %d", new Object[]{var1_4}));
            }
            var2_1.isBluetoothScanningEnabled = false;
            return var2_1;
        }
    }
}
