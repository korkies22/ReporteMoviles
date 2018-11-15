/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzrk
extends zzf<zzrk> {
    private ProductAction zzaaN;
    private final Map<String, List<Product>> zzaaO = new HashMap<String, List<Product>>();
    private final List<Promotion> zzaaP = new ArrayList<Promotion>();
    private final List<Product> zzaaQ = new ArrayList<Product>();

    public String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (!this.zzaaQ.isEmpty()) {
            hashMap.put("products", this.zzaaQ);
        }
        if (!this.zzaaP.isEmpty()) {
            hashMap.put("promotions", this.zzaaP);
        }
        if (!this.zzaaO.isEmpty()) {
            hashMap.put("impressions", this.zzaaO);
        }
        hashMap.put("productAction", this.zzaaN);
        return zzrk.zzj(hashMap);
    }

    public void zza(Product product, String string) {
        if (product == null) {
            return;
        }
        String string2 = string;
        if (string == null) {
            string2 = "";
        }
        if (!this.zzaaO.containsKey(string2)) {
            this.zzaaO.put(string2, new ArrayList());
        }
        this.zzaaO.get(string2).add(product);
    }

    public void zza(zzrk zzrk2) {
        zzrk2.zzaaQ.addAll(this.zzaaQ);
        zzrk2.zzaaP.addAll(this.zzaaP);
        for (Map.Entry<String, List<Product>> entry : this.zzaaO.entrySet()) {
            String string = entry.getKey();
            Iterator<Product> object = entry.getValue().iterator();
            while (object.hasNext()) {
                zzrk2.zza(object.next(), string);
            }
        }
        if (this.zzaaN != null) {
            zzrk2.zzaaN = this.zzaaN;
        }
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrk)zzf2);
    }

    public ProductAction zzmO() {
        return this.zzaaN;
    }

    public List<Product> zzmP() {
        return Collections.unmodifiableList(this.zzaaQ);
    }

    public Map<String, List<Product>> zzmQ() {
        return this.zzaaO;
    }

    public List<Promotion> zzmR() {
        return Collections.unmodifiableList(this.zzaaP);
    }
}
