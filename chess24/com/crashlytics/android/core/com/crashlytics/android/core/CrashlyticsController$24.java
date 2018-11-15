/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  org.json.JSONObject
 */
package com.crashlytics.android.core;

import android.os.Build;
import com.crashlytics.android.core.CrashlyticsController;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CrashlyticsController
implements CrashlyticsController.FileOutputStreamWriteAction {
    final /* synthetic */ int val$arch;
    final /* synthetic */ int val$availableProcessors;
    final /* synthetic */ long val$diskSpace;
    final /* synthetic */ Map val$ids;
    final /* synthetic */ boolean val$isEmulator;
    final /* synthetic */ int val$state;
    final /* synthetic */ long val$totalRam;

    CrashlyticsController(int n, int n2, long l, long l2, boolean bl, Map map, int n3) {
        this.val$arch = n;
        this.val$availableProcessors = n2;
        this.val$totalRam = l;
        this.val$diskSpace = l2;
        this.val$isEmulator = bl;
        this.val$ids = map;
        this.val$state = n3;
    }

    @Override
    public void writeTo(FileOutputStream fileOutputStream) throws Exception {
        fileOutputStream.write(new JSONObject((Map)new HashMap<String, Object>(){
            {
                this.put("arch", 24.this.val$arch);
                this.put("build_model", Build.MODEL);
                this.put("available_processors", 24.this.val$availableProcessors);
                this.put("total_ram", 24.this.val$totalRam);
                this.put("disk_space", 24.this.val$diskSpace);
                this.put("is_emulator", 24.this.val$isEmulator);
                this.put("ids", 24.this.val$ids);
                this.put("state", 24.this.val$state);
                this.put("build_manufacturer", Build.MANUFACTURER);
                this.put("build_product", Build.PRODUCT);
            }
        }).toString().getBytes());
    }

}
