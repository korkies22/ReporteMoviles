/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.facebook.appevents.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import bolts.AppLinks;
import com.facebook.appevents.internal.SourceApplicationInfo;

public static class SourceApplicationInfo.Factory {
    public static SourceApplicationInfo create(Activity object) {
        String string = "";
        Object object2 = object.getCallingActivity();
        if (object2 != null) {
            object2 = object2.getPackageName();
            string = object2;
            if (object2.equals(object.getPackageName())) {
                return null;
            }
        }
        object2 = object.getIntent();
        boolean bl = false;
        object = string;
        boolean bl2 = bl;
        if (object2 != null) {
            object = string;
            bl2 = bl;
            if (!object2.getBooleanExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
                object2.putExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
                Bundle bundle = AppLinks.getAppLinkData((Intent)object2);
                object = string;
                bl2 = bl;
                if (bundle != null) {
                    object = bundle.getBundle("referer_app_link");
                    if (object != null) {
                        string = object.getString("package");
                    }
                    bl2 = true;
                    object = string;
                }
            }
        }
        object2.putExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
        return new SourceApplicationInfo((String)object, bl2, null);
    }
}
