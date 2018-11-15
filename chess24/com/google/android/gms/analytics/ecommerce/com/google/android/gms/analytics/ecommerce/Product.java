/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzc;
import com.google.android.gms.analytics.zzf;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Product {
    Map<String, String> zzacy = new HashMap<String, String>();

    void put(String string, String string2) {
        zzac.zzb(string, (Object)"Name should be non-null");
        this.zzacy.put(string, string2);
    }

    public Product setBrand(String string) {
        this.put("br", string);
        return this;
    }

    public Product setCategory(String string) {
        this.put("ca", string);
        return this;
    }

    public Product setCouponCode(String string) {
        this.put("cc", string);
        return this;
    }

    public Product setCustomDimension(int n, String string) {
        this.put(zzc.zzaw(n), string);
        return this;
    }

    public Product setCustomMetric(int n, int n2) {
        this.put(zzc.zzax(n), Integer.toString(n2));
        return this;
    }

    public Product setId(String string) {
        this.put("id", string);
        return this;
    }

    public Product setName(String string) {
        this.put("nm", string);
        return this;
    }

    public Product setPosition(int n) {
        this.put("ps", Integer.toString(n));
        return this;
    }

    public Product setPrice(double d) {
        this.put("pr", Double.toString(d));
        return this;
    }

    public Product setQuantity(int n) {
        this.put("qt", Integer.toString(n));
        return this;
    }

    public Product setVariant(String string) {
        this.put("va", string);
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
