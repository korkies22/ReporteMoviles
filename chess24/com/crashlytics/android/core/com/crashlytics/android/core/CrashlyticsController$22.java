/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
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
    final /* synthetic */ boolean val$isRooted;

    CrashlyticsController(boolean bl) {
        this.val$isRooted = bl;
    }

    @Override
    public void writeTo(FileOutputStream fileOutputStream) throws Exception {
        fileOutputStream.write(new JSONObject((Map)new HashMap<String, Object>(){
            {
                this.put("version", Build.VERSION.RELEASE);
                this.put("build_version", Build.VERSION.CODENAME);
                this.put("is_rooted", 22.this.val$isRooted);
            }
        }).toString().getBytes());
    }

}
