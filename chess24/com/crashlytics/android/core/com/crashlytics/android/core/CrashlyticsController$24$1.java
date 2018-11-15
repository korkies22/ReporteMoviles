/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 */
package com.crashlytics.android.core;

import android.os.Build;
import com.crashlytics.android.core.CrashlyticsController;
import java.util.HashMap;
import java.util.Map;

class CrashlyticsController
extends HashMap<String, Object> {
    CrashlyticsController() {
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
}
