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

public class SkuDetails {
    String mDescription;
    String mItemType;
    String mJson;
    String mPrice;
    String mSku;
    String mTitle;
    String mType;

    public SkuDetails(String string) throws JSONException {
        this("inapp", string);
    }

    public SkuDetails(String string, String string2) throws JSONException {
        this.mItemType = string;
        this.mJson = string2;
        string = new JSONObject(this.mJson);
        this.mSku = string.optString("productId");
        this.mType = string.optString("type");
        this.mPrice = string.optString("price");
        this.mTitle = string.optString("title");
        this.mDescription = string.optString("description");
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public String getSku() {
        return this.mSku;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getType() {
        return this.mType;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SkuDetails:");
        stringBuilder.append(this.mJson);
        return stringBuilder.toString();
    }
}
