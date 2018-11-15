/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.Process
 *  android.text.TextUtils
 */
package com.google.android.gms.common.stats;

import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import java.util.List;

public class zze {
    public static String zza(PowerManager.WakeLock object, String string) {
        String string2 = String.valueOf(String.valueOf((long)Process.myPid() << 32 | (long)System.identityHashCode(object)));
        object = string;
        if (TextUtils.isEmpty((CharSequence)string)) {
            object = "";
        }
        if ((object = String.valueOf(object)).length() != 0) {
            return string2.concat((String)object);
        }
        return new String(string2);
    }

    static String zzdB(String string) {
        String string2 = string;
        if ("com.google.android.gms".equals(string)) {
            string2 = null;
        }
        return string2;
    }

    static List<String> zzz(List<String> list) {
        List<String> list2 = list;
        if (list != null) {
            list2 = list;
            if (list.size() == 1) {
                list2 = list;
                if ("com.google.android.gms".equals(list.get(0))) {
                    list2 = null;
                }
            }
        }
        return list2;
    }
}
