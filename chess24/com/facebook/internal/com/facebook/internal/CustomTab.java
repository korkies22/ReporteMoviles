/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.facebook.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import com.facebook.FacebookSdk;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;

public class CustomTab {
    private Uri uri;

    public CustomTab(String string, Bundle object) {
        Bundle bundle = object;
        if (object == null) {
            bundle = new Bundle();
        }
        object = ServerProtocol.getDialogAuthority();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FacebookSdk.getGraphApiVersion());
        stringBuilder.append("/");
        stringBuilder.append("dialog/");
        stringBuilder.append(string);
        this.uri = Utility.buildUri((String)object, stringBuilder.toString(), bundle);
    }

    public void openCustomTab(Activity activity, String string) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        customTabsIntent.intent.setPackage(string);
        customTabsIntent.intent.addFlags(1073741824);
        customTabsIntent.launchUrl((Context)activity, this.uri);
    }
}
