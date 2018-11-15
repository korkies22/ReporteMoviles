/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.crashlytics.android.core;

import android.os.Build;
import com.crashlytics.android.core.CrashlyticsController;
import java.util.HashMap;

class CrashlyticsController
extends HashMap<String, Object> {
    CrashlyticsController() {
        this.put("version", Build.VERSION.RELEASE);
        this.put("build_version", Build.VERSION.CODENAME);
        this.put("is_rooted", 22.this.val$isRooted);
    }
}
