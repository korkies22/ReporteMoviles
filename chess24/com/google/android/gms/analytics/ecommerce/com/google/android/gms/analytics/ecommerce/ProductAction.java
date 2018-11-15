/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzf;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ProductAction {
    public static final String ACTION_ADD = "add";
    public static final String ACTION_CHECKOUT = "checkout";
    public static final String ACTION_CHECKOUT_OPTION = "checkout_option";
    @Deprecated
    public static final String ACTION_CHECKOUT_OPTIONS = "checkout_options";
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_DETAIL = "detail";
    public static final String ACTION_PURCHASE = "purchase";
    public static final String ACTION_REFUND = "refund";
    public static final String ACTION_REMOVE = "remove";
    Map<String, String> zzacy = new HashMap<String, String>();

    public ProductAction(String string) {
        this.put("&pa", string);
    }

    public Map<String, String> build() {
        return new HashMap<String, String>(this.zzacy);
    }

    void put(String string, String string2) {
        zzac.zzb(string, (Object)"Name should be non-null");
        this.zzacy.put(string, string2);
    }

    public ProductAction setCheckoutOptions(String string) {
        this.put("&col", string);
        return this;
    }

    public ProductAction setCheckoutStep(int n) {
        this.put("&cos", Integer.toString(n));
        return this;
    }

    public ProductAction setProductActionList(String string) {
        this.put("&pal", string);
        return this;
    }

    public ProductAction setProductListSource(String string) {
        this.put("&pls", string);
        return this;
    }

    public ProductAction setTransactionAffiliation(String string) {
        this.put("&ta", string);
        return this;
    }

    public ProductAction setTransactionCouponCode(String string) {
        this.put("&tcc", string);
        return this;
    }

    public ProductAction setTransactionId(String string) {
        this.put("&ti", string);
        return this;
    }

    public ProductAction setTransactionRevenue(double d) {
        this.put("&tr", Double.toString(d));
        return this;
    }

    public ProductAction setTransactionShipping(double d) {
        this.put("&ts", Double.toString(d));
        return this;
    }

    public ProductAction setTransactionTax(double d) {
        this.put("&tt", Double.toString(d));
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Iterator<Map.Entry<String, String>> iterator = this.zzacy.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String string = entry.getKey().startsWith("&") ? entry.getKey().substring(1) : entry.getKey();
            hashMap.put(string, entry.getValue());
        }
        return zzf.zzR(hashMap);
    }
}
