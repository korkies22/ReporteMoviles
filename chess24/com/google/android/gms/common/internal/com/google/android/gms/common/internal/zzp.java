/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 */
package com.google.android.gms.common.internal;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class zzp {
    private static final Uri zzaEQ = Uri.parse((String)"http://plus.google.com/");
    private static final Uri zzaER = zzaEQ.buildUpon().appendPath("circles").appendPath("find").build();

    private static Uri zzB(String string, @Nullable String string2) {
        string = Uri.parse((String)"market://details").buildUpon().appendQueryParameter("id", string);
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            string.appendQueryParameter("pcampaignid", string2);
        }
        return string.build();
    }

    public static Intent zzC(String string, @Nullable String string2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(zzp.zzB(string, string2));
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }

    public static Intent zzdt(String string) {
        string = Uri.fromParts((String)"package", (String)string, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData((Uri)string);
        return intent;
    }

    public static Intent zzxu() {
        Intent intent = new Intent("com.google.android.clockwork.home.UPDATE_ANDROID_WEAR_ACTION");
        intent.setPackage("com.google.android.wearable.app");
        return intent;
    }
}
