/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CrashlyticsController
implements CrashlyticsController.FileOutputStreamWriteAction {
    final /* synthetic */ String val$generator;
    final /* synthetic */ String val$sessionId;
    final /* synthetic */ long val$startedAtSeconds;

    CrashlyticsController(String string, String string2, long l) {
        this.val$sessionId = string;
        this.val$generator = string2;
        this.val$startedAtSeconds = l;
    }

    @Override
    public void writeTo(FileOutputStream fileOutputStream) throws Exception {
        fileOutputStream.write(new JSONObject((Map)new HashMap<String, Object>(){
            {
                this.put("session_id", 18.this.val$sessionId);
                this.put("generator", 18.this.val$generator);
                this.put("started_at_seconds", 18.this.val$startedAtSeconds);
            }
        }).toString().getBytes());
    }

}
