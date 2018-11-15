/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.crashlytics.android.beta;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.ValueLoader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeviceTokenLoader
implements ValueLoader<String> {
    private static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
    private static final String DIRFACTOR_DEVICE_TOKEN_PREFIX = "assets/com.crashlytics.android.beta/dirfactor-device-token=";

    String determineDeviceToken(ZipInputStream object) throws IOException {
        if ((object = object.getNextEntry()) != null && (object = object.getName()).startsWith(DIRFACTOR_DEVICE_TOKEN_PREFIX)) {
            return object.substring(DIRFACTOR_DEVICE_TOKEN_PREFIX.length(), object.length() - 1);
        }
        return "";
    }

    ZipInputStream getZipInputStreamOfApkFrom(Context context, String string) throws PackageManager.NameNotFoundException, FileNotFoundException {
        return new ZipInputStream(new FileInputStream(context.getPackageManager().getApplicationInfo((String)string, (int)0).sourceDir));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public String load(Context var1_1) throws Exception {
        block25 : {
            block29 : {
                block26 : {
                    block28 : {
                        block27 : {
                            var4_6 = System.nanoTime();
                            var8_7 = "";
                            var9_8 = null;
                            var10_9 = null;
                            var7_10 = null;
                            var6_16 = null;
                            var1_1 = this.getZipInputStreamOfApkFrom((Context)var1_1, "io.crash.air");
                            try {
                                var6_16 = this.determineDeviceToken((ZipInputStream)var1_1);
                                ** if (var1_1 == null) goto lbl-1000
                            }
                            catch (Throwable var7_11) {
                                var6_16 = var1_1;
                                var1_1 = var7_11;
                                break block26;
                            }
                            catch (IOException var7_12) {
                                break block27;
                            }
                            catch (FileNotFoundException var7_13) {
                                break block28;
                            }
                            catch (PackageManager.NameNotFoundException var6_17) {
                                break block29;
                            }
lbl-1000: // 1 sources:
                            {
                                try {
                                    var1_1.close();
                                }
                                catch (IOException var1_2) {
                                    Fabric.getLogger().e("Beta", "Failed to close the APK file", var1_2);
                                }
                            }
lbl-1000: // 2 sources:
                            {
                                break block25;
                            }
                            catch (Throwable var1_3) {
                                break block26;
                            }
                            catch (IOException var7_14) {
                                var1_1 = var9_8;
                            }
                        }
                        var6_16 = var1_1;
                        {
                            Fabric.getLogger().e("Beta", "Failed to read the APK file", (Throwable)var7_10);
                            var6_16 = var8_7;
                            ** if (var1_1 == null) goto lbl-1000
                        }
lbl-1000: // 1 sources:
                        {
                            var1_1.close();
                            var6_16 = var8_7;
                        }
lbl-1000: // 2 sources:
                        {
                            break block25;
                        }
                        catch (FileNotFoundException var7_15) {
                            var1_1 = var10_9;
                        }
                    }
                    var6_16 = var1_1;
                    {
                        Fabric.getLogger().e("Beta", "Failed to find the APK file", (Throwable)var7_10);
                        var6_16 = var8_7;
                        ** if (var1_1 == null) goto lbl-1000
                    }
lbl-1000: // 1 sources:
                    {
                        var1_1.close();
                        var6_16 = var8_7;
                    }
lbl-1000: // 2 sources:
                    {
                        break block25;
                    }
                }
                if (var6_16 == null) throw var1_1;
                try {
                    var6_16.close();
                    throw var1_1;
                }
                catch (IOException var6_18) {
                    Fabric.getLogger().e("Beta", "Failed to close the APK file", var6_18);
                }
                throw var1_1;
                catch (PackageManager.NameNotFoundException var1_5) {
                    var1_1 = var7_10;
                }
            }
            var6_16 = var1_1;
            {
                Fabric.getLogger().d("Beta", "Beta by Crashlytics app is not installed");
                var6_16 = var8_7;
                ** if (var1_1 == null) goto lbl-1000
            }
lbl-1000: // 1 sources:
            {
                var1_1.close();
                var6_16 = var8_7;
            }
lbl-1000: // 2 sources:
            {
                break block25;
            }
            catch (IOException var1_4) {
                Fabric.getLogger().e("Beta", "Failed to close the APK file", var1_4);
                var6_16 = var8_7;
            }
        }
        var2_19 = (double)(System.nanoTime() - var4_6) / 1000000.0;
        var1_1 = Fabric.getLogger();
        var7_10 = new StringBuilder();
        var7_10.append("Beta device token load took ");
        var7_10.append(var2_19);
        var7_10.append("ms");
        var1_1.d("Beta", var7_10.toString());
        return var6_16;
    }
}
