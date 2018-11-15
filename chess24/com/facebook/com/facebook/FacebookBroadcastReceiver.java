/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;

public class FacebookBroadcastReceiver
extends BroadcastReceiver {
    protected void onFailedAppCall(String string, String string2, Bundle bundle) {
    }

    public void onReceive(Context object, Intent intent) {
        object = intent.getStringExtra("com.facebook.platform.protocol.CALL_ID");
        String string = intent.getStringExtra("com.facebook.platform.protocol.PROTOCOL_ACTION");
        if (object != null && string != null) {
            Bundle bundle = intent.getExtras();
            if (NativeProtocol.isErrorResult(intent)) {
                this.onFailedAppCall((String)object, string, bundle);
                return;
            }
            this.onSuccessfulAppCall((String)object, string, bundle);
        }
    }

    protected void onSuccessfulAppCall(String string, String string2, Bundle bundle) {
    }
}
