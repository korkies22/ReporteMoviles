/*
 * Decompiled with CFR 0_134.
 */
package com.example.android.trivialdrivesample.util;

import com.example.android.trivialdrivesample.util.Purchase;
import com.example.android.trivialdrivesample.util.SkuDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Inventory {
    Map<String, Purchase> mPurchaseMap = new HashMap<String, Purchase>();
    Map<String, SkuDetails> mSkuMap = new HashMap<String, SkuDetails>();

    Inventory() {
    }

    void addPurchase(Purchase purchase) {
        this.mPurchaseMap.put(purchase.getSku(), purchase);
    }

    void addSkuDetails(SkuDetails skuDetails) {
        this.mSkuMap.put(skuDetails.getSku(), skuDetails);
    }

    public void erasePurchase(String string) {
        if (this.mPurchaseMap.containsKey(string)) {
            this.mPurchaseMap.remove(string);
        }
    }

    List<String> getAllOwnedSkus() {
        return new ArrayList<String>(this.mPurchaseMap.keySet());
    }

    List<String> getAllOwnedSkus(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Purchase purchase : this.mPurchaseMap.values()) {
            if (!purchase.getItemType().equals(string)) continue;
            arrayList.add(purchase.getSku());
        }
        return arrayList;
    }

    List<Purchase> getAllPurchases() {
        return new ArrayList<Purchase>(this.mPurchaseMap.values());
    }

    public Purchase getPurchase(String string) {
        return this.mPurchaseMap.get(string);
    }

    public SkuDetails getSkuDetails(String string) {
        return this.mSkuMap.get(string);
    }

    public boolean hasDetails(String string) {
        return this.mSkuMap.containsKey(string);
    }

    public boolean hasPurchase(String string) {
        return this.mPurchaseMap.containsKey(string);
    }
}
