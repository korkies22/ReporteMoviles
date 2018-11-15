/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.util.Log
 */
package com.facebook.places.internal;

import android.location.Location;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.places.internal.BleScanner;
import com.facebook.places.internal.BluetoothScanResult;
import com.facebook.places.internal.LocationPackage;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.LocationScanner;
import com.facebook.places.internal.ScannerException;
import com.facebook.places.internal.ScannerFactory;
import com.facebook.places.internal.WifiScanResult;
import com.facebook.places.internal.WifiScanner;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class LocationPackageManager {
    private static final String TAG = "LocationPackageManager";

    static /* synthetic */ FutureTask access$000(LocationScanner locationScanner, LocationPackageRequestParams locationPackageRequestParams) {
        return LocationPackageManager.newLocationScanFuture(locationScanner, locationPackageRequestParams);
    }

    static /* synthetic */ FutureTask access$100(LocationPackageRequestParams locationPackageRequestParams) {
        return LocationPackageManager.newWifiScanFuture(locationPackageRequestParams);
    }

    static /* synthetic */ FutureTask access$200(LocationPackageRequestParams locationPackageRequestParams) {
        return LocationPackageManager.newBluetoothScanFuture(locationPackageRequestParams);
    }

    private static void logException(String string, Throwable throwable) {
        if (FacebookSdk.isDebugEnabled()) {
            Log.e((String)TAG, (String)string, (Throwable)throwable);
        }
    }

    private static FutureTask<LocationPackage> newBluetoothScanFuture(final LocationPackageRequestParams locationPackageRequestParams) {
        return new FutureTask<LocationPackage>(new Callable<LocationPackage>(){

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
                    var3_2 = ScannerFactory.newBleScanner(FacebookSdk.getApplicationContext(), locationPackageRequestParams);
                    var3_2.initAndCheckEligibility();
                }
lbl5: // 2 sources:
                catch (Exception var3_3) {
                    LocationPackageManager.access$300("Exception scanning for bluetooth beacons", var3_3);
                    var2_1.isBluetoothScanningEnabled = false;
                    return var2_1;
                }
                var3_2.startScanning();
                Thread.sleep(locationPackageRequestParams.getBluetoothScanDurationMs());
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
        });
    }

    private static FutureTask<LocationPackage> newLocationScanFuture(final LocationScanner locationScanner, LocationPackageRequestParams locationPackageRequestParams) {
        return new FutureTask<LocationPackage>(new Callable<LocationPackage>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public LocationPackage call() throws Exception {
                LocationPackage locationPackage = new LocationPackage();
                try {
                    locationPackage.location = locationScanner.getLocation();
                    return locationPackage;
                }
                catch (ScannerException scannerException) {
                    locationPackage.locationError = scannerException.type;
                    LocationPackageManager.logException("Exception while getting location", scannerException);
                    return locationPackage;
                }
                catch (Exception exception) {}
                locationPackage.locationError = ScannerException.Type.UNKNOWN_ERROR;
                return locationPackage;
            }
        });
    }

    private static FutureTask<LocationPackage> newWifiScanFuture(final LocationPackageRequestParams locationPackageRequestParams) {
        return new FutureTask<LocationPackage>(new Callable<LocationPackage>(){

            @Override
            public LocationPackage call() throws Exception {
                LocationPackage locationPackage = new LocationPackage();
                try {
                    WifiScanner wifiScanner = ScannerFactory.newWifiScanner(FacebookSdk.getApplicationContext(), locationPackageRequestParams);
                    wifiScanner.initAndCheckEligibility();
                    locationPackage.connectedWifi = wifiScanner.getConnectedWifi();
                    locationPackage.isWifiScanningEnabled = wifiScanner.isWifiScanningEnabled();
                    if (locationPackage.isWifiScanningEnabled) {
                        locationPackage.ambientWifi = wifiScanner.getWifiScans();
                        return locationPackage;
                    }
                }
                catch (Exception exception) {
                    LocationPackageManager.logException("Exception scanning for wifi access points", exception);
                    locationPackage.isWifiScanningEnabled = false;
                }
                return locationPackage;
            }
        });
    }

    public static void requestLocationPackage(final LocationPackageRequestParams locationPackageRequestParams, final Listener listener) {
        FacebookSdk.getExecutor().execute(new Runnable(){

            /*
             * Exception decompiling
             */
            @Override
            public void run() {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 1[TRYBLOCK]
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:767)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:864)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
                // org.benf.cfr.reader.Main.doClass(Main.java:54)
                // org.benf.cfr.reader.Main.main(Main.java:247)
                throw new IllegalStateException("Decompilation failed");
            }
        });
    }

    public static interface Listener {
        public void onLocationPackage(LocationPackage var1);
    }

}
