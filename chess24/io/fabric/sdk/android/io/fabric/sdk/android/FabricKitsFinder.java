/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.text.TextUtils
 */
package io.fabric.sdk.android;

import android.os.SystemClock;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class FabricKitsFinder
implements Callable<Map<String, KitInfo>> {
    private static final String FABRIC_BUILD_TYPE_KEY = "fabric-build-type";
    static final String FABRIC_DIR = "fabric/";
    private static final String FABRIC_IDENTIFIER_KEY = "fabric-identifier";
    private static final String FABRIC_VERSION_KEY = "fabric-version";
    final String apkFileName;

    FabricKitsFinder(String string) {
        this.apkFileName = string;
    }

    private Map<String, KitInfo> findImplicitKits() {
        HashMap<String, KitInfo> hashMap = new HashMap<String, KitInfo>();
        try {
            Class.forName("com.google.android.gms.ads.AdView");
            KitInfo kitInfo = new KitInfo("com.google.firebase.firebase-ads", "0.0.0", "binary");
            hashMap.put(kitInfo.getIdentifier(), kitInfo);
            Fabric.getLogger().v("Fabric", "Found kit: com.google.firebase.firebase-ads");
            return hashMap;
        }
        catch (Exception exception) {
            return hashMap;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Map<String, KitInfo> findRegisteredKits() throws Exception {
        HashMap<String, KitInfo> hashMap = new HashMap<String, KitInfo>();
        ZipFile zipFile = this.loadApkFile();
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        while (enumeration.hasMoreElements()) {
            Object object = enumeration.nextElement();
            if (!object.getName().startsWith(FABRIC_DIR) || object.getName().length() <= FABRIC_DIR.length() || (object = this.loadKitInfo((ZipEntry)object, zipFile)) == null) continue;
            hashMap.put(object.getIdentifier(), (KitInfo)object);
            Fabric.getLogger().v("Fabric", String.format("Found kit:[%s] version:[%s]", object.getIdentifier(), object.getVersion()));
        }
        if (zipFile == null) return hashMap;
        try {
            zipFile.close();
        }
        catch (IOException iOException) {
            return hashMap;
        }
        return hashMap;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private KitInfo loadKitInfo(ZipEntry zipEntry, ZipFile closeable) {
        void var1_4;
        block9 : {
            Object object;
            InputStream inputStream;
            Object object2;
            void var4_11;
            block10 : {
                Object object3;
                block8 : {
                    inputStream = closeable.getInputStream(zipEntry);
                    closeable = inputStream;
                    object2 = new Properties();
                    closeable = inputStream;
                    object2.load(inputStream);
                    closeable = inputStream;
                    object3 = object2.getProperty(FABRIC_IDENTIFIER_KEY);
                    closeable = inputStream;
                    object = object2.getProperty(FABRIC_VERSION_KEY);
                    closeable = inputStream;
                    object2 = object2.getProperty(FABRIC_BUILD_TYPE_KEY);
                    closeable = inputStream;
                    if (TextUtils.isEmpty((CharSequence)object3)) break block8;
                    closeable = inputStream;
                    if (TextUtils.isEmpty((CharSequence)object)) break block8;
                    closeable = inputStream;
                    object3 = new KitInfo((String)object3, (String)object, (String)object2);
                    CommonUtils.closeQuietly(inputStream);
                    return object3;
                }
                closeable = inputStream;
                try {
                    object3 = new StringBuilder();
                    closeable = inputStream;
                    object3.append("Invalid format of fabric file,");
                    closeable = inputStream;
                    object3.append(zipEntry.getName());
                    closeable = inputStream;
                    throw new IllegalStateException(object3.toString());
                }
                catch (Throwable throwable) {
                    break block9;
                }
                catch (IOException iOException) {
                    break block10;
                }
                catch (Throwable throwable) {
                    closeable = null;
                    break block9;
                }
                catch (IOException iOException) {
                    inputStream = null;
                }
            }
            closeable = inputStream;
            object = Fabric.getLogger();
            closeable = inputStream;
            object2 = new StringBuilder();
            closeable = inputStream;
            object2.append("Error when parsing fabric properties ");
            closeable = inputStream;
            object2.append(zipEntry.getName());
            closeable = inputStream;
            object.e("Fabric", object2.toString(), (Throwable)var4_11);
            CommonUtils.closeQuietly(inputStream);
            return null;
        }
        CommonUtils.closeQuietly(closeable);
        throw var1_4;
    }

    @Override
    public Map<String, KitInfo> call() throws Exception {
        HashMap<String, KitInfo> hashMap = new HashMap<String, KitInfo>();
        long l = SystemClock.elapsedRealtime();
        hashMap.putAll(this.findImplicitKits());
        hashMap.putAll(this.findRegisteredKits());
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("finish scanning in ");
        stringBuilder.append(SystemClock.elapsedRealtime() - l);
        logger.v("Fabric", stringBuilder.toString());
        return hashMap;
    }

    protected ZipFile loadApkFile() throws IOException {
        return new ZipFile(this.apkFileName);
    }
}
