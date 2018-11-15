/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.example.android.trivialdrivesample.util;

import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    String mDeveloperPayload;
    String mItemType;
    String mOrderId;
    String mOriginalJson;
    String mPackageName;
    int mPurchaseState;
    long mPurchaseTime;
    String mSignature;
    String mSku;
    String mToken;

    public Purchase(String string, String string2, String string3) throws JSONException {
        this.mItemType = string;
        this.mOriginalJson = string2;
        string = new JSONObject(this.mOriginalJson);
        this.mOrderId = string.optString("orderId");
        this.mPackageName = string.optString("packageName");
        this.mSku = string.optString("productId");
        this.mPurchaseTime = string.optLong("purchaseTime");
        this.mPurchaseState = string.optInt("purchaseState");
        this.mDeveloperPayload = string.optString("developerPayload");
        this.mToken = string.optString("token", string.optString("purchaseToken"));
        this.mSignature = string3;
    }

    public String getDeveloperPayload() {
        return this.mDeveloperPayload;
    }

    public String getItemType() {
        return this.mItemType;
    }

    public String getOrderId() {
        return this.mOrderId;
    }

    public String getOriginalJson() {
        return this.mOriginalJson;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getPurchaseState() {
        return this.mPurchaseState;
    }

    public long getPurchaseTime() {
        return this.mPurchaseTime;
    }

    public String getSignature() {
        return this.mSignature;
    }

    public String getSku() {
        return this.mSku;
    }

    public String getToken() {
        return this.mToken;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PurchaseInfo(type:");
        stringBuilder.append(this.mItemType);
        stringBuilder.append("):");
        stringBuilder.append(this.mOriginalJson);
        return stringBuilder.toString();
    }
}
