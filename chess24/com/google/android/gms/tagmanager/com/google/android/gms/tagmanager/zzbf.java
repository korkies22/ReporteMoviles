/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.google.android.gms.tagmanager.zzdd;
import java.util.HashMap;
import java.util.Map;

public class zzbf {
    private static String zzbEt;
    static Map<String, String> zzbEu;

    static {
        zzbEu = new HashMap<String, String>();
    }

    static void zzG(Context context, String string) {
        zzdd.zzc(context, "gtm_install_referrer", "referrer", string);
        zzbf.zzI(context, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String zzH(Context object, String string) {
        if (zzbEt == null) {
            synchronized (zzbf.class) {
                if (zzbEt == null) {
                    object = (object = object.getSharedPreferences("gtm_install_referrer", 0)) != null ? object.getString("referrer", "") : "";
                    zzbEt = object;
                }
            }
        }
        return zzbf.zzag(zzbEt, string);
    }

    public static void zzI(Context context, String string) {
        String string2 = zzbf.zzag(string, "conv");
        if (string2 != null && string2.length() > 0) {
            zzbEu.put(string2, string);
            zzdd.zzc(context, "gtm_click_referrers", string2, string);
        }
    }

    public static String zzag(String string, String string2) {
        if (string2 == null) {
            if (string.length() > 0) {
                return string;
            }
            return null;
        }
        string = (string = String.valueOf(string)).length() != 0 ? "http://hostname/?".concat(string) : new String("http://hostname/?");
        return Uri.parse((String)string).getQueryParameter(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zzhn(String string) {
        synchronized (zzbf.class) {
            zzbEt = string;
            return;
        }
    }

    public static String zzj(Context object, String string, String string2) {
        String string3;
        String string4 = string3 = zzbEu.get(string);
        if (string3 == null) {
            object = (object = object.getSharedPreferences("gtm_click_referrers", 0)) != null ? object.getString(string, "") : "";
            zzbEu.put(string, (String)object);
            string4 = object;
        }
        return zzbf.zzag(string4, string2);
    }
}
