/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class zzf<T extends zzf> {
    public static String zzR(Map map) {
        return zzf.zza(map, 1);
    }

    private static String zza(Object iterator, int n) {
        if (n > 10) {
            return "ERROR: Recursive toString calls";
        }
        if (iterator == null) {
            return "";
        }
        if (iterator instanceof String) {
            if (TextUtils.isEmpty((CharSequence)((String)((Object)iterator)))) {
                return "";
            }
            return iterator.toString();
        }
        if (iterator instanceof Integer) {
            if ((Integer)((Object)iterator) == 0) {
                return "";
            }
            return iterator.toString();
        }
        if (iterator instanceof Long) {
            if ((Long)((Object)iterator) == 0L) {
                return "";
            }
            return iterator.toString();
        }
        if (iterator instanceof Double) {
            if ((Double)((Object)iterator) == 0.0) {
                return "";
            }
            return iterator.toString();
        }
        if (iterator instanceof Boolean) {
            if (!((Boolean)((Object)iterator)).booleanValue()) {
                return "";
            }
            return iterator.toString();
        }
        if (iterator instanceof List) {
            StringBuffer stringBuffer = new StringBuffer();
            if (n > 0) {
                stringBuffer.append("[");
            }
            iterator = (List)((Object)iterator);
            int n2 = stringBuffer.length();
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (stringBuffer.length() > n2) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(zzf.zza(object, n + 1));
            }
            if (n > 0) {
                stringBuffer.append("]");
            }
            return stringBuffer.toString();
        }
        if (iterator instanceof Map) {
            StringBuffer stringBuffer = new StringBuffer();
            iterator = new TreeMap((Map)((Object)iterator)).entrySet().iterator();
            boolean bl = false;
            int n3 = 0;
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                String string = zzf.zza(entry.getValue(), n + 1);
                if (TextUtils.isEmpty((CharSequence)string)) continue;
                boolean bl2 = bl;
                int n4 = n3;
                if (n > 0) {
                    bl2 = bl;
                    n4 = n3;
                    if (!bl) {
                        stringBuffer.append("{");
                        n4 = stringBuffer.length();
                        bl2 = true;
                    }
                }
                if (stringBuffer.length() > n4) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append((String)entry.getKey());
                stringBuffer.append('=');
                stringBuffer.append(string);
                bl = bl2;
                n3 = n4;
            }
            if (bl) {
                stringBuffer.append("}");
            }
            return stringBuffer.toString();
        }
        return iterator.toString();
    }

    public static String zzj(Object object) {
        return zzf.zza(object, 0);
    }

    public abstract void zzb(T var1);
}
