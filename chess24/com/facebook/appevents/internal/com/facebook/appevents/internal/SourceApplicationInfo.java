/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 */
package com.facebook.appevents.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import bolts.AppLinks;
import com.facebook.FacebookSdk;

class SourceApplicationInfo {
    private static final String CALL_APPLICATION_PACKAGE_KEY = "com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage";
    private static final String OPENED_BY_APP_LINK_KEY = "com.facebook.appevents.SourceApplicationInfo.openedByApplink";
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    private String callingApplicationPackage;
    private boolean openedByAppLink;

    private SourceApplicationInfo(String string, boolean bl) {
        this.callingApplicationPackage = string;
        this.openedByAppLink = bl;
    }

    public static void clearSavedSourceApplicationInfoFromDisk() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext()).edit();
        editor.remove(CALL_APPLICATION_PACKAGE_KEY);
        editor.remove(OPENED_BY_APP_LINK_KEY);
        editor.apply();
    }

    public static SourceApplicationInfo getStoredSourceApplicatioInfo() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext());
        if (!sharedPreferences.contains(CALL_APPLICATION_PACKAGE_KEY)) {
            return null;
        }
        return new SourceApplicationInfo(sharedPreferences.getString(CALL_APPLICATION_PACKAGE_KEY, null), sharedPreferences.getBoolean(OPENED_BY_APP_LINK_KEY, false));
    }

    public String getCallingApplicationPackage() {
        return this.callingApplicationPackage;
    }

    public boolean isOpenedByAppLink() {
        return this.openedByAppLink;
    }

    public String toString() {
        String string = "Unclassified";
        if (this.openedByAppLink) {
            string = "Applink";
        }
        if (this.callingApplicationPackage != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("(");
            stringBuilder.append(this.callingApplicationPackage);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        return string;
    }

    public void writeSourceApplicationInfoToDisk() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext()).edit();
        editor.putString(CALL_APPLICATION_PACKAGE_KEY, this.callingApplicationPackage);
        editor.putBoolean(OPENED_BY_APP_LINK_KEY, this.openedByAppLink);
        editor.apply();
    }

    public static class Factory {
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
            return new SourceApplicationInfo((String)object, bl2);
        }
    }

}
