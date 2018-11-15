/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzf;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Promotion {
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_VIEW = "view";
    Map<String, String> zzacy = new HashMap<String, String>();

    void put(String string, String string2) {
        zzac.zzb(string, (Object)"Name should be non-null");
        this.zzacy.put(string, string2);
    }

    public Promotion setCreative(String string) {
        this.put("cr", string);
        return this;
    }

    public Promotion setId(String string) {
        this.put("id", string);
        return this;
    }

    public Promotion setName(String string) {
        this.put("nm", string);
        return this;
    }

    public Promotion setPosition(String string) {
        this.put("ps", string);
        return this;
    }

    public String toString() {
        return zzf.zzR(this.zzacy);
    }

    public Map<String, String> zzbL(String string) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : this.zzacy.entrySet()) {
            String string2 = String.valueOf(string);
            String string3 = String.valueOf(entry.getKey());
            string2 = string3.length() != 0 ? string2.concat(string3) : new String(string2);
            hashMap.put(string2, entry.getValue());
        }
        return hashMap;
    }
}
