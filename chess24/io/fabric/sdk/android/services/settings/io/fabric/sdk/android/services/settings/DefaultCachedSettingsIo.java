/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.settings.CachedSettingsIo;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import org.json.JSONObject;

class DefaultCachedSettingsIo
implements CachedSettingsIo {
    private final Kit kit;

    public DefaultCachedSettingsIo(Kit kit) {
        this.kit = kit;
    }

    @Override
    public JSONObject readCachedSettings() {
        Object object;
        void var2_9;
        block11 : {
            Object object2;
            block10 : {
                block9 : {
                    File file;
                    block8 : {
                        Fabric.getLogger().d("Fabric", "Reading cached settings...");
                        file = null;
                        object = null;
                        object2 = new File(new FileStoreImpl(this.kit).getFilesDir(), "com.crashlytics.settings.json");
                        if (!object2.exists()) break block8;
                        object = object2 = new FileInputStream((File)object2);
                        try {
                            file = new JSONObject(CommonUtils.streamToString((InputStream)object2));
                            object = object2;
                            object2 = file;
                            break block9;
                        }
                        catch (Exception exception) {
                            break block10;
                        }
                    }
                    try {
                        Fabric.getLogger().d("Fabric", "No cached settings found.");
                        object2 = null;
                    }
                    catch (Throwable throwable) {
                        object = file;
                        break block11;
                    }
                    catch (Exception exception) {
                        object2 = null;
                    }
                }
                CommonUtils.closeOrLog((Closeable)object, "Error while closing settings cache file.");
                return object2;
            }
            object = object2;
            try {
                void var3_4;
                Fabric.getLogger().e("Fabric", "Failed to fetch cached settings", (Throwable)var3_4);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            CommonUtils.closeOrLog((Closeable)object2, "Error while closing settings cache file.");
            return null;
        }
        CommonUtils.closeOrLog(object, "Error while closing settings cache file.");
        throw var2_9;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void writeCachedSettings(long l, JSONObject object) {
        void var3_5;
        Object object2;
        block7 : {
            Object object3;
            block8 : {
                Fabric.getLogger().d("Fabric", "Writing settings to cache file...");
                if (object == null) return;
                Object var6_6 = null;
                object2 = object3 = null;
                object.put("expires_at", l);
                object2 = object3;
                object3 = new FileWriter(new File(new FileStoreImpl(this.kit).getFilesDir(), "com.crashlytics.settings.json"));
                try {
                    object3.write(object.toString());
                    object3.flush();
                }
                catch (Throwable throwable) {
                    object2 = object3;
                    break block7;
                }
                catch (Exception exception) {
                    object = object3;
                    object3 = exception;
                    break block8;
                }
                CommonUtils.closeOrLog((Closeable)object3, "Failed to close settings writer.");
                return;
                catch (Throwable throwable) {
                    break block7;
                }
                catch (Exception exception) {
                    object = var6_6;
                }
            }
            object2 = object;
            {
                Fabric.getLogger().e("Fabric", "Failed to cache settings", (Throwable)object3);
            }
            CommonUtils.closeOrLog((Closeable)object, "Failed to close settings writer.");
            return;
        }
        CommonUtils.closeOrLog(object2, "Failed to close settings writer.");
        throw var3_5;
    }
}
