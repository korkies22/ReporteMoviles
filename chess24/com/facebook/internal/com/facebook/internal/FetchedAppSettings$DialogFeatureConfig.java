/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.net.Uri;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

public static class FetchedAppSettings.DialogFeatureConfig {
    private static final String DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR = "\\|";
    private static final String DIALOG_CONFIG_NAME_KEY = "name";
    private static final String DIALOG_CONFIG_URL_KEY = "url";
    private static final String DIALOG_CONFIG_VERSIONS_KEY = "versions";
    private String dialogName;
    private Uri fallbackUrl;
    private String featureName;
    private int[] featureVersionSpec;

    private FetchedAppSettings.DialogFeatureConfig(String string, String string2, Uri uri, int[] arrn) {
        this.dialogName = string;
        this.featureName = string2;
        this.fallbackUrl = uri;
        this.featureVersionSpec = arrn;
    }

    public static FetchedAppSettings.DialogFeatureConfig parseDialogConfig(JSONObject jSONObject) {
        String string = jSONObject.optString(DIALOG_CONFIG_NAME_KEY);
        boolean bl = Utility.isNullOrEmpty(string);
        Uri uri = null;
        if (bl) {
            return null;
        }
        Object object = string.split(DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR);
        if (((String[])object).length != 2) {
            return null;
        }
        string = object[0];
        object = object[1];
        if (!Utility.isNullOrEmpty(string)) {
            if (Utility.isNullOrEmpty((String)object)) {
                return null;
            }
            String string2 = jSONObject.optString(DIALOG_CONFIG_URL_KEY);
            if (!Utility.isNullOrEmpty(string2)) {
                uri = Uri.parse((String)string2);
            }
            return new FetchedAppSettings.DialogFeatureConfig(string, (String)object, uri, FetchedAppSettings.DialogFeatureConfig.parseVersionSpec(jSONObject.optJSONArray(DIALOG_CONFIG_VERSIONS_KEY)));
        }
        return null;
    }

    private static int[] parseVersionSpec(JSONArray jSONArray) {
        Object object;
        block6 : {
            if (jSONArray != null) {
                int n = jSONArray.length();
                int[] arrn = new int[n];
                int n2 = 0;
                do {
                    int n3;
                    object = arrn;
                    if (n2 >= n) break block6;
                    int n4 = n3 = jSONArray.optInt(n2, -1);
                    if (n3 == -1) {
                        object = jSONArray.optString(n2);
                        n4 = n3;
                        if (!Utility.isNullOrEmpty((String)object)) {
                            try {
                                n4 = Integer.parseInt((String)object);
                            }
                            catch (NumberFormatException numberFormatException) {
                                Utility.logd("FacebookSDK", numberFormatException);
                                n4 = -1;
                            }
                        }
                    }
                    arrn[n2] = n4;
                    ++n2;
                } while (true);
            }
            object = null;
        }
        return object;
    }

    public String getDialogName() {
        return this.dialogName;
    }

    public Uri getFallbackUrl() {
        return this.fallbackUrl;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public int[] getVersionSpec() {
        return this.featureVersionSpec;
    }
}
