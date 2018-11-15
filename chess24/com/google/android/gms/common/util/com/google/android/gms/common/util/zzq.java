/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class zzq {
    /*
     * Enabled aggressive block sorting
     */
    public static void zza(StringBuilder stringBuilder, HashMap<String, String> hashMap) {
        stringBuilder.append("{");
        Iterator<String> iterator = hashMap.keySet().iterator();
        boolean bl = true;
        do {
            if (!iterator.hasNext()) {
                stringBuilder.append("}");
                return;
            }
            String string = iterator.next();
            if (!bl) {
                stringBuilder.append(",");
            } else {
                bl = false;
            }
            String string2 = hashMap.get(string);
            stringBuilder.append("\"");
            stringBuilder.append(string);
            stringBuilder.append("\":");
            if (string2 == null) {
                string = "null";
            } else {
                stringBuilder.append("\"");
                stringBuilder.append(string2);
                string = "\"";
            }
            stringBuilder.append(string);
        } while (true);
    }
}
